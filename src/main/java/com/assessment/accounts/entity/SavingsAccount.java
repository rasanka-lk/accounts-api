package com.assessment.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "savings_accounts", indexes = {
        @Index(name = "idx_savings_accounts_customer_name", columnList = "customer_name"),
        @Index(name = "idx_savings_accounts_account_number", columnList = "account_number", unique = true)
})
public class SavingsAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "account_number", nullable = false, unique = true, updatable = false, length = 20)
    private String accountNumber;

    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @Column(name = "account_nick_name", length = 30)
    private String accountNickName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
