package com.market.market.order.domain.repository;

import com.market.market.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    public Optional<Order> findById(Long id) {
        return orderJpaRepository.findById(id);
    }

    public Long count() {
        return orderJpaRepository.count();
    }
}
