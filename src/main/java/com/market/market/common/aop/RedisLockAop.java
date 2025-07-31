package com.market.market.common.aop;

import com.market.market.common.annotation.RedisLock;
import com.market.market.common.exception.BadRequestException;
import com.market.market.common.exception.ErrorCode;
import com.market.market.common.infrastructure.RedisLockManager;
import com.market.market.common.tx.TransactionFacade;
import com.market.market.common.util.CSpringExpressionParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RedisLockAop {

	private static final String REDIS_LOCK_PREFIX = "LOCK:";

	private final RedisLockManager redisLockManager;
	private final TransactionFacade transactionFacade;

	@Around("@annotation(com.market.market.common.annotation.RedisLock)")
	public Object lock(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		RedisLock lock = method.getAnnotation(RedisLock.class);

		long leaseTime = lock.leaseTime();

		String key = REDIS_LOCK_PREFIX + CSpringExpressionParser.getDynamicValue(
				signature.getParameterNames(),
				joinPoint.getArgs(),
				lock.key()
		);

		try {
			boolean available = redisLockManager.tryLock(key, leaseTime);
			if (!available) {
				throw new BadRequestException(ErrorCode.PRODUCT_IS_LOCKED_AOP);
			}
			return transactionFacade.proceed(joinPoint);
		} catch (Exception e) {
			throw e;
		} finally {
			redisLockManager.unLock(key);
		}
	}
}