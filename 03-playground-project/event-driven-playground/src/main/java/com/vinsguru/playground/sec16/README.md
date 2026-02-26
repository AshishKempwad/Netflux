## Error Handling

**Note:** To avoid demo inconsistencies, always start with a fresh Kafka state.

```bash
docker compose down
docker compose up
```

## Demo 1: Retry with Fixed Delay

### Start the Consumer

```java
SpringApplication.run(
        SectionRunner.class,
        "--section=sec16",
        "--config=01-retry-fixed-delay"
);
```

### Open Two Terminals

**Terminal 1 – Console Producer**

```bash
docker exec -it kafka bash

./kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --timeout 0
```

**Optional: Terminal 2 – Consumer Group Status**

```bash
./kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --describe \
    --group demo-group
```

### Demo & Observe

1. **Produce messages:** `1, 2, 3, 4`

   * Application processes messages successfully
   * Messages are acknowledged
   * Offsets are committed
   * **LAG = 0**

2. **Produce message:** `0`

   * Application throws an exception
   * Spring Cloud Stream retries the message (based on `consumer.maxAttempts`)
   * Observe **delivery attempt** header on each retry
   * Offset is **not committed** during retries
   * After retries are exhausted:

     * Exception is logged
     * Offset is committed

3. **Produce messages again:** `1, 2, 3, 4`

   * Normal processing resumes
   * Offsets are committed

## Demo 2: Retry with Exponential Delay

### Start the Consumer

```java
SpringApplication.run(
        SectionRunner.class,
        "--section=sec16",
        "--config=02-retry-exponential-delay"
);
```

### Open Console Producer

```bash
docker exec -it kafka bash

./kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --timeout 0
```

### Demo & Observe

1. **Produce messages:** `1, 2, 3, 4`

   * Successful processing
   * Offsets committed
   * **LAG = 0**

2. **Produce message:** `0`

   * Application throws an exception
   * Message is retried
   * Retry delays increase exponentially based on:

     ```
     consumer.back-off-multiplier
     ```
   * Observe increasing time gaps between retries

## Demo 3: Retryable vs Non-Retryable Exceptions

### Start the Consumer

```java
SpringApplication.run(
        SectionRunner.class,
        "--section=sec16",
        "--config=03-retryable-exceptions"
);
```

### Open Console Producer

```bash
docker exec -it kafka bash

./kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --timeout 0
```

### Demo & Observe

1. **Produce messages:** `1, 2, 3, 4`

   * Successful processing
   * Offsets committed

2. **Produce message:** `0`

   * `InputValidationException` is thrown
   * Message is **not retried**

3. **Produce messages:** `6, 7, 8`

   * `ServiceUnavailableException` is thrown
   * Message **is retried** based on retry configuration

## Demo 4: Dead Letter Queue (DLQ)

### Start the Consumer

```java
SpringApplication.run(
        SectionRunner.class,
        "--section=sec16",
        "--config=04-dlq"
);
```

### Open Console Producer

```bash
docker exec -it kafka bash

./kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --timeout 0
```

### Open DLQ Consumer (New Terminal)

```bash
./kafka-console-consumer.sh \
   --bootstrap-server localhost:9092 \
   --topic demo-topic-dlq \
   --property print.headers=true \
   --property print.key=true \
   --property print.value=true
```

### Demo & Observe

1. **Produce messages:** `1, 2, 3, 4`

   * Successful processing
   * Offsets committed
   * **LAG = 0**

2. **Produce message:** `0`

   * `InputValidationException` occurs
   * No retries
   * Message is sent to the **DLQ**
   * Observe:

     * Original payload
     * Exception details in headers

3. **Produce messages:** `6, 7, 8`

   * `ServiceUnavailableException` occurs
   * Retries are attempted
   * After retries are exhausted:

     * Message is sent to the **DLQ**
     * Headers contain retry and failure metadata

