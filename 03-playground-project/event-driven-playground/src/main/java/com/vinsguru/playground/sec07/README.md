## Building a Processor

**Note:** To avoid demo inconsistencies, start with a fresh Kafka state for each run.

```bash
docker compose down
docker compose up
```

---

## Demo 1: 1 → 1 Mapping

Start the **Consumer** application.

```java
SpringApplication.run(
        Consumer.class, "--section=sec07", "--config=01-consumer"
);
```

Start the **Processor** application.

```java
SpringApplication.run(
        Processor.class, "--section=sec07", "--config=03-payment-processor"
);
```

Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class, "--section=sec07", "--config=02-producer"
);
```

**Observe:**

* For each order event received, the processor emits a payment event.
* The consumer consumes the payment events.

---

## Demo 2: 1 → 0/1 Mapping (Filter)

Start the **Consumer** application.

```java
SpringApplication.run(
        Consumer.class, "--section=sec07", "--config=01-consumer"
);
```

Start the **Processor** application.

```java
SpringApplication.run(
        Processor.class, "--section=sec07", "--config=04-shipment-processor"
);
```

Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class, "--section=sec07", "--config=02-producer"
);
```

**Observe:**

* The processor emits a shipment event only for physical orders.
* Digital orders are filtered out.

---

## Demo 3: 1 → N Mapping

Start the **Consumer** application.

```java
SpringApplication.run(
        Consumer.class, "--section=sec07", "--config=01-consumer"
);
```

Start the **Processor** application.

```java
SpringApplication.run(
        Processor.class, "--section=sec07", "--config=05-notification-processor"
);
```

Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class, "--section=sec07", "--config=02-producer"
);
```

**Observe:**

* For every order event, the processor emits two notification events.
