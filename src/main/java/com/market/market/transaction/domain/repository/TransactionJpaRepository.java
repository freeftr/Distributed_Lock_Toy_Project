package com.market.market.transaction.domain.repository;

import com.market.market.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {
}
