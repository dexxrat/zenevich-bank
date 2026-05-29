package com.zenevich.bank.tests.api;

import com.zenevich.bank.tests.config.TestConfig;
import com.zenevich.bank.tests.models.AuthRequest;
import com.zenevich.bank.tests.models.AuthResponse;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Auth Service API Tests")
@Feature("Authentication")
public class AuthApiTests {

    @Test
    @Story("User Login")
    @DisplayName("Should login successfully with valid credentials")
    @Description("Test that user can login and receive JWT token")
    public void testLoginSuccess() {
        AuthRequest request = AuthRequest.builder()
                .email(TestConfig.TEST_EMAIL)
                .password(TestConfig.TEST_PASSWORD)
                .build();

        AuthResponse response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestConfig.AUTH_SERVICE_URL + "/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .as(AuthResponse.class);

        assertThat(response.getToken()).isNotEmpty();
        assertThat(response.getEmail()).isEqualTo(TestConfig.TEST_EMAIL);
        assertThat(response.getFullName()).isEqualTo(TestConfig.TEST_FULL_NAME);
    }

    @Test
    @Story("User Login")
    @DisplayName("Should fail login with invalid password")
    @Description("Test that login fails with wrong password")
    public void testLoginInvalidPassword() {
        AuthRequest request = AuthRequest.builder()
                .email(TestConfig.TEST_EMAIL)
                .password("wrongpassword")
                .build();

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestConfig.AUTH_SERVICE_URL + "/api/auth/login")
                .then().statusCode(403);
    }

    @Test
    @Story("User Profile")
    @DisplayName("Should get current user profile")
    @Description("Test that authenticated user can get their profile")
    public void testGetCurrentUser() {
        String token = com.zenevich.bank.tests.utils.TokenManager.getToken();

        AuthResponse response = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(TestConfig.AUTH_SERVICE_URL + "/api/auth/me")
                .then()
                .statusCode(200)
                .extract()
                .as(AuthResponse.class);

        assertThat(response.getEmail()).isEqualTo(TestConfig.TEST_EMAIL);
    }
}