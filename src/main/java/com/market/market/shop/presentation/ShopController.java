package com.market.market.shop.presentation;

import com.market.market.shop.application.ShopService;
import com.market.market.shop.domain.Shop;
import com.market.market.shop.dto.request.ShopCreateRequest;
import com.market.market.shop.dto.response.ShopProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shops")
@RequiredArgsConstructor
public class ShopController {
	private final ShopService shopService;

	@PostMapping
	public ResponseEntity<Void> create(@RequestBody ShopCreateRequest request) {
		shopService.createShop(request);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/products")
	public ResponseEntity<List<ShopProductResponse>> getShopProducts(@RequestParam Long shopId) {
		return ResponseEntity.ok(shopService.getShopProducts(shopId));
	}
}
