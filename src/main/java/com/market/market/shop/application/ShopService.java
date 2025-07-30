package com.market.market.shop.application;

import com.market.market.product.domain.repository.ProductRepository;
import com.market.market.product.dto.query.ProductDetail;
import com.market.market.shop.domain.Shop;
import com.market.market.shop.domain.repository.ShopProductRepository;
import com.market.market.shop.domain.repository.ShopRepository;
import com.market.market.shop.dto.request.ShopCreateRequest;
import com.market.market.shop.dto.response.ShopProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {
	private final ShopRepository shopRepository;
	private final ShopProductRepository shopProductRepository;
	private final ProductRepository productRepository;

	public void createShop(ShopCreateRequest request) {
		Shop shop = Shop.builder()
				.name(request.shopName())
				.ownerId(request.ownerId())
				.build();

		shopRepository.save(shop);
	}

	public List<ShopProductResponse> getShopProducts(Long shopId) {
		List<Long> shopProductIds = shopProductRepository.getProductIdsByShopId(shopId);
		List<ProductDetail> productDetails = productRepository.getProductDetailsIn(shopProductIds);

		return productDetails.stream()
				.map(detail -> new ShopProductResponse(
						shopId,
						detail.productId(),
						detail.productName(),
						detail.price()
				))
				.toList();
	}
}
