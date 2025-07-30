package com.market.market.shop.dto.request;

public record ShopCreateRequest(
		String shopName,
		Long ownerId
) {
}
