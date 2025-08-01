package com.market.market.product.domain;

import com.market.market.common.entity.BaseEntity;
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

    @Column(name = "product_price", nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status", nullable = false)
    private ProductStatus status;

    @Builder
    public Product(
            String name,
            int price,
            ProductStatus status
    ) {
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public void markSoldOut() {
        this.status = ProductStatus.SOLD_OUT;
    }

    public void markOnSale() {
        this.status = ProductStatus.ON_SALE;
    }
}
