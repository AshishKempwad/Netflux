## Content-Based Routing

**Note:** To avoid demo inconsistencies, start with a fresh Kafka state for each run.

```bash
docker compose down
docker compose up
```

Start the **Digital Delivery Consumer** application.

```java
SpringApplication.run(
        DigitalDeliveryConsumer.class, "--section=sec09", "--config=01-digital-consumer"
);
```

Start the **Physical Delivery Consumer** application.

```java
SpringApplication.run(
        PhysicalDeliveryConsumer.class, "--section=sec09", "--config=02-physical-consumer"
);
```

Start the **Processor** application.

```java
SpringApplication.run(
        Processor.class, "--section=sec09", "--config=03-processor"
);
```

Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class, "--section=sec09", "--config=04-producer"
);
```

**Observe:**

* The processor builds delivery objects for each order.
* Events are routed to two different consumers based on the product type.
