package com.market.market.common.infrastructure;

import com.market.market.common.tx.SupplierTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.UUID;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class FunctionalLockManager {

	private static final String LOCK_REMOVE_SCRIPT =
			"""
			if redis.call('get', KEYS[1]) == ARGV[1] then
				return redis.call('del', KEYS[1])
			else
				return 0;
			end
			""";

	private static final long WAIT_TIME = 10_000L;
	private static final long LEASE_TIME = 10_000L;

	private final RedisTemplate<String, Object> redisTemplate;
	private final SupplierTransaction supplierTransaction;

	public <T> T tryLock(String key, Supplier<T> supplier) {
		String value = UUID.randomUUID().toString();
		long endTime = System.currentTimeMillis() + WAIT_TIME;

		while (System.currentTimeMillis() < endTime) {
			Boolean isSuccess = redisTemplate.opsForValue().setIfAbsent(
					key,
					value,
					Duration.ofMillis(LEASE_TIME)
			);

			if (isSuccess) {
				try {
					return supplierTransaction.proceed(supplier);
				} finally {
					unLock(key, value);
				}
			}

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}

		throw new RuntimeException("락 획득 실패: " + key);
	}

	public void unLock(String key, String value) {
		redisTemplate.execute(
				new DefaultRedisScript<>(LOCK_REMOVE_SCRIPT, Long.class),
				Collections.singletonList(key),
				value
		);
	}
}
