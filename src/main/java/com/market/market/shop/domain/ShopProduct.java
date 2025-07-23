package com.market.market.shop.domain;

import com.market.market.common.entity.BaseEntity;
import com.market.market.common.exception.BadRequestException;
import com.market.market.common.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_product_id", nullable = false)
    private Long shopProductId;

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "status", nullable = false)
    private ShopProductStatus status;

    public void sold() {
        this.quantity--;
    }

    public void soldOut() {
        if (this.status == ShopProductStatus.SOLD_OUT) {
            throw new BadRequestException(ErrorCode.PRODUCT_ALREADY_SOLD_OUT);
        }
        this.status = ShopProductStatus.SOLD_OUT;
    }

    @Builder
    public ShopProduct(
            Long shopProductId,
            Long shopId,
            Long productId,
            int quantity,
            ShopProductStatus status
    ) {
        this.shopProductId = shopProductId;
        this.shopId = shopId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }
}
