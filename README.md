# 🏦 Zenevich Bank — Pet Project: Full‑Stack Microservices + Test Automation Framework

## 📌 О проекте

**Zenevich Bank** — это мой **pet project** (учебный проект для портфолио), в котором я разработал полноценную банковскую микросервисную платформу. Но главная цель проекта — не просто создать банк, а построить вокруг него **профессиональный тестовый фреймворк**, демонстрирующий мой подход к автоматизации тестирования .


### 🏗 Микросервисная архитектура 
| Сервис | Порт | Назначение |
|--------|------|------------|
| Auth Service | 8081 | JWT-авторизация, регистрация, профиль |
| Account Service | 8082 | Управление банковскими счетами (CRUD) |
| Transaction Service | 8083 | Обработка переводов, Kafka producer |
| API Gateway | 8080 | Spring Cloud Gateway, маршрутизация |
| Frontend | 3000 | React + TypeScript + TailwindCSS |

### 🧪 Тестовый фреймворк (моя главная цель)
- Автотесты (API, UI, БД, E2E)
- REST Assured + JSON Schema Validation
- Selenide + Page Object Model
- JDBC для проверки данных в БД
- Allure отчёты с шагами и скриншотами
- GitHub Actions CI/CD
- Параллельный запуск тестов 
- RetryListener для flaky-тестов
- Java Faker + Builder Pattern
- Owner для конфигурации

