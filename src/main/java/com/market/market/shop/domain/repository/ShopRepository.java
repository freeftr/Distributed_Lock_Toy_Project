package com.market.market.shop.domain.repository;

import com.market.market.shop.domain.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShopRepository {
    private final ShopJpaRepository shopJpaRepository;

    public Optional<Shop> findById(Long id) {
        return shopJpaRepository.findById(id);
    }
}
