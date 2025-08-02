package com.market.market.product.domain;

import com.market.market.common.entity.BaseEntity;
import com.market.market.common.exception.BadRequestException;
import com.market.market.common.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductQuantity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_quantity_id", nullable = false)
	private Long id;

	@Column(name = "product_id", nullable = false)
	private Long productId;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Builder
	public ProductQuantity(
			Long productId,
			Integer quantity
	) {
		this.productId = productId;
		this.quantity = quantity;
	}

	public void decrease(int amount) {
		if (this.quantity < amount) {
			throw new BadRequestException(ErrorCode.NOT_ENOUGH_STOCK);
		}
		this.quantity -= amount;
	}

	public void increase(int amount) {
		this.quantity += amount;
	}

	public boolean isSoldOut() {
		return this.quantity == 0;
	}
}
