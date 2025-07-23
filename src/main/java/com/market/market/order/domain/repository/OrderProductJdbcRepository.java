package com.market.market.order.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderProductJdbcRepository {
    private final JdbcTemplate jdbcTemplate;
}
