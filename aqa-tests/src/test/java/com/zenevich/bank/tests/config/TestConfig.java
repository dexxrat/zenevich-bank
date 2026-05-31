package com.zenevich.bank.tests.config;

public class TestConfig {
    // API Gateway (единая точка входа)
    public static final String API_GATEWAY_URL = "http://localhost:8080";

    // Для совместимости со старыми тестами
    public static final String AUTH_SERVICE_URL = API_GATEWAY_URL;
    public static final String ACCOUNT_SERVICE_URL = API_GATEWAY_URL;

    // Тестовые данные
    public static final String TEST_EMAIL = "testauto@mail.com";
    public static final String TEST_PASSWORD = "123456";
    public static final String TEST_FULL_NAME = "Auto Test User";
}