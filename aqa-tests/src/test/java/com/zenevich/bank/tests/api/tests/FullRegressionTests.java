package com.zenevich.bank.tests.api.tests;

import com.zenevich.bank.tests.api.assertions.AssertableResponse;
import com.zenevich.bank.tests.api.conditions.Conditions;
import com.zenevich.bank.tests.api.services.AuthService;
import com.zenevich.bank.tests.generators.TestDataGenerator;
import com.zenevich.bank.tests.listeners.RetryListener;
import com.zenevich.bank.tests.models.AuthRequest;
import com.zenevich.bank.tests.models.RegisterRequest;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Zenevich Bank")
@Feature("Full Regression Suite")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(RetryListener.class)
public class FullRegressionTests {
    private static AuthService authService;
    private static String authToken;
    private static String userId;

    @BeforeAll
    public static void setUp() {
        authService = new AuthService("http://localhost:8080");
    }

    @Test
    @Order(1)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Register new user with valid data")
    @DisplayName("TC-001: User Registration - Positive")
    public void testRegisterUser() {
        RegisterRequest request = RegisterRequest.builder()
                .email(TestDataGenerator.randomEmail())
                .password(TestDataGenerator.randomPassword())
                .fullName(TestDataGenerator.randomFullName())
                .build();

        AssertableResponse response = authService.register(request);
        response.should(Conditions.hasStatusCode(200));

        userId = response.asJwt(); // placeholder
        assertThat(userId).isNotNull();
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Login with valid credentials")
    @DisplayName("TC-002: User Login - Positive")
    public void testLoginUser() {
        AuthRequest request = AuthRequest.builder()
                .email("testauto@mail.com")
                .password("123456")
                .build();

        AssertableResponse response = authService.login(request);
        response.should(Conditions.hasStatusCode(200));

        authToken = response.asJwt();
        assertThat(authToken).isNotNull();
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Description("Get user profile with valid token")
    @DisplayName("TC-003: Get Profile - Positive")
    public void testGetProfile() {
        AssertableResponse response = authService.getProfile(authToken);
        response.should(Conditions.hasStatusCode(200));
    }

    @Test
    @Order(4)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Login with invalid password")
    @DisplayName("TC-004: User Login - Negative (Wrong Password)")
    public void testLoginInvalidPassword() {
        AuthRequest request = AuthRequest.builder()
                .email("testauto@mail.com")
                .password("wrongpassword")
                .build();

        AssertableResponse response = authService.login(request);
        response.should(Conditions.hasStatusCode(403));
    }
}