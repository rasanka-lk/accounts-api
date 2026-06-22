package com.assessment.accounts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.assessment.accounts.dto.AccountRequest;
import com.assessment.accounts.dto.AccountResponse;
import com.assessment.accounts.service.SavingsAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Savings Accounts", description = "Savings account management APIs")
@RestController
@RequestMapping("/api/v1/savings-accounts")
@RequiredArgsConstructor
public class SavingsAccountController {

    private final SavingsAccountService savingsAccountService;

    @Operation(summary = "Create a new savings account")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Savings account created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody AccountRequest request) {
        return savingsAccountService.createAccount(request);
    }

    @Operation(summary = "Get a savings account by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Savings account found"),
            @ApiResponse(responseCode = "404", description = "Savings account not found")
    })
    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable UUID id) {
        return savingsAccountService.getAccount(id);
    }

    @Operation(summary = "Get a savings account by account number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Savings account found"),
            @ApiResponse(responseCode = "404", description = "Savings account not found")
    })
    @GetMapping("/account-number/{accountNumber}")
    public AccountResponse getAccountByAccountNumber(@PathVariable String accountNumber) {
        return savingsAccountService.getAccountByAccountNumber(accountNumber);
    }
}
