package com.zenevich.bank.tests.api.advanced.builders;

import com.zenevich.bank.tests.models.RegisterRequest;
import com.github.javafaker.Faker;

public class RegisterRequestBuilder {
    private static final Faker faker = new Faker();
    private String email;
    private String password;
    private String fullName;

    public RegisterRequestBuilder withRandomEmail() {
        this.email = faker.internet().emailAddress();
        return this;
    }

    public RegisterRequestBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public RegisterRequestBuilder withRandomPassword() {
        this.password = faker.internet().password(6, 12, true, true, true);
        return this;
    }

    public RegisterRequestBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public RegisterRequestBuilder withRandomFullName() {
        this.fullName = faker.name().fullName();
        return this;
    }

    public RegisterRequestBuilder withFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public RegisterRequest build() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail(email);
        request.setPassword(password);
        request.setFullName(fullName);
        return request;
    }

    public static RegisterRequestBuilder aRandomUser() {
        return new RegisterRequestBuilder()
                .withRandomEmail()
                .withRandomPassword()
                .withRandomFullName();
    }
}