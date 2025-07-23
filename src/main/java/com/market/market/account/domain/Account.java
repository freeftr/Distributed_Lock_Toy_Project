package com.market.market.account.domain;

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
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Long id;

    @Column(name = "account_owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "balance", nullable = false)
    private int balance;

    @Builder
    public Account(
            Long ownerId,
            int balance
    ) {
        this.ownerId = ownerId;
        this.balance = balance;
    }

    public boolean checkOwner(Long id) {
        return this.ownerId == id;
    }

    public int deposit(int amount) {
        this.balance += amount;
        return this.balance;
    }

    public int withdraw(int amount) {
        if (this.balance - amount < 0) {
            throw new BadRequestException(ErrorCode.NOT_ENOUGH_BALANCE);
        }
        this.balance -= amount;
        return this.balance;
    }
}
