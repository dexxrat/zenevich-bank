package com.zenevich.bank.tests.api;

import com.zenevich.bank.tests.config.TestConfig;
import com.zenevich.bank.tests.models.AccountResponse;
import com.zenevich.bank.tests.models.CreateAccountRequest;
import com.zenevich.bank.tests.utils.TokenManager;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Account Service API Tests")
@Feature("Account Management")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountApiTests {

    private static String createdAccountId;

    @Test
    @Order(1)
    @Story("Create Account")
    @DisplayName("Should create new account successfully")
    @Description("Test that authenticated user can create a new bank account")
    public void testCreateAccount() {
        String token = TokenManager.getToken();

        CreateAccountRequest request = CreateAccountRequest.builder()
                .currency("EUR")
                .build();

        AccountResponse response = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestConfig.ACCOUNT_SERVICE_URL + "/api/accounts")
                .then()
                .statusCode(200)
                .extract()
                .as(AccountResponse.class);

        assertThat(response.getId()).isNotEmpty();
        assertThat(response.getAccountNumber()).isNotEmpty();
        assertThat(response.getBalance()).isEqualTo(BigDecimal.ZERO);  // ← ИСПРАВЛЕНО
        assertThat(response.getCurrency()).isEqualTo("EUR");

        createdAccountId = response.getId();  // ← ДОБАВЛЕНО
    }

    @Test
    @Order(2)
    @Story("Get Accounts")
    @DisplayName("Should get all accounts for current user")
    public void testGetMyAccounts() {
        String token = TokenManager.getToken();

        List<AccountResponse> accounts = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(TestConfig.ACCOUNT_SERVICE_URL + "/api/accounts")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", AccountResponse.class);

        assertThat(accounts).isNotEmpty();
        assertThat(accounts).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    @Order(3)
    @Story("Get Account By ID")
    @DisplayName("Should get specific account by ID")
    public void testGetAccountById() {
        assertThat(createdAccountId).isNotNull();

        String token = TokenManager.getToken();

        AccountResponse response = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(TestConfig.ACCOUNT_SERVICE_URL + "/api/accounts/" + createdAccountId)
                .then()
                .statusCode(200)
                .extract()
                .as(AccountResponse.class);

        assertThat(response.getId()).isEqualTo(createdAccountId);
        assertThat(response.getCurrency()).isEqualTo("EUR");
    }

    @Test
    @Order(4)
    @Story("Create Account")
    @DisplayName("Should create USD account")
    public void testCreateUSDAccount() {
        String token = TokenManager.getToken();

        CreateAccountRequest request = CreateAccountRequest.builder()
                .currency("USD")
                .build();

        AccountResponse response = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestConfig.ACCOUNT_SERVICE_URL + "/api/accounts")
                .then()
                .statusCode(200)
                .extract()
                .as(AccountResponse.class);

        assertThat(response.getCurrency()).isEqualTo("USD");
        assertThat(response.getBalance()).isEqualTo(BigDecimal.ZERO);
    }
}