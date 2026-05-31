package com.zenevich.bank.tests.api.advanced.tests;

import com.zenevich.bank.tests.api.advanced.builders.RegisterRequestBuilder;
import com.zenevich.bank.tests.models.AccountResponse;
import com.zenevich.bank.tests.models.AuthResponse;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static com.zenevich.bank.tests.api.advanced.steps.ApiSteps.*;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("API Testing")
@Feature("Advanced API Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdvancedApiTests {

    private static String token;
    private static String accountId;

    @Test
    @Order(1)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Register user with dynamically generated data using Builder pattern")
    @DisplayName("Should register user with random data")
    public void testRegisterWithBuilder() {
        var request = RegisterRequestBuilder.aRandomUser()
                .withPassword("Test123!")
                .build();

        AuthResponse response = register(request);

        assertThat(response.getToken()).isNotNull();
        assertThat(response.getUserId()).isNotNull();
        assertThat(response.getEmail()).isNotNull();
        assertThat(response.getFullName()).isNotNull();

        token = response.getToken();
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Test response time - API performance check")
    @DisplayName("Should respond within 2000ms")
    public void testResponseTime() {
        Response response = getAccounts(token);
        assertResponseTime(response, 2000);
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Description("Validate JSON Schema for account response")
    @DisplayName("Should match JSON schema")
    public void testJsonSchemaValidation() {
        AccountResponse account = createAccount(token, "USD");
        accountId = account.getId();

        Response response = getAccounts(token);
        validateJsonSchema(response, "src/test/resources/schemas/account-response-schema.json");
    }

    @Test
    @Order(4)
    @Severity(SeverityLevel.CRITICAL)
    @Description("End-to-end flow: register → login → create account → verify")
    @DisplayName("Complete API flow should work correctly")
    public void testFullApiFlow() {
        // 1. Register new user
        var registerRequest = RegisterRequestBuilder.aRandomUser().build();
        AuthResponse authResponse = register(registerRequest);
        assertThat(authResponse.getToken()).isNotEmpty();

        // 2. Login with registered user
        AuthResponse loginResponse = login(registerRequest.getEmail(), registerRequest.getPassword());
        assertThat(loginResponse.getToken()).isNotEmpty();

        // 3. Create new account
        AccountResponse account = createAccount(loginResponse.getToken(), "EUR");
        assertThat(account.getId()).isNotEmpty();
        assertThat(account.getCurrency()).isEqualTo("EUR");
        assertThat(account.getBalance()).isEqualTo(BigDecimal.ZERO);
    }
}