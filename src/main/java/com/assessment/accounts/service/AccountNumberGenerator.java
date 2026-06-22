package com.assessment.accounts.service;

import lombok.RequiredArgsConstructor;
import com.assessment.accounts.repository.SavingsAccountRepository;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class AccountNumberGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();
    private final SavingsAccountRepository repository;

    /**
     * Simple demo account number format: 12 numeric digits.
     * @return accountNumber
     */
    public String generateUniqueAccountNumber() {
        // TODO: Update account number generation logic
        for (int attempt = 0; attempt < 10; attempt++) {
            String accountNumber = String.valueOf(100000000000L + Math.abs(RANDOM.nextLong() % 900000000000L));
            if (!repository.existsByAccountNumber(accountNumber)) {
                return accountNumber;
            }
        }
        throw new IllegalStateException("Unable to generate a unique account number");
    }
}
