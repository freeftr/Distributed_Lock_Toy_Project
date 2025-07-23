package com.market.market.order.domain;

import com.market.market.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Builder
    public Order(
            Long memberId,
            Long shopId,
            OrderStatus orderStatus,
            int totalPrice
    ) {
        this.memberId = memberId;
        this.shopId = shopId;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }

    public void paid() {
        this.orderStatus = OrderStatus.PAID;
    }

    public void cancel() {
        this.orderStatus = OrderStatus.CANCELLED;
    }
}
