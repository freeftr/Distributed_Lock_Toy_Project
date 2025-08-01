package com.market.market.order.application;

import com.market.market.order.domain.repository.OrderRepository;
import com.market.market.order.dto.request.OrderRequest;
import com.market.market.product.domain.Product;
import com.market.market.product.domain.ProductQuantity;
import com.market.market.product.domain.ProductStatus;
import com.market.market.product.domain.repository.ProductQuantityRepository;
import com.market.market.product.domain.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class OrderServiceConcurrencyTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductQuantityRepository productQuantityRepository;

	@Autowired
	private OrderRepository orderRepository;

	private Long productId;

	@BeforeEach
	void setUp() {
		Product product = Product.builder()
				.name("테스트 상품")
				.price(1000)
				.status(ProductStatus.ON_SALE)
				.build();

		productId = productRepository.save(product).getId();

		ProductQuantity quantity = ProductQuantity.builder()
				.productId(productId)
				.quantity(1)
				.build();

		productQuantityRepository.save(quantity);
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
					log.error("주문 실패 " + e.getMessage(), e);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		ProductQuantity productQuantity = productQuantityRepository.findByProductId(productId)
				.orElseThrow();

		assertThat(productQuantity.getQuantity()).isEqualTo(0);
	}
}
