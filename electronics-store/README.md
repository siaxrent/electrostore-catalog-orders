# Интернет‑магазин электроники (Spring Boot + PostgreSQL)

Курсовой проект: веб‑приложение для управления каталогом электроники и оформления заказов.  
Стек: **Java 17, Spring Boot 3, Spring Data JPA, Spring Security, Log4j2, PostgreSQL, H2, Docker**.

---

## 1. Возможности системы

- **Категории**
  - просмотр списка категорий;
  - добавление новых категорий;
  - удаление категорий (если нет зависимостей).

- **Товары**
  - просмотр списка товаров;
  - фильтрация по названию/артикулу и «виду товара»;
  - добавление новых товаров с полями: артикул, название, цена, остаток, вид товара, категория.

- **Заказы**
  - создание заказа с несколькими позициями;
  - проверка остатков на складе при оформлении;
  - расчёт суммарной стоимости заказа;
  - просмотр списка заказов;
  - просмотр состава заказа (позиции с количеством и ценой);
  - смена статуса заказа (`NEW`, `PAID`, `CANCELED`).

- **Безопасность**
  - HTTP Basic‑аутентификация в профиле `postgres` для операций записи (POST/PUT/PATCH/DELETE);
  - интерфейс авторизации `login.html`.

- **Документация и логирование**
  - Javadoc (папка `javadoc/`);
  - лог‑файлы в `logs/app.log` через Log4j2.

---

## 2. Архитектура и структура проекта

Основные пакеты:

- `config` — конфигурация Spring Boot (`SecurityConfig`, сидеры данных, точка входа).  
- `controller` — REST‑контроллеры (`CategoryController`, `ProductController`, `OrderController`).  
- `service` / `service.impl` — интерфейсы и реализации бизнес‑логики.  
- `repository` — интерфейсы Spring Data JPA для доступа к БД.  
- `entity` — доменные сущности и enum’ы (товары, категории, заказы, статусы, виды товаров).  
- `dto` — объекты передачи данных для запросов/ответов.  
- `exception` — собственные исключения и глобальный обработчик ошибок.

Фронтенд (простое SPA без фреймворков) расположен в `src/main/resources/static`:

- `index.html` — дашборд/панель управления.  
- `categories.html` — работа с категориями.  
- `products.html` — каталог товаров.  
- `orders.html` — заказы.  
- `login.html` — авторизация.

---

## 3. Профили и базы данных

### Профиль `dev` (по умолчанию)

- БД: **H2** (файловая), параметры в `application-dev.properties`.  
- Используется для разработки и тестов.  
- Таблицы создаются автоматически (`spring.jpa.hibernate.ddl-auto=update`).  
- Начальные данные создаёт `DataSeeder` (профиль `dev`).

Запуск:

```bash
cd electronics-store
.\mvnw.cmd spring-boot:run
```

Приложение будет доступно по адресу `http://localhost:8080/`.

### Профиль `postgres` (основная БД)

- БД: **PostgreSQL**, параметры в `application-postgres.properties`.  
- Структура БД фиксируется (режим `spring.jpa.hibernate.ddl-auto=validate`, включён Flyway).  
- Демонстрационные данные для показа создаёт `PostgresDemoDataSeeder` (только если БД пуста).

Запуск без Docker:

1. Поднять PostgreSQL (локально или в Docker).  
2. Установить переменную профиля и запустить:

```bash
cd electronics-store
$env:SPRING_PROFILES_ACTIVE = "postgres"
.\mvnw.cmd spring-boot:run
```

---

## 4. Запуск через Docker Compose

В корне проекта есть `Dockerfile` и `docker-compose.yml`.

Запуск всего окружения (Postgres + приложение):

```bash
cd electronics-store
docker compose up -d --build     # первый запуск
docker compose up -d --no-build  # последующие запуски
```

Поднимаются сервисы:

- `db` — контейнер `postgres:16`, БД `electronics_store`.  
- `app` — контейнер с приложением, профиль `postgres`, доступ по `http://localhost:8080/`.

---

## 5. Аутентификация

- В профиле `dev` (H2) аутентификация отключена — удобно для разработки и тестов.
- В профиле `postgres` включён Spring Security:
  - все `GET`‑запросы к API и статика (`index.html`, `*.html`, `css/js`) доступны без логина;
  - операции записи (POST/PUT/PATCH/DELETE) требуют HTTP Basic‑логин.

Учётные данные администратора задаются в `application-postgres.properties`:

```properties
app.security.admin.username=admin
app.security.admin.password=admin123
```

Эти значения можно переопределить через переменные окружения.

Страница входа: `http://localhost:8080/login.html`.

---

## 6. Тестирование

Интеграционные тесты находятся в `src/test/java`:

- `OrderApiTest` — проверка:
  - создания заказа и корректного расчёта суммы;
  - обработки ошибки при недостаточном остатке;
  - смены статуса заказа.

Тесты запускаются командой:

```bash
cd electronics-store
.\mvnw.cmd test
```

---

## 7. Документация и логирование

- **Javadoc**
  - Генерируется командой:
    ```bash
    cd electronics-store
    .\mvnw.cmd -DskipTests javadoc:javadoc
    ```
  - Сгенерированная документация копируется в папку `javadoc/` (главный файл `javadoc/index.html`).

- **Логирование (Log4j2)**
  - Конфигурация: `src/main/resources/log4j2-spring.xml`.  
  - Логи пишутся в консоль и в файл `logs/app.log`.  
  - В логи попадают ключевые события: создание заказов, изменение стоков, смена статуса, ошибки бизнес‑логики.

---

## 8. Полезные URL

- UI:
  - `http://localhost:8080/` — главная (дашборд).  
  - `http://localhost:8080/categories.html` — категории.  
  - `http://localhost:8080/products.html` — товары.  
  - `http://localhost:8080/orders.html` — заказы.  
  - `http://localhost:8080/login.html` — вход.

- Javadoc (если сгенерирован и скопирован):  
  - `javadoc/index.html` (локально в проекте или через статический хостинг).


