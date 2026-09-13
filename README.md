# Artwork Sharing Platform — Backend

> Spring Boot backend for an artwork marketplace where creators sell finished works and take on custom commissions, backed by an in-app wallet with PayPal deposits, real-time chat, and push notifications.

![Java](https://img.shields.io/badge/Java-Spring%20Boot-6DB33F?logo=springboot&logoColor=white)
![JHipster](https://img.shields.io/badge/JHipster-8.1.0-3E8ACC?logo=jhipster&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?logo=gradle&logoColor=white)

## Overview

This is the REST API and domain layer for the Artwork Sharing Platform, a marketplace connecting art creators with audiences. Members can publish artworks, buy them either at a fixed price or through timed auctions, and post custom-work requests that creators bid on and then deliver through a milestone-based progress workflow. Money movements run through a per-user wallet with PayPal-funded deposits, and creators and audiences coordinate over real-time chat with Firebase-based push notifications.

The application was scaffolded with [JHipster 8.1.0](https://www.jhipster.tech) as a JWT-secured monolith and then extended with the marketplace domain, PayPal integration, WebSocket messaging, and scheduled jobs. It is the server half of a two-part project; the React client lives in [ArtworkSharingPlatformFE](https://github.com/khangzxrr/ArtworkSharingPlatformFE).

## Features

- **Role-based access** (audience, creator, admin) with JWT authentication and Spring Security; most endpoints are namespaced per role (`/api/audience/...`, `/api/creator/...`).
- **Artwork catalog** — artworks with assets/media, categories, likes, comments, and complaints.
- **Selling** — direct fixed-price sales and auction-style selling with bids (`ArtworkSelling`, `SellingBid`).
- **Commission requests** — audiences post requests, creators place bids with price/duration, and the selected work is tracked through a staged progress timeline (`Request`, `RequestBid`, `RequestProgress`) with two-phase payment and attachments.
- **In-app wallet** — per-user balance with typed, auditable transactions (deposit, withdrawal, buy, service-fee earnings, escrow-style temporary holds, refunds) and pessimistic locking around balance changes.
- **PayPal integration** — server-side order creation and webhook verification for wallet deposits.
- **Real-time chat & notifications** — Spring WebSocket (STOMP) messaging plus Firebase Admin SDK for push notifications.
- **Scheduled jobs** to progress or expire time-bound auctions and requests.
- **OpenAPI/Swagger** documentation and MapStruct-based DTO mapping throughout the service layer.

## Tech stack

- **Framework:** Spring Boot (JHipster 8.1.0 monolith), Java
- **Security:** Spring Security + JWT
- **Persistence:** Spring Data JPA / Hibernate, MySQL, Liquibase migrations
- **Real-time:** Spring WebSocket + STOMP
- **Integrations:** PayPal, Firebase Admin SDK (push notifications)
- **API docs & mapping:** springdoc-openapi, MapStruct
- **Build & tooling:** Gradle, Docker (Jib), SonarQube, Checkstyle
- **Testing:** JUnit, Cucumber (BDD), Cypress (E2E), Jest (client tests)

## Domain model

The domain is defined in [`artwork.jh`](artwork.jh) (JHipster JDL). Key entities:

```
Artwork ─┬─ ArtworkAsset ── Media          Wallet ── WalletTransaction
         ├─ ArtworkComment                 Request ─┬─ RequestBid
         ├─ ArtworkLike                              ├─ RequestProgress ── RequestProgressAttachment
         ├─ ArtworkComplain                          └─ RequestAttachment
         ├─ ArtworkCategory
         └─ ArtworkSelling ── SellingBid    Certificate ── Media
```

Backend source follows the standard JHipster layout under `src/main/java/com/github/khangzxrr/`:

```
domain/        JPA entities & enums          service/       business logic, impl, DTOs, mappers, jobs
repository/     Spring Data repositories      web/rest/      REST controllers (per role)
security/       JWT & Spring Security config  web/websocket/ STOMP messaging
config/         app configuration             aop/logging/   request logging
```

## Getting started

### Prerequisites

- JDK 17+ and the bundled Gradle wrapper (`./gradlew`)
- Node.js (for the client build tooling JHipster generates)
- MySQL, or Docker to run it via the provided compose files
- Credentials for PayPal and a Firebase service account (see configuration below)

### Configuration

Do **not** hard-code secrets. Provide them via environment variables / externalized config. The main settings to supply for a production run:

- `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` — MySQL connection
- `JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET` — JWT signing secret (generate with `openssl rand -base64 64`)
- PayPal `base-url`, `client-id`, and `secret-key` under `application.paypal-configuration`
- PayPal webhook `verify-url` reachable from PayPal
- A Firebase Admin SDK service-account key file, referenced by `FirebaseConfiguration`

> Note: JHipster generates a placeholder `application-*.yml` with sample values. Replace every credential with your own and keep real secrets out of version control (env vars or a secrets manager).

### Run in development

```bash
./gradlew          # start Spring Boot (dev profile, MySQL)
npm install        # first time only, for client tooling
npm start          # webpack dev server with live reload
```

Start a dev MySQL with Docker if needed:

```bash
docker compose -f src/main/docker/mysql.yml up -d
```

### Build for production

```bash
./gradlew -Pprod clean bootJar
java -jar build/libs/*.jar
```

Then open http://localhost:8080. A Docker image can be built with Jib (see [`build_docker_image.sh`](build_docker_image.sh) and [`command.md`](command.md)).

## Testing

```bash
./gradlew test integrationTest jacocoTestReport   # backend (JUnit + Cucumber)
npm test                                          # client unit tests (Jest)
npm run e2e                                        # end-to-end (Cypress)
```

## Credits

Built as a group project. Backend led by **Vo Ngoc Khang** ([@khangzxrr](https://github.com/khangzxrr)); the React frontend was a shared effort documented in [ArtworkSharingPlatformFE](https://github.com/khangzxrr/ArtworkSharingPlatformFE). Generated with JHipster 8.1.0 — full JHipster development, testing, and deployment documentation is at https://www.jhipster.tech/documentation-archive/v8.1.0.
