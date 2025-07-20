package com.market.market.product.domain.repository;

import com.market.market.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id);
    }
}
