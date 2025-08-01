package com.market.market.product.domain.repository;

import com.market.market.product.domain.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductQuantityJpaRepository extends JpaRepository<ProductQuantity, Long> {
	Optional<ProductQuantity> findByProductId(Long productId);
}
