package com.market.market.product.domain;

import com.market.market.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
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

    @Builder
    public Product(
            Long id,
            String name,
            String detail,
            int price
    ) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.price = price;
    }
}
