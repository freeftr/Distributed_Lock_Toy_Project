package com.market.market.account.domain.repository;

import com.market.market.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepository {
    private final AccountJpaRepository accountJpaRepository;

    public Optional<Account> findById (Long id) {
        return accountJpaRepository.findById(id);
    }
}
