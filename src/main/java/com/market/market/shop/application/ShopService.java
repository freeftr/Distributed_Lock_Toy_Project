package com.market.market.shop.application;

import com.market.market.shop.domain.Shop;
import com.market.market.shop.domain.repository.ShopRepository;
import com.market.market.shop.dto.request.ShopCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    public void createShop(ShopCreateRequest request) {
        Shop shop = Shop.builder()
                .name(request.name())
                .ownerId(request.ownerId())
                .build();

        shopRepository.createShop(shop);
    }
}
