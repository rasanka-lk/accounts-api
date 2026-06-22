package com.assessment.accounts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.assessment.accounts.validation.NotOffensiveNickname;

public record AccountRequest(
        @NotBlank(message = "Customer name is mandatory")
        @Size(max = 100, message = "Customer name must not exceed 100 characters")
        String customerName,

        @Size(min = 5, max = 30, message = "Account nick name should be between 5 and 30 characters")
        @NotOffensiveNickname
        String accountNickName
) {
}
