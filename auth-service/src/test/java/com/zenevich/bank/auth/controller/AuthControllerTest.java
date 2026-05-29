package com.zenevich.bank.auth.controller;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Epic("Auth Service")
@Feature("Registration")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Story("User Registration")
    @Description("Should register new user and return 200 OK")
    public void shouldRegisterUser() throws Exception {
        String requestBody = """
                {
                    "email": "testuser@mail.com",
                    "password": "123456",
                    "fullName": "Test User"
                }
                """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }
}