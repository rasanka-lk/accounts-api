package com.assessment.accounts.repository;

import com.assessment.accounts.entity.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, UUID> {

    long countByCustomerNameIgnoreCase(String customerName);

    boolean existsByAccountNumber(String accountNumber);

    Optional<SavingsAccount> findByAccountNumber(String accountNumber);
}
