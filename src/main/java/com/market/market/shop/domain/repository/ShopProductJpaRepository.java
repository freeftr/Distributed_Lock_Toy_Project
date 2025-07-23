package com.market.market.shop.domain.repository;

import com.market.market.shop.domain.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopProductJpaRepository extends JpaRepository<ShopProduct, Long> {
}
