package com.market.market.shop.domain.repository;

import com.market.market.shop.domain.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopProductJpaRepository extends JpaRepository<ShopProduct, Long> {
	@Query("""
		SELECT sp.productId
		FROM ShopProduct sp
		WHERE sp.shopId = :shopId
	""")
	List<Long> getProductIdsByShopId(Long shopId);
}
