package com.market.market.common.tx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Slf4j
@Component
public class SupplierTransaction {

	@Transactional
	public <T> T proceed(Supplier<T> supplier) {
		return supplier.get();
	}

}