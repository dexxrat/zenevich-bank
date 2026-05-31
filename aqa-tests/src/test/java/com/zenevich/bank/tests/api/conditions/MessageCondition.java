package com.zenevich.bank.tests.api.conditions;

import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;

@RequiredArgsConstructor
public class MessageCondition implements Condition {
    private final String expectedMessage;

    @Override
    public void check(ValidatableResponse response) {
        String actual = response.extract().jsonPath().getString("message");
        Assertions.assertEquals(expectedMessage, actual,
                String.format("Expected message '%s' but got '%s'", expectedMessage, actual));
    }
}