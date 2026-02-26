## Concurrent Message Processing: Application-Level Concurrency

**Note:** To avoid demo inconsistencies, start with a fresh Kafka state for each run.

```bash
docker compose down
docker compose up
```

## Demo 1: Unordered Concurrent Message Processing

Start the **Digital Delivery Consumer** application.

```java
SpringApplication.run(
        DigitalDeliveryConsumer.class,
        "--section=sec14",
        "--config=01-digital-consumer"
);
```

Start the **Physical Delivery Consumer** application.

```java
SpringApplication.run(
        PhysicalDeliveryConsumer.class,
        "--section=sec14",
        "--config=02-physical-consumer"
);
```

Start the **Processor** application in **unordered mode**.

```java
SpringApplication.run(
        Processor.class,
        "--section=sec14",
        "--config=03-processor",
        "--processing.mode=unordered"
);
```

Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class,
        "--section=sec14",
        "--config=04-producer"
);
```

### Observe

* The producer publishes one order event every **10 ms**.
* The processor builds a delivery object for each order, which takes **200 ms per event**.
* The processor consumes records **in batch mode**, and each record is processed independently using **virtual threads**, allowing it to keep up with the producer.
* Events are routed to different consumers based on the **product type**.


## Demo 2: Ordered Concurrent Message Processing

In this mode, the processor groups records based on a **custom key**.
Each group is processed **concurrently**, while items **within a group** are processed **sequentially** to preserve ordering.

Start the **Processor** application in **ordered mode**.

```java
SpringApplication.run(
        Processor.class,
        "--section=sec14",
        "--config=03-processor",
        "--processing.mode=ordered"
);
```

