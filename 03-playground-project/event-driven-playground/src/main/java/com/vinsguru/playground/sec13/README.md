## Concurrent Message Processing with Spring Cloud Stream

**Note:** To avoid demo inconsistencies, start with a fresh Kafka state for each run.

```bash
docker compose down
docker compose up
```

Create a topic with **3 partitions**.

```bash
docker exec -it kafka bash

./kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic order-events \
    --create \
    --partitions 3
```

Start the **Digital Delivery Consumer** application.

```java
SpringApplication.run(
        DigitalDeliveryConsumer.class,
        "--section=sec13",
        "--config=01-digital-consumer"
);
```

Start the **Physical Delivery Consumer** application.

```java
SpringApplication.run(
        PhysicalDeliveryConsumer.class,
        "--section=sec13",
        "--config=02-physical-consumer"
);
```

Start the **Processor** application.

```java
SpringApplication.run(
        Processor.class,
        "--section=sec13",
        "--config=03-processor"
);
```

Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class,
        "--section=sec13",
        "--config=04-producer"
);
```

**Observe:**

* The producer publishes one order event every **100 ms**.
* The processor builds a delivery object for each order, and this takes **200 ms** per event.
* The processor runs with **3 consumer threads**, each consuming from **one partition**, allowing it to keep up with the producer.
* Events are routed to different consumers based on the **product type**.

---

### What If `concurrency = 1`?

* The processor uses **only one consumer thread**.
* All **3 partitions** are assigned to that single consumer.
* Each event still takes **200 ms** to process.
* The producer publishes events faster (**100 ms per event**) than the processor can handle.
* As a result, **lag increases** and the processor falls behind.

