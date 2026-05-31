package com.zenevich.bank.tests.listeners;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import java.util.concurrent.atomic.AtomicInteger;

public class RetryAnalyzer implements TestExecutionExceptionHandler {
    private static final int MAX_RETRIES = 3;
    private final AtomicInteger attempt = new AtomicInteger(0);

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        int currentAttempt = attempt.incrementAndGet();
        if (currentAttempt <= MAX_RETRIES) {
            System.out.println("⚠ Test failed. Retrying (" + currentAttempt + "/" + MAX_RETRIES + ")");
            return; // ретрай
        }
        throw throwable;
    }
}