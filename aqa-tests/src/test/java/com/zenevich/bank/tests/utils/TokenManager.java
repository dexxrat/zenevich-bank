package com.zenevich.bank.tests.utils;

import com.zenevich.bank.tests.config.TestConfig;
import com.zenevich.bank.tests.models.AuthRequest;
import com.zenevich.bank.tests.models.AuthResponse;
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
        AuthRequest loginRequest = AuthRequest.builder()
                .email(TestConfig.TEST_EMAIL)
                .password(TestConfig.TEST_PASSWORD)
                .build();

        System.out.println("Attempting to login with: " + TestConfig.TEST_EMAIL);

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post(TestConfig.AUTH_SERVICE_URL + "/api/auth/login")
                .then()
                .extract()
                .response();

        System.out.println("Login response status: " + response.getStatusCode());
        System.out.println("Login response body: " + response.asString());

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to get token. Status: " + response.getStatusCode() +
                    ", Body: " + response.asString());
        }

        AuthResponse authResponse = response.as(AuthResponse.class);
        return authResponse.getToken();
    }

    public static void clearToken() {
        cachedToken = null;
    }
}