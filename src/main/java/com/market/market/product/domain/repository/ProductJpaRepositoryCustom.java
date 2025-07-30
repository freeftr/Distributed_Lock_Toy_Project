package com.market.market.product.domain.repository;

import com.market.market.product.dto.query.ProductDetail;

import java.util.List;

public interface ProductJpaRepositoryCustom {
	List<ProductDetail> getProductDetailIn(List<Long> ids);

}
