package com.market.market.shop.domain;

import com.market.market.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop extends BaseEntity {

	@Id
	@Column(name = "shop_id", nullable = false)
	private Long id;

	@Column(name = "shop_name", nullable = false)
	private String name;

	@Builder
	public Shop(
			Long id,
			String name
	) {
		this.id = id;
		this.name = name;
	}
}
