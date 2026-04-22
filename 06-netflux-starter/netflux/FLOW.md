# Netflux High-Level Flow

This document describes the end-to-end request and event flow for the `06-netflux-starter/netflux` project.

## Services and Responsibilities

- `customer-service` (port `6060`): manages customer profile/genre preference, publishes customer genre update events.
- `movie-service` (port `7070`): manages movie catalog, publishes movie added events.
- `recommendation-service` (port `8080`): consumes Kafka events, maintains recommendation view, exposes list + stream APIs.
- `netflux-events`: shared event contract module used across services.

## Runtime Flow (Current Design)

1. A client updates customer genre via `customer-service`.
2. `customer-service` persists change and publishes `CustomerGenreUpdatedEvent` to Kafka topic `customer-events`.
3. A client adds a movie via `movie-service`.
4. `movie-service` persists movie and publishes `MovieAddedEvent` to Kafka topic `movie-events`.
5. `recommendation-service` consumes both topics, updates local recommendation state, and emits in-app recommendation events.
6. Clients fetch recommendations using:
   - `GET /api/recommendations/{customerId}`
   - `GET /api/recommendations/{customerId}/stream` (Server-Sent Events).

## Mermaid Diagram

```mermaid
flowchart LR
    C[Client / UI]
    CS[customer-service]
    MS[movie-service]
    RS[recommendation-service]
    DB1[(Customer DB)]
    DB2[(Movie DB)]
    DB3[(Recommendation DB/View)]
    T1[(Kafka: customer-events)]
    T2[(Kafka: movie-events)]

    C -->|PATCH /api/customers/{id}/genre| CS
    CS --> DB1
    CS -->|CustomerGenreUpdatedEvent| T1

    C -->|POST /api/movies| MS
    C -->|GET /api/movies/{id}| MS
    MS --> DB2
    MS -->|MovieAddedEvent| T2

    T1 --> RS
    T2 --> RS
    RS --> DB3

    C -->|GET /api/recommendations/{customerId}| RS
    C -->|GET /api/recommendations/{customerId}/stream| RS
```

## Code Entry Points

- Customer API: `customer-service/.../controller/CustomerController.java`
- Customer event publisher: `customer-service/.../messaging/CustomerEventPublisher.java`
- Movie API: `movie-service/.../controller/MovieController.java`
- Movie event publisher: `movie-service/.../messaging/MovieEventPublisher.java`
- Recommendation API: `recommendation-service/.../controller/RecommendationController.java`
- Recommendation consumer config: `recommendation-service/.../messaging/EventConsumerConfig.java`

## Note About Starter State

This is a starter module: several methods and stream bindings are intentionally placeholders (`return null`, `Flux.empty()`, `??`) and are expected to be implemented as part of the exercise.
