package com.market.market.transaction.domain.repository;

import com.market.market.transaction.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionJdbcRepository {

	private final JdbcTemplate jdbcTemplate;

	public void batchInsert(List<Transaction> transactions) {
		String sql = "INSERT INTO transaction (sender_account_id, receiver_account_id, amount, order_id, created_at, modified_at)"
				+ "VALUES (?, ?, ?, ?, NOW(), NOW()";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int idx) throws SQLException {
				Transaction transaction = transactions.get(idx);
				ps.setLong(1, transaction.getSenderAccountId());
				ps.setLong(2, transaction.getReceiverAccountId());
				ps.setInt(3, transaction.getAmount());
				ps.setLong(4, transaction.getOrderId());
			}

			@Override
			public int getBatchSize() {
				return transactions.size();
			}
		});
	}
}
