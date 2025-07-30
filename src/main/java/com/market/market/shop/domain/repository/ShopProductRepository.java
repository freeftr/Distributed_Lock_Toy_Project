package com.market.market.shop.domain.repository;

import com.market.market.shop.domain.ShopProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShopProductRepository {
    private final ShopProductJpaRepository shopProductJpaRepository;

    public Optional<ShopProduct> findById(Long id) {
        return shopProductJpaRepository.findById(id);
    }

    public List<Long> getProductIdsByShopId(Long shopId) {
        return shopProductJpaRepository.getProductIdsByShopId(shopId);
    }
}
