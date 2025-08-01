package com.market.market.order.application;

import com.market.market.member.domain.Member;
import com.market.market.member.domain.repository.MemberRepository;
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
class OrderServiceTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductQuantityRepository productQuantityRepository;

	@Autowired
	private MemberRepository memberRepository;

	private Long productId;
	private Long buyerId;

	@BeforeEach
	void setUp() {
		Member member = Member.builder()
				.name("박종하")
				.build();
		buyerId = memberRepository.save(member).getId();

		Product product = Product.builder()
				.name("테스트 상품")
				.price(1000)
				.status(ProductStatus.ON_SALE)
				.build();

		productId = productRepository.save(product).getId();

		ProductQuantity quantity = ProductQuantity.builder()
				.productId(productId)
				.quantity(100)
				.build();

		productQuantityRepository.save(quantity);
	}

	@Test
	void 동시에_100개의_주문_AOP() throws InterruptedException {
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					OrderRequest request = new OrderRequest(buyerId, 1);
					orderService.orderAopLock(productId, request);
				} catch (Exception e) {
					log.error("AOP 주문 실패: {}", e.getMessage());
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		ProductQuantity productQuantity = productQuantityRepository.findByProductId(productId)
				.orElseThrow();

		log.info("AOP 최종 재고: {}", productQuantity.getQuantity());
		assertThat(productQuantity.getQuantity()).isEqualTo(0);
	}

	@Test
	void 동시에_100개의_주문_FunctionalLock() throws InterruptedException {
		ProductQuantity quantity = productQuantityRepository.findByProductId(productId)
				.orElseThrow();
		productQuantityRepository.save(quantity);

		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					OrderRequest request = new OrderRequest(buyerId, 1);
					orderService.orderFunctionalLock(productId, request);
				} catch (Exception e) {
					log.error("Functional 주문 실패: {}", e.getMessage());
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		ProductQuantity productQuantity = productQuantityRepository.findByProductId(productId)
				.orElseThrow();

		log.info("Functional 최종 재고: {}", productQuantity.getQuantity());
		assertThat(productQuantity.getQuantity()).isEqualTo(0);
	}
}
