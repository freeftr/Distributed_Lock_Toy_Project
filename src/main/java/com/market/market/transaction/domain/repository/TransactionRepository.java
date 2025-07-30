package com.market.market.transaction.domain.repository;

import com.market.market.transaction.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {
	private final TransactionJpaRepository transactionJpaRepository;
	private final TransactionJdbcRepository transactionJdbcRepository;

	public Transaction save(Transaction transaction) {
		return transactionJpaRepository.save(transaction);
	}

	public void bulkInsert(List<Transaction> transactions) {
		transactionJdbcRepository.batchInsert(transactions);
	}
}
