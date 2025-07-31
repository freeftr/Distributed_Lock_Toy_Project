package com.market.market.product.domain;

import com.market.market.common.entity.BaseEntity;
import com.market.market.common.exception.BadRequestException;
import com.market.market.common.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(name = "product_name", nullable = false, length = 512)
    private String name;

    @Column(name = "product_detail", nullable = false, length = 512)
    private String detail;

    @Column(name = "product_price", nullable = false)
    private int price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status", nullable = false)
    private ProductStatus status;

    @Builder
    public Product(
            String name,
            String detail,
            int price,
            int quantity,
            ProductStatus status
    ) {
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public void sold(int amount) {

        this.quantity -= amount;

        if (this.quantity == 0) {
            this.status = ProductStatus.SOLD_OUT;
        }
    }

    public void restock(int amount) {
        if (this.status == ProductStatus.SOLD_OUT) {
            this.status = ProductStatus.ON_SALE;
        }
        this.quantity += amount;
    }
}
