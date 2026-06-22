# Accounts API

Simple project for creating and retrieving savings bank accounts.

## Stack

- Java 21
- Spring Boot 4.1.0
- Postgres
- Redis 
- Gradle

## Assumptions

- Account number uses a simple 12-digit generated value. In a real banking system this would implement complex logic hence created it as a separate component.
- Offensive nickname checking is implemented using a small in-memory list. In production this should use a database table or config.
- `GET /api/v1/savings-accounts/{id}` is the main retrieval endpoint. A secondary account-number lookup endpoint is included for practical usage.
- Hibernate `ddl-auto=update` is used to keep setup simple for assessment purposes. In production, Flyway would be preferred.

## Run locally

Start PostgreSQL and Redis:

```bash
docker compose up -d
```

Run the application:

```bash
gradle bootRun
```

Run tests:

```bash
gradle test
```

## Swagger Docs
```
http://localhost:8080/swagger-ui.html
```

## Open API JSON
```
http://localhost:8080/v3/api-docs
```

## API examples

### Create savings account

```bash
curl -X POST http://localhost:8080/api/v1/savings-accounts \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Rasanka Jayawardana",
    "accountNickName": "Vacation"
  }'
```

Sample response:

```json
{
  "id": "91a15907-dfde-45f9-93a9-1ef788606946",
  "accountNumber": "756396285441",
  "customerName": "Rasanka Jayawardana",
  "accountNickName": "Vacation",
  "createdAt": "2026-06-22T08:50:04.987592Z"
}
```

### Get savings account by ID

```bash
curl http://localhost:8080/api/v1/savings-accounts/{id}
```

### Get savings account by account number

```bash
curl http://localhost:8080/api/v1/savings-accounts/account-number/{accountNumber}
```

## Improvements / TODOs

- Add Flyway for database migrations.
- Update account number generation logic
- Load offensive nick names from a db table
- Add authentication and authorisation.
- Add structured logging and correlation IDs.
- Add resilience patterns such as retry, timeout, and circuit breaker where appropriate.
- Add performance/load tests for account creation and lookup.
