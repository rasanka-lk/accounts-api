package com.assessment.accounts.repository;

import com.assessment.accounts.entity.SavingsAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SavingsAccountRepositoryTest {

    @Autowired
    private SavingsAccountRepository repository;

    @TestConfiguration
    static class TestCacheConfig {
        @Bean
        CacheManager cacheManager() {
            return new ConcurrentMapCacheManager();
        }
    }

    @Test
    @DisplayName("Should find savings account by account number")
    void shouldFindByAccountNumber() {
        SavingsAccount account = new SavingsAccount();
        account.setAccountNumber("ACC-100001");
        account.setCustomerName("John Smith");
        account.setAccountNickName("Holiday Fund");
        account.setCreatedAt(Instant.now());

        repository.saveAndFlush(account);

        Optional<SavingsAccount> result =
                repository.findByAccountNumber("ACC-100001");

        assertThat(result).isPresent();
        assertThat(result.get().getCustomerName()).isEqualTo("John Smith");
        assertThat(result.get().getAccountNickName()).isEqualTo("Holiday Fund");
    }

    @Test
    @DisplayName("Should return empty when account number does not exist")
    void shouldReturnEmptyWhenAccountNumberDoesNotExist() {
        Optional<SavingsAccount> result =
                repository.findByAccountNumber("ACC-999999");

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should count accounts by customer name")
    void shouldCountAccountsByCustomerName() {
        repository.save(createAccount("ACC-100001", "John Smith", "Holiday Fund"));
        repository.save(createAccount("ACC-100002", "John Smith", "Car Fund"));
        repository.save(createAccount("ACC-100003", "Jane Smith", "Travel Fund"));

        long count = repository.countByCustomerNameIgnoreCase("John Smith");

        assertThat(count).isEqualTo(2);
    }

    private SavingsAccount createAccount(
            String accountNumber,
            String customerName,
            String nickName
    ) {
        SavingsAccount account = new SavingsAccount();
        account.setAccountNumber(accountNumber);
        account.setCustomerName(customerName);
        account.setAccountNickName(nickName);
        account.setCreatedAt(Instant.now());
        return account;
    }
}