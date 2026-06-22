package com.assessment.accounts.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public record AccountResponse(
        UUID id,
        String accountNumber,
        String customerName,
        String accountNickName,
        Instant createdAt
) implements Serializable {
}