package com.assessment.accounts.controller;

import com.assessment.accounts.dto.AccountRequest;
import com.assessment.accounts.repository.SavingsAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import tools.jackson.databind.json.JsonMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SavingsAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @Autowired
    private SavingsAccountRepository repository;

    @Test
    void createAccountShouldReturnCreatedAccount() throws Exception {
        AccountRequest request = new AccountRequest("Jane Smith", "Travel Fund");

        mockMvc.perform(post("/api/v1/savings-accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.accountNumber").exists())
                .andExpect(jsonPath("$.customerName").value("Jane Smith"))
                .andExpect(jsonPath("$.accountNickName").value("Travel Fund"));
    }

    @Test
    void createAccountShouldValidateMandatoryCustomerName() throws Exception {
        AccountRequest request = new AccountRequest("", "Travel Fund");

        mockMvc.perform(post("/api/v1/savings-accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Customer name is mandatory")));
    }

    @Test
    void createAccountShouldValidateNicknameLength() throws Exception {
        AccountRequest request = new AccountRequest("Jane Smith", "abc");

        mockMvc.perform(post("/api/v1/savings-accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("between 5 and 30")));
    }

    @Test
    void createAccountShouldRejectOffensiveNickname() throws Exception {
        AccountRequest request = new AccountRequest("Jane Smith", "badword account");

        mockMvc.perform(post("/api/v1/savings-accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("offensive")));
    }

    @Test
    void getAccountShouldReturnNotFoundForUnknownAccount() throws Exception {
        mockMvc.perform(get("/api/v1/savings-accounts/00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isNotFound());
    }
}
