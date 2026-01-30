<p align="center">
  <img src="assets/header.svg" alt="Weather Aggregator" />
</p>

<h1 align="center">🌦 Weather Aggregator API</h1>

<p align="center">
  Backend-приложение на Spring Boot для агрегации данных о погоде  
  из нескольких внешних источников и предоставления единого REST API
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3-green" />
  <img src="https://img.shields.io/badge/Maven-build-blue" />
  <img src="https://img.shields.io/badge/Status-Pet%20Project-lightgrey" />
</p>

---

## 🚀 О проекте

**Weather Aggregator API** — backend-сервис, который получает погодные данные  
из нескольких внешних API, нормализует их и возвращает клиенту  
в едином формате.

Проект выполнен как пет-проект с акцентом на:
- архитектуру backend-приложений
- работу с внешними API
- проектирование абстракций и слоёв приложения

---

## ✨ Возможности

- 🌍 Поддержка нескольких погодных провайдеров
- 🔗 Агрегация и унификация данных
- 🧩 Абстракция источников через интерфейсы
- 🏗 Чёткое разделение ответственности по слоям
- 📦 Разделение external и internal DTO

---

## 🧠 Архитектура

| Слой | Ответственность |
|-----|-----------------|
| Controller | HTTP API, обработка запросов |
| Service | Бизнес-логика и агрегация |
| Provider | Интеграция с внешними сервисами |
| DTO | Передача и нормализация данных |

<details>
<summary><b>🔌 Provider layer</b></summary>

Используется интерфейс `WeatherProvider`, который реализуют:

- `WeatherApiProvider`
- `WeatherBitProvider`

Такой подход позволяет:
- легко добавлять новых провайдеров
- изолировать работу с внешними API
- соблюдать принцип Dependency Inversion

</details>

---

## 📦 DTO

<details>
<summary><b>🌐 External DTO</b></summary>

Используются для десериализации ответов конкретных внешних API:

- `WeatherApiResponse`
- `WeatherBitResponse`

</details>

<details>
<summary><b>🏠 Internal DTO</b></summary>

Используются внутри приложения и при формировании ответа клиенту:

- `WeatherData`
- `WeatherResponse`

Разделение DTO позволяет не зависеть от формата сторонних сервисов  
и упрощает поддержку приложения.

</details>

---

## 🛠 Технологии

- ☕ Java
- 🌱 Spring Boot
- 📦 Maven
- 🔁 REST API

---

## 🎯 Цель проекта

Проект создан для практики:

- работы с несколькими внешними API
- агрегации данных из разных источников
- проектирования интерфейсов и абстракций
- построения чистой и поддерживаемой backend-архитектуры

---

## 🔮 Идеи для развития

- retry / timeout механизмы для внешних API
- fallback при недоступности провайдера
- unit-тесты для сервисного слоя
- кеширование ответов
- метрики и логирование

---

<p align="center">
  <i>Pet-project для демонстрации backend-навыков и архитектурного мышления</i>
</p>
