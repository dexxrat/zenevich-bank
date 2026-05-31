package com.zenevich.bank.tests.listeners;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class RetryListener implements TestExecutionExceptionHandler {
    private static final int MAX_RETRIES = 3;
    private int attempt = 1;

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        if (attempt <= MAX_RETRIES) {
            System.out.println("⚠ Test failed. Retrying (" + attempt + "/" + MAX_RETRIES + ")");
            attempt++;
            return;
        }
        throw throwable;
    }
}