package com.market.market.product.domain.repository;

import com.market.market.product.domain.ProductQuantity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductQuantityRepository {
	private final ProductQuantityJpaRepository productQuantityJpaRepository;

	public ProductQuantity save(ProductQuantity productQuantity) {
		return productQuantityJpaRepository.save(productQuantity);
	}

	public Optional<ProductQuantity> findByProductId(Long productId) {
		return productQuantityJpaRepository.findByProductId(productId);
	}
}
