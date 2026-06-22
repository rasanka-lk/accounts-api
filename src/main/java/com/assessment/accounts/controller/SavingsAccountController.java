package com.assessment.accounts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.assessment.accounts.dto.AccountRequest;
import com.assessment.accounts.dto.AccountResponse;
import com.assessment.accounts.service.SavingsAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/savings-accounts")
@RequiredArgsConstructor
public class SavingsAccountController {

    private final SavingsAccountService savingsAccountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody AccountRequest request) {
        return savingsAccountService.createAccount(request);
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable UUID id) {
        return savingsAccountService.getAccount(id);
    }

    @GetMapping("/account-number/{accountNumber}")
    public AccountResponse getAccountByAccountNumber(@PathVariable String accountNumber) {
        return savingsAccountService.getAccountByAccountNumber(accountNumber);
    }
}
