package com.market.market.order.application;

import com.market.market.common.annotation.RedisLock;
import com.market.market.common.exception.BadRequestException;
import com.market.market.common.exception.ErrorCode;
import com.market.market.common.functional.FunctionalDistributedLockExecutor;
import com.market.market.member.domain.Member;
import com.market.market.member.domain.repository.MemberRepository;
import com.market.market.order.domain.Order;
import com.market.market.order.domain.OrderStatus;
import com.market.market.order.domain.repository.OrderRepository;
import com.market.market.order.dto.request.OrderRequest;
import com.market.market.product.domain.Product;
import com.market.market.product.domain.ProductQuantity;
import com.market.market.product.domain.repository.ProductQuantityRepository;
import com.market.market.product.domain.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final ProductQuantityRepository productQuantityRepository;
	private final MemberRepository memberRepository;
	private final FunctionalDistributedLockExecutor functionalDistributedLockExecutor;

	@RedisLock(key = "'productId:' + #productId")
	public void orderAopLock(Long productId, OrderRequest request) {

		Member member = memberRepository.findById(request.buyerId())
				.orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new BadRequestException(ErrorCode.PRODUCT_NOT_FOUND));

		ProductQuantity quantity = productQuantityRepository.findByProductId(productId)
				.orElseThrow(() -> new BadRequestException(ErrorCode.PRODUCT_NOT_FOUND));

		quantity.decrease(request.amount());

		if (quantity.isSoldOut()) {
			product.markSoldOut();
		}

		Order order = Order.builder()
				.buyerId(request.buyerId())
				.orderStatus(OrderStatus.ONGOING)
				.productId(productId)
				.amount(request.amount())
				.build();

		order.confirm();
		orderRepository.save(order);
	}

	@Transactional
	public void orderFunctionalLock(Long productId, OrderRequest request) {
		String key = "LOCK:productId:" + productId;
		long leaseTime = 3000L;

		Member member = memberRepository.findById(request.buyerId())
				.orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new BadRequestException(ErrorCode.PRODUCT_NOT_FOUND));

		functionalDistributedLockExecutor.executeWithLock(key, () -> {

			ProductQuantity quantity = productQuantityRepository.findByProductId(productId)
					.orElseThrow(() -> new BadRequestException(ErrorCode.PRODUCT_NOT_FOUND));

			quantity.decrease(request.amount());

			if (quantity.isSoldOut()) {
				product.markSoldOut();
			}

			Order order = Order.builder()
					.buyerId(request.buyerId())
					.orderStatus(OrderStatus.ONGOING)
					.productId(productId)
					.amount(request.amount())
					.build();

			order.confirm();
			orderRepository.save(order);

			return null;
		});
	}
}
