package com.zenevich.bank.tests.api.advanced.steps;

import com.zenevich.bank.tests.config.TestConfig;
import com.zenevich.bank.tests.models.AccountResponse;
import com.zenevich.bank.tests.models.AuthResponse;
import com.zenevich.bank.tests.models.RegisterRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ApiSteps {

    public static AuthResponse register(RegisterRequest request) {
        return given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestConfig.API_GATEWAY_URL + "/api/auth/register")
                .then()
                .statusCode(200)
                .extract()
                .as(AuthResponse.class);
    }

    public static AuthResponse login(String email, String password) {
        String body = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(TestConfig.API_GATEWAY_URL + "/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .as(AuthResponse.class);
    }

    public static AccountResponse createAccount(String token, String currency) {
        String body = String.format("{\"currency\":\"%s\"}", currency);
        return given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(TestConfig.API_GATEWAY_URL + "/api/accounts")
                .then()
                .statusCode(200)
                .extract()
                .as(AccountResponse.class);
    }

    public static Response getAccounts(String token) {
        return given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(TestConfig.API_GATEWAY_URL + "/api/accounts");
    }

    public static void validateJsonSchema(Response response, String schemaPath) {
        response.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new File(schemaPath)));
    }

    public static void assertResponseTime(Response response, long maxMillis) {
        long responseTime = response.time();
        if (responseTime > maxMillis) {
            throw new AssertionError(String.format(
                    "Response time %d ms exceeds maximum %d ms", responseTime, maxMillis
            ));
        }
    }
}