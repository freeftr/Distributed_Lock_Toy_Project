package com.market.market.common.functional;

import com.market.market.common.exception.BadRequestException;
import com.market.market.common.exception.ErrorCode;
import com.market.market.common.infrastructure.RedisLockManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class FunctionalDistributedLockExecutor {
	private final RedisLockManager redisLockManager;

	@Transactional
	public <T> T executeWithLock(String key, Supplier<T> task) {
		boolean available = redisLockManager.tryLock(key);
		if (!available) {
			throw new BadRequestException(ErrorCode.PRODUCT_IS_LOCKED_AOP);
		}
		try {
			return task.get();
		} finally {
			redisLockManager.unLock(key);
		}
	}
}
