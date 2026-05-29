package com.zenevich.bank.tests.utils;

import com.zenevich.bank.tests.config.TestConfig;
import com.zenevich.bank.tests.models.AuthRequest;
import com.zenevich.bank.tests.models.AuthResponse;
import com.zenevich.bank.tests.models.RegisterRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TokenManager {
    private static String cachedToken;

    public static String getToken() {
        if (cachedToken == null) {
            cachedToken = fetchNewToken();
        }
        return cachedToken;
    }

    public static String fetchNewToken() {
        ensureUserExists();

        AuthRequest loginRequest = AuthRequest.builder()
                .email(TestConfig.TEST_EMAIL)
                .password(TestConfig.TEST_PASSWORD)
                .build();

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post(TestConfig.AUTH_SERVICE_URL + "/api/auth/login")
                .then()
                .extract()
                .response();

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to get token. Status: " + response.getStatusCode() +
                    ", Body: " + response.asString());
        }

        AuthResponse authResponse = response.as(AuthResponse.class);
        return authResponse.getToken();
    }

    private static void ensureUserExists() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email(TestConfig.TEST_EMAIL)
                .password(TestConfig.TEST_PASSWORD)
                .fullName(TestConfig.TEST_FULL_NAME)
                .build();

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(registerRequest)
                .when()
                .post(TestConfig.AUTH_SERVICE_URL + "/api/auth/register");

        if (response.getStatusCode() == 200) {
            System.out.println("✓ Test user registered: " + TestConfig.TEST_EMAIL);
        } else if (response.getStatusCode() == 400 && response.asString().contains("already exists")) {
            System.out.println("✓ Test user already exists: " + TestConfig.TEST_EMAIL);
        } else if (response.getStatusCode() == 403 || response.getStatusCode() == 409) {
            System.out.println("✓ Test user already exists (already registered): " + TestConfig.TEST_EMAIL);
        } else if (response.getStatusCode() == 500) {
            System.out.println("✓ User probably already exists (server error ignored): " + TestConfig.TEST_EMAIL);
        } else if (response.getStatusCode() != 200) {
            System.out.println("⚠ Registration response: " + response.getStatusCode() + " - " + response.asString());
        }
    }

    public static void clearToken() {
        cachedToken = null;
    }
}