# Outbox Pattern Notes (To Implement Later)

## Goal

Guarantee that DB updates and Kafka events do not drift apart.

## Why Outbox

Without outbox, a request can:
- update DB successfully but fail to publish to Kafka, or
- publish event but fail DB commit.

Outbox avoids this by writing business data + event record in one DB transaction.

## Transaction Boundaries (Important)

1. `CustomerService.updateCustomerGenre(...)` must be `@Transactional`
- update `customer.favoriteGenre`
- insert outbox row (status `NEW`)
- one commit for both

2. Outbox publisher method should be `@Transactional` **if relying on dirty checking**
- when Kafka send succeeds: `outbox.setStatus(SENT)`
- JPA persists status change at commit

If publisher is **not** transactional, call `outboxRepository.save(outbox)` explicitly.

## Suggested Implementation Steps

1. Add outbox table/entity:
- fields: `id`, `eventType`, `aggregateId/customerId`, `payload`, `eventTime`, `status`, `retryCount`

2. In customer update flow:
- update customer row
- insert outbox row in same transaction

3. Add scheduled publisher:
- read `NEW` outbox rows in batches
- publish to `customer-events` topic
- mark `SENT` on success
- retry with backoff / increment retry count on failure

4. Add cleanup/retention:
- archive or delete old `SENT` rows periodically

## Extra Hardening (Later)

- `@Version` for optimistic locking on outbox rows
- `SELECT ... FOR UPDATE SKIP LOCKED` style processing if multiple publisher instances
- idempotent consumer handling downstream
