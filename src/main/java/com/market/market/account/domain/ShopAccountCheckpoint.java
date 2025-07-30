package com.market.market.account.domain;

import com.market.market.common.entity.BaseEntity;
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
public class ShopAccountCheckpoint extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shop_account_check_point_id", nullable = false)
	private Long id;

	@Column(name = "shop_id", nullable = false)
	private Long shopId;

	@Column(name = "last_updated_id", nullable = false)
	private Long lastUpdatedId;

	@Builder
	public ShopAccountCheckpoint (
			Long id,
			Long shopId,
			Long lastUpdatedId
	) {
		this.id = id;
		this.shopId = shopId;
		this.lastUpdatedId = lastUpdatedId;
	}

}
