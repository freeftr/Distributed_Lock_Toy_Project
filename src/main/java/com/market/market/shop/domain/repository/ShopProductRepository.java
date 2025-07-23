package com.market.market.shop.domain.repository;

import com.market.market.shop.domain.ShopProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShopProductRepository {
    private final ShopProductJpaRepository shopProductJpaRepository;

    public Optional<ShopProduct> findBuId(Long id) {
        return shopProductJpaRepository.findById(id);
    }
}
