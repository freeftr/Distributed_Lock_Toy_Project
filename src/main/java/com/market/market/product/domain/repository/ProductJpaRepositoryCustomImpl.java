package com.market.market.product.domain.repository;

import com.market.market.product.dto.query.ProductDetail;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.market.market.product.domain.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductJpaRepositoryCustomImpl implements ProductJpaRepositoryCustom {

	private final JPAQueryFactory queryFactory;


	@Override
	public List<ProductDetail> getProductDetailIn(List<Long> ids) {
		return queryFactory
				.select(Projections.constructor(
						ProductDetail.class,
						product.id,
						product.name,
						product.price
				))
				.from(product)
				.where(product.id.in(ids))
				.fetch();
	}
}
