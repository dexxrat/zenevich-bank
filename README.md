# 🏦 Zenevich Bank — Professional Test Automation Framework

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://adoptium.net/)
[![Maven](https://img.shields.io/badge/Maven-3.9.9-red.svg)](https://maven.apache.org/)
[![JUnit 5](https://img.shields.io/badge/JUnit-5.11.4-green.svg)](https://junit.org/junit5/)
[![REST Assured](https://img.shields.io/badge/REST%20Assured-5.5.0-brightgreen.svg)](https://rest-assured.io/)
[![Selenide](https://img.shields.io/badge/Selenide-7.7.3-blue.svg)](https://selenide.org/)
[![Allure](https://img.shields.io/badge/Allure-2.29.1-orange.svg)](https://allure.qameta.io/)
[![Docker](https://img.shields.io/badge/Docker-✔-blue.svg)](https://www.docker.com/)
[![CI/CD](https://github.com/dexxrat/zenevich-bank/actions/workflows/test.yml/badge.svg)](https://github.com/dexxrat/zenevich-bank/actions)
[![Allure Report](https://img.shields.io/badge/Allure%20Report-Live-brightgreen.svg)](https://dexxrat.github.io/zenevich-bank/allure-report/)

## 📖 О проекте

**Zenevich Bank** — это демонстрационный проект, созданный в рамках ускоренной программы развития **Java AQA Engineer**. Проект показывает **профессиональный подход к автоматизации тестирования** микросервисной архитектуры.

## 🛠 Технологический стек

| Категория | Технологии |
|-----------|------------|
| **Язык** | Java 21 |
| **Сборка** | Maven |
| **Тестовый фреймворк** | JUnit 5 |
| **UI тесты** | Selenide + Page Object |
| **API тесты** | REST Assured + Jackson |
| **БД тесты** | JDBC + PostgreSQL |
| **Генерация данных** | Java Faker |
| **Ретрай логи** | Кастомный RetryAnalyzer |
| **Отчёты** | Allure Framework |
| **Логирование** | SLF4J + Logback |
| **Конфигурация** | Owner |
| **Контейнеризация** | Docker + Docker Compose |
| **CI/CD** | GitHub Actions |
| **Контрактные тесты** | Spring Cloud Contract |

## ✅ Что умеет фреймворк

- ✅ **API тесты** (REST Assured) с валидацией JSON Schema
- ✅ **UI тесты** (Selenide) с Page Object Pattern
- ✅ **БД проверки** через JDBC
- ✅ **Генерация тестовых данных** (Java Faker)
- ✅ **Ретрай логи** для flaky-тестов
- ✅ **Параллельный запуск** тестов
- ✅ **Allure отчёты** с шагами и скриншотами
- ✅ **CI/CD** (GitHub Actions)
- ✅ **Docker** для изоляции окружения

## 🚀 Быстрый старт

```bash
# 1. Клонируй проект
git clone https://github.com/dexxrat/zenevich-bank.git
cd zenevich-bank

# 2. Запусти все сервисы
docker-compose up -d

# 3. Запусти тесты
cd aqa-tests
mvn clean test

# 4. Сгенерируй Allure отчёт
mvn allure:serve