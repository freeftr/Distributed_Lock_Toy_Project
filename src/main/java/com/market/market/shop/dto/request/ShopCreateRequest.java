package com.market.market.shop.dto.request;

public record ShopCreateRequest(
        String name,
        Long ownerId
) {
}
