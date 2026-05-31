package com.zenevich.bank.tests.api.advanced.matchers;

import com.zenevich.bank.tests.models.AccountResponse;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class AccountResponseMatchers {

    public static TypeSafeMatcher<AccountResponse> hasPositiveBalance() {
        return new TypeSafeMatcher<>() {
            @Override
            protected boolean matchesSafely(AccountResponse item) {
                return item.getBalance() != null && item.getBalance().doubleValue() > 0;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("account with positive balance");
            }
        };
    }

    public static TypeSafeMatcher<AccountResponse> hasCurrency(String expectedCurrency) {
        return new TypeSafeMatcher<>() {
            @Override
            protected boolean matchesSafely(AccountResponse item) {
                return expectedCurrency.equals(item.getCurrency());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("currency " + expectedCurrency);
            }
        };
    }
}