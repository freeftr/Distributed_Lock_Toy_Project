package com.market.market.order.application;

import com.market.market.common.annotation.RedisLock;
import com.market.market.common.exception.BadRequestException;
import com.market.market.common.exception.ErrorCode;
import com.market.market.order.domain.Order;
import com.market.market.order.domain.OrderStatus;
import com.market.market.order.domain.repository.OrderRepository;
import com.market.market.order.dto.request.OrderConfirmRequest;
import com.market.market.order.dto.request.OrderRequest;
import com.market.market.product.domain.Product;
import com.market.market.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;

	@RedisLock(key = "'productId:' + #productId")
	public void orderAopLock(
			Long productId,
			OrderRequest request
	) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new BadRequestException(ErrorCode.PRODUCT_NOT_FOUND));

		if (product.getQuantity() < request.amount()) {
			throw new BadRequestException(ErrorCode.NOT_ENOUGH_STOCK);
		}


		Order order = Order.builder()
				.buyerId(request.buyerId())
				.orderStatus(OrderStatus.ONGOING)
				.productId(productId)
				.amount(request.amount())
				.build();

		orderRepository.save(order);
	}


	@RedisLock(key = "'productId:' + #productId")
	public void confirmAopLock(Long orderId, OrderConfirmRequest request) {
		Long buyerId = request.buyerId();

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new BadRequestException(ErrorCode.ORDER_NOT_FOUND));

		if (!order.getBuyerId().equals(buyerId)) {
			throw new BadRequestException(ErrorCode.UNAUTHORIZED_ORDER);
		}

		Product product = productRepository.findById(order.getProductId())
				.orElseThrow(() -> new BadRequestException(ErrorCode.PRODUCT_NOT_FOUND));

		order.confirm();
		product.sold(order.getAmount());
	}
}
