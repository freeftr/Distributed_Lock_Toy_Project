package com.market.market.order.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderProductRepository {

    private final OrderProductJpaRepository orderProductJpaRepository;
    private final OrderProductJdbcRepository orderProductJdbcRepository;
}
