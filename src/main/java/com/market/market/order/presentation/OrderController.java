package com.market.market.order.presentation;

import com.market.market.order.application.OrderService;
import com.market.market.order.dto.request.OrderConfirmRequest;
import com.market.market.order.dto.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/{productId}/aop")
	public ResponseEntity<Void> orderAopLock(
			@RequestBody OrderRequest request,
			@PathVariable Long productId
	) {
		orderService.orderAopLock(productId, request);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{productId}/function")
	public ResponseEntity<Void> orderFunctionalLock(
			@RequestBody OrderRequest request,
			@PathVariable Long productId
	) {
		orderService.orderFunctionalLock(productId, request);
		return ResponseEntity.ok().build();
	}
}
