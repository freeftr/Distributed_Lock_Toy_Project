package com.market.market.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_price", nullable = false)
    private Integer price;


    @Builder
    public Product(
            Long id,
            Integer price
    ) {
        this.id = id;
        this.price = price;
    }
}
