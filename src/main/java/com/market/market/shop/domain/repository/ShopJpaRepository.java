package com.market.market.shop.domain.repository;

import com.market.market.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopJpaRepository extends JpaRepository<Shop, Long> {
}
