## Manual Acknowledgement Demo

**Note:** To avoid demo inconsistencies, start with a fresh Kafka state for each run.

```bash
docker compose down
docker compose up
```

### Start the Consumer application

```java
SpringApplication.run(
        SectionRunner.class,
        "--section=sec15",
        "--config=01-consumer"
);
```

- Open two terminals

### Terminal 1 – Console Producer

```bash
docker exec -it kafka bash

./kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --timeout 0
```

### Terminal 2 – Consumer Group Status

```bash
./kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --describe \
    --group demo-group
```

### Demo Steps

1. **Produce messages:** `1`, `2`, `3`

   * Application acknowledges the messages
   * Offsets are committed
   * LAG remains `0`

2. **Produce messages:** `4`, `5`, `6`

   * Application does **not** acknowledge
   * Offsets are **not committed**
   * LAG increases
   * Restart the consumer application. Messages `4`, `5`, `6` are re-delivered because offsets were not committed

3. **Produce message:** `7`

   * Application simulates a temporary failure
   * Message is **NACKed**
   * Kafka re-delivers the message after a delay

### Observe

* Acknowledged messages → offsets committed
* Unacknowledged messages → LAG increases
* NACK → delayed retry without offset commit

