package com.market.market.shop.dto.response;

public record ShopProductResponse(
		Long shopId,
		Long productId,
		String productName,
		int price
) {
}
