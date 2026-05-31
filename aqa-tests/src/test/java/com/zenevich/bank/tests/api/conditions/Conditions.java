package com.zenevich.bank.tests.api.conditions;

public class Conditions {
    public static StatusCodeCondition hasStatusCode(int code) {
        return new StatusCodeCondition(code);
    }

    public static MessageCondition hasMessage(String message) {
        return new MessageCondition(message);
    }
}