package com.market.market.transaction.domain;

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
public class Transaction extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(name = "sender_account_id", nullable = false)
	private Long senderAccountId;

	@Column(name = "receiver_account_id", nullable = false)
	private Long receiverAccountId;

	@Column(name = "amount", nullable = false)
	private int amount;

	@Column(name = "order_id", nullable = false)
	private Long orderId;

	@Builder
	public Transaction(
			Long senderAccountId,
			Long receiverAccountId,
			int amount,
			Long orderId
	) {
		this.senderAccountId = senderAccountId;
		this.receiverAccountId = receiverAccountId;
		this.amount = amount;
		this.orderId = orderId;
	}
}
