package com.market.market.shop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_name")
    private String name;

    @Column(name = "owner_id")
    private Long ownerId;

    @Builder
    public Shop(
            Long id,
            String name,
            Long ownerId
    ) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
    }
}
