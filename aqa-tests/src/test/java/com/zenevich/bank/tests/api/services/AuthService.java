package com.zenevich.bank.tests.api.services;

import com.zenevich.bank.tests.api.assertions.AssertableResponse;
import com.zenevich.bank.tests.models.AuthRequest;
import com.zenevich.bank.tests.models.RegisterRequest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class AuthService {
    private final String baseUrl;

    public AuthService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public AssertableResponse register(RegisterRequest request) {
        return new AssertableResponse(
                given()
                        .contentType(ContentType.JSON)
                        .body(request)
                        .post(baseUrl + "/api/auth/register")
                        .then()
        );
    }

    public AssertableResponse login(AuthRequest request) {
        return new AssertableResponse(
                given()
                        .contentType(ContentType.JSON)
                        .body(request)
                        .post(baseUrl + "/api/auth/login")
                        .then()
        );
    }

    public AssertableResponse getProfile(String token) {
        return new AssertableResponse(
                given()
                        .header("Authorization", "Bearer " + token)
                        .get(baseUrl + "/api/auth/me")
                        .then()
        );
    }
}