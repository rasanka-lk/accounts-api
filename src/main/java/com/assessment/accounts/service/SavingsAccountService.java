package com.assessment.accounts.service;

import lombok.RequiredArgsConstructor;
import com.assessment.accounts.dto.AccountRequest;
import com.assessment.accounts.dto.AccountResponse;
import com.assessment.accounts.entity.SavingsAccount;
import com.assessment.accounts.exception.AccountLimitExceededException;
import com.assessment.accounts.exception.AccountNotFoundException;
import com.assessment.accounts.exception.DatabaseUnavailableException;
import com.assessment.accounts.repository.SavingsAccountRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SavingsAccountService {

    private static final int MAX_ACCOUNTS_PER_CUSTOMER = 5;

    private final SavingsAccountRepository repository;
    private final AccountNumberGenerator accountNumberGenerator;

    @Transactional
    @CacheEvict(value = "savingsAccounts", allEntries = true)
    public AccountResponse createAccount(AccountRequest request) {
        try {
            String customerName = request.customerName().trim();
            long existingAccounts = repository.countByCustomerNameIgnoreCase(customerName);

            if (existingAccounts >= MAX_ACCOUNTS_PER_CUSTOMER) {
                throw new AccountLimitExceededException("A customer cannot create more than 5 savings accounts");
            }

            SavingsAccount account = SavingsAccount.builder()
                    .accountNumber(accountNumberGenerator.generateUniqueAccountNumber())
                    .customerName(customerName)
                    .accountNickName(request.accountNickName())
                    .build();

            return toResponse(repository.save(account));
        } catch (DataAccessException ex) {
            throw new DatabaseUnavailableException("Database is currently unavailable", ex);
        }
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "savingsAccounts", key = "#id")
    public AccountResponse getAccount(UUID id) {
        try {
            SavingsAccount account = repository.findById(id)
                    .orElseThrow(() -> new AccountNotFoundException("Savings account not found"));
            return toResponse(account);
        } catch (DataAccessException ex) {
            throw new DatabaseUnavailableException("Database is currently unavailable", ex);
        }
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "savingsAccountsByNumber", key = "#accountNumber")
    public AccountResponse getAccountByAccountNumber(String accountNumber) {
        try {
            SavingsAccount account = repository.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new AccountNotFoundException("Savings account not found"));
            return toResponse(account);
        } catch (DataAccessException ex) {
            throw new DatabaseUnavailableException("Database is currently unavailable", ex);
        }
    }

    private AccountResponse toResponse(SavingsAccount account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getCustomerName(),
                account.getAccountNickName(),
                account.getCreatedAt()
        );
    }
}
