# payment-service

Payment authorization for the [ar-ecommerce-platform](https://github.com/ar-ecommerce-platform).

> **Demo stand-in — no real payment provider.** It approves every request except amounts above
> a configurable ceiling, giving the order flow a deterministic decline path to show.

- **Port:** 8085
- **Persistence:** in-memory H2 (`payments` table) — resets on restart
- **Registers with:** Eureka (discovery-server :8761)

## Endpoints

Reached through the gateway as `/api/payments/**`.

| Method | Path | Body | Result |
|---|---|---|---|
| `POST` | `/payments` | `{ orderId, amountCents }` | `{ paymentId, orderId, amountCents, status, createdAt }` — `status` is `APPROVED`, or `DECLINED` when `amountCents > PAYMENT_AUTO_DECLINE_ABOVE_CENTS` |
| `GET` | `/payments/{id}` | — | one payment, `404` if missing |

**API docs:** Swagger UI at `http://localhost:8085/swagger-ui.html` (OpenAPI JSON at `/v3/api-docs`).

## Run

Whole platform (recommended):

```bash
docker compose -f ../infra/compose/docker-compose.yml up -d --build
```

This service alone:

```bash
./gradlew bootRun
# or
docker build -t ecom/payment-service . && docker run --rm -p 8085:8085 ecom/payment-service
```

## Build & quality

```bash
./gradlew build          # compile + test + spotless + checkstyle (cyclomatic complexity <= 10) + jacoco report
./gradlew spotlessApply
```

Quality config is vendored: `gradle/quality.gradle`, `config/checkstyle/`.

## Testing

`./gradlew test` runs every layer below; `./gradlew build` also runs Checkstyle + Spotless and writes a JaCoCo report.

- **Smoke** — `PaymentServiceApplicationTests`: the full Spring context starts.
- **Unit** — `service/PaymentServiceTest`: amounts at or below the ceiling are `APPROVED`, amounts above it are `DECLINED`.
- **API / web slice** — `web/PaymentControllerTest` (`@WebMvcTest`): `POST /payments` returns the payment view; `GET /payments/{id}` missing → 404; a non-positive amount → 400.

## Config

| Variable | Default | Purpose |
|---|---|---|
| `SERVER_PORT` | `8085` | HTTP port |
| `PAYMENT_AUTO_DECLINE_ABOVE_CENTS` | `500000` | amounts strictly above this are declined |
| `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` | `http://localhost:8761/eureka/` | registry URL |

## Tech

Java 21 · Spring Boot 3.5.7 · Spring Data JPA + H2 · Bean Validation ·
Spring Cloud 2025.0.0 (`netflix-eureka-client`) · Gradle

See [infra/RUNBOOK.md](../infra/RUNBOOK.md) for the full platform runbook.
