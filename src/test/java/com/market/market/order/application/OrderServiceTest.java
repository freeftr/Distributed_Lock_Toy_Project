package com.market.market.order.application;

import com.market.market.order.domain.Order;
import com.market.market.order.domain.repository.OrderRepository;
import com.market.market.order.dto.request.OrderRequest;
import com.market.market.product.domain.Product;
import com.market.market.product.domain.ProductStatus;
import com.market.market.product.domain.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderServiceConcurrencyTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	private Long productId;

	@BeforeEach
	void setUp() {
		Product product = Product.builder()
				.name("동시성 상품")
				.price(1000)
				.detail("테스트용 상품입니다")
				.quantity(10)
				.status(ProductStatus.ON_SALE)
				.build();

		productId = productRepository.save(product).getId();
	}

	@Test
	void 동시에_10개의_주문을_요청하면_정확히_1개만_성공한다() throws InterruptedException {
		int threadCount = 10;
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			final long buyerId = i + 1;

			executorService.submit(() -> {
				try {
					OrderRequest request = new OrderRequest(buyerId, 1);
					orderService.orderAopLock(productId, request);
				} catch (Exception e) {
					System.out.println("주문 실패: " + e.getMessage());
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Product product = productRepository.findById(productId).orElseThrow();

		System.out.println("최종 재고: " + product.getQuantity());

		assertThat(product.getQuantity()).isEqualTo(9);
	}
}
