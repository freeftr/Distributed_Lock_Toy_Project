package com.market.market.order.dto.request;

public record OrderRequest(
		Long buyerId,
		int amount
) {
}
