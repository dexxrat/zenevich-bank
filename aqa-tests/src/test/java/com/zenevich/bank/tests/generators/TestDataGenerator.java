package com.zenevich.bank.tests.generators;

import com.github.javafaker.Faker;

public class TestDataGenerator {
    private static final Faker faker = new Faker();

    public static String randomEmail() {
        return faker.internet().emailAddress();
    }

    public static String randomPassword() {
        return faker.internet().password(6, 12, true, true, true);
    }

    public static String randomFullName() {
        return faker.name().fullName();
    }

    public static String randomCurrency() {
        String[] currencies = {"USD", "EUR", "GBP", "CHF", "JPY"};
        return currencies[faker.random().nextInt(currencies.length)];
    }

    public static double randomAmount() {
        return faker.number().randomDouble(2, 10, 10000);
    }
}