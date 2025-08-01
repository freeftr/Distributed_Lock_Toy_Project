package com.market.market.common.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RedisLockManager {

	private static final String LOCK_REMOVE_SCRIPT =
			"""
			if redis.call('get', KEYS[1]) == ARGV[1] then
				return redis.call('del', KEYS[1])
			else
				return 0;
			end
			""";

	private static final ThreadLocal<String> lockValueHolder = new ThreadLocal<>();

	private static final long WAIT_TIME = 3000L;
	private static final long LEASE_TIME = 10000L;

	private final RedisTemplate<String, Object> redisTemplate;

	public boolean tryLock(String key) {
		String value = UUID.randomUUID().toString();
		long endTime = System.currentTimeMillis() + WAIT_TIME;

		while (System.currentTimeMillis() < endTime) {
			Boolean isSuccess = redisTemplate.opsForValue().setIfAbsent(
					key,
					value,
					Duration.ofMillis(LEASE_TIME)
			);

			if (isSuccess) {
				lockValueHolder.set(value);
				return true;
			}

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}

		return false;
	}

	public void unLock(String key) {
		String value = lockValueHolder.get();
		if (value == null) {
			return;
		}

		redisTemplate.execute(
				new DefaultRedisScript<>(LOCK_REMOVE_SCRIPT, Long.class),
				Collections.singletonList(key),
				value
		);

		lockValueHolder.remove();
	}
}
