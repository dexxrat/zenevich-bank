package com.zenevich.bank.tests.assertions;

import com.zenevich.bank.tests.models.AccountResponse;
import org.assertj.core.api.AbstractAssert;

public class CustomAssertions extends AbstractAssert<CustomAssertions, AccountResponse> {

    public CustomAssertions(AccountResponse actual) {
        super(actual, CustomAssertions.class);
    }

    public static CustomAssertions assertThat(AccountResponse actual) {
        return new CustomAssertions(actual);
    }

    public CustomAssertions hasPositiveBalance() {
        if (actual.getBalance() == null || actual.getBalance().doubleValue() < 0) {
            failWithMessage("Expected balance to be positive but was %s", actual.getBalance());
        }
        return this;
    }

    public CustomAssertions hasCurrency(String expectedCurrency) {
        if (!actual.getCurrency().equals(expectedCurrency)) {
            failWithMessage("Expected currency to be %s but was %s", expectedCurrency, actual.getCurrency());
        }
        return this;
    }
}