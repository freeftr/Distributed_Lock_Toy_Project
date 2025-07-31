package com.market.market.product.domain.repository;

import com.market.market.product.domain.Product;
import com.market.market.product.dto.query.ProductDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id);
    }

    public List<ProductDetail> getProductDetailsIn(List<Long> ids) {
        return productJpaRepository.getProductDetailIn(ids);
    }
}
