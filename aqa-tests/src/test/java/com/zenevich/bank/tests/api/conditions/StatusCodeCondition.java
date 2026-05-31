package com.zenevich.bank.tests.api.conditions;

import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;

@RequiredArgsConstructor
public class StatusCodeCondition implements Condition {
    private final int expectedStatusCode;

    @Override
    public void check(ValidatableResponse response) {
        int actual = response.extract().statusCode();
        Assertions.assertEquals(expectedStatusCode, actual,
                String.format("Expected status code %d but got %d", expectedStatusCode, actual));
    }
}