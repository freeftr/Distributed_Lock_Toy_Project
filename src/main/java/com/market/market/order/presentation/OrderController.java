package com.market.market.order.presentation;

import com.market.market.order.application.OrderService;
import com.market.market.order.dto.request.OrderConfirmRequest;
import com.market.market.order.dto.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/api/v1/products/{productId}/order")
	public ResponseEntity<Void> createOrder(
			@RequestBody OrderRequest request,
			@PathVariable Long productId
	) {
		orderService.order(productId, request);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/api/v1/orders/{orderId}/confirm")
	private ResponseEntity<Void> confirmOrder(
			@RequestBody OrderConfirmRequest request,
			@PathVariable Long orderId
	) {
		orderService.confirm(orderId, request);
		return ResponseEntity.ok().build();
	}
}
