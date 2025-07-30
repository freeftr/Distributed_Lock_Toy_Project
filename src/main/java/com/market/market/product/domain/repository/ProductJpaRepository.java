package com.market.market.product.domain.repository;

import com.market.market.product.domain.Product;
import com.market.market.product.dto.query.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductJpaRepositoryCustom{
}
