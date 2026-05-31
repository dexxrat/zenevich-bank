package com.zenevich.bank.tests.utils;

import com.github.javafaker.Faker;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {
    private static final Faker faker = new Faker(new Locale("en"));

    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static String getRandomPassword() {
        return faker.internet().password(6, 12, true, true, true);
    }

    public static String getRandomFullName() {
        return faker.name().fullName();
    }

    public static String getRandomCurrency() {
        String[] currencies = {"USD", "EUR", "GBP", "JPY", "CHF"};
        return currencies[ThreadLocalRandom.current().nextInt(currencies.length)];
    }

    public static double getRandomAmount() {
        return ThreadLocalRandom.current().nextDouble(10, 10000);
    }
}