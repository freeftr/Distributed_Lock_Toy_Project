package com.market.market.account.domain.repository;

import com.market.market.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {
	@Query("""
			SELECT a
			FROM Account a
			WHERE a.ownerId = :memberId
	""")
	Optional<Account> findByMemberId(Long memberId);
}
