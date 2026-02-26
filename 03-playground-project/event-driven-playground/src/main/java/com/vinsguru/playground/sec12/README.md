## Batch Processing

**Note:** To avoid demo inconsistencies, start with a fresh Kafka state for each run.
Please use the Docker Compose YAML from `01-kafka-setup`.

```bash
docker compose down
docker compose up
```

Start the **Consumer** application.

* The consumer is configured to **consume messages in batch mode**. Check `sec12/01-consumer.yaml`.
* The application exposes a bean that consumes `List<T>`.

```java
SpringApplication.run(
        Consumer.class, "--section=sec12", "--config=01-consumer"
);
```

Start the **Producer** application.

* The producer is configured to **buffer messages and send them in batches**. Check `sec12/02-producer.yaml`.

```java
SpringApplication.run(
        Producer.class, "--section=sec12", "--config=02-producer"
);
```

**Observe:**

* The producer sends **1 million messages**.
* The consumer processes **up to 1,000 messages in a single poll**.

