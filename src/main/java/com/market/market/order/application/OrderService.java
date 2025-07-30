package com.market.market.order.application;

import com.market.market.account.domain.Account;
import com.market.market.account.domain.repository.AccountRepository;
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
	private final AccountRepository accountRepository;
	private final ProductRepository productRepository;

	public void order(
			Long productId,
			OrderRequest request
	) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new BadRequestException(ErrorCode.PRODUCT_NOT_FOUND));

		if (product.getQuantity() < request.amount()) {
			throw new BadRequestException(ErrorCode.NOT_ENOUGH_STOCK);
		}

		int totalPrice = product.getPrice() * request.amount();

		Account account = accountRepository.findByMemberId(request.buyerId())
				.orElseThrow(() -> new BadRequestException(ErrorCode.ACCOUNT_NOT_FOUND));

		if (account.getBalance() < totalPrice) {
			throw new BadRequestException(ErrorCode.NOT_ENOUGH_BALANCE);
		}

		Order order = Order.builder()
				.buyerId(request.buyerId())
				.orderStatus(OrderStatus.ONGOING)
				.productId(productId)
				.amount(request.amount())
				.totalPrice(totalPrice)
				.build();

		orderRepository.save(order);
	}

	public void confirm(Long orderId, OrderConfirmRequest request) {
		Long buyerId = request.buyerId();

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new BadRequestException(ErrorCode.ORDER_NOT_FOUND));

		if (!order.getBuyerId().equals(buyerId)) {
			throw new BadRequestException(ErrorCode.UNAUTHORIZED_ORDER);
		}

		Product product = productRepository.findById(order.getProductId())
				.orElseThrow(() -> new BadRequestException(ErrorCode.PRODUCT_NOT_FOUND));

		Account account = accountRepository.findByMemberId(buyerId)
				.orElseThrow(() -> new BadRequestException(ErrorCode.ACCOUNT_NOT_FOUND));

		order.confirm();
		account.withdraw(order.getTotalPrice());
		product.sold(order.getAmount());

		//TODO: 트랜잭션 저장
	}
}
