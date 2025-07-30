package com.market.market.order.domain;

import com.market.market.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;

    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Builder
    public Order(
            Long buyerId,
            Long productId,
            OrderStatus orderStatus,
            int totalPrice,
            int amount
    ) {
        this.buyerId = buyerId;
        this.productId = productId;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.amount = amount;
    }

    public boolean isBuyer(Long memberId) {
        return memberId.equals(this.buyerId);
    }

    public void confirm() {
        this.orderStatus = OrderStatus.CONFIRMED;
    }

    public void paid() {
        this.orderStatus = OrderStatus.PAID;
    }

    public void cancel() {
        this.orderStatus = OrderStatus.CANCELLED;
    }
}
