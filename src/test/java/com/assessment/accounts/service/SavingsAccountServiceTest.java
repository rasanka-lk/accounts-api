    package com.assessment.accounts.service;

import com.assessment.accounts.dto.AccountRequest;
import com.assessment.accounts.exception.AccountLimitExceededException;
import com.assessment.accounts.repository.SavingsAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SavingsAccountServiceTest {

    @Mock
    private SavingsAccountRepository repository;

    @Mock
    private AccountNumberGenerator accountNumberGenerator;

    @InjectMocks
    private SavingsAccountService service;

    @Test
    void createAccountShouldRejectWhenCustomerAlreadyHasFiveAccounts() {
        when(repository.countByCustomerNameIgnoreCase("John Smith")).thenReturn(5L);

        AccountRequest request = new AccountRequest("John Smith", "Holiday Fund");

        assertThatThrownBy(() -> service.createAccount(request))
                .isInstanceOf(AccountLimitExceededException.class)
                .hasMessageContaining("cannot create more than 5");
    }
}
