## Dynamic Routing

**Note:** To avoid demo inconsistencies, start with a fresh Kafka state for each run.

```bash
docker compose down
docker compose up
```

Start the **Digital Delivery Consumer** application.

```java
SpringApplication.run(
        DigitalDeliveryConsumer.class, "--section=sec10", "--config=01-digital-consumer"
);
```

Start the **Fedex Consumer** application.

```java
SpringApplication.run(
        FedExConsumer.class, "--section=sec10", "--config=02-fedex-consumer"
);
```

Start the **USPS Consumer** application.

```java
SpringApplication.run(
        USPSConsumer.class, "--section=sec10", "--config=03-usps-consumer"
);
```

Start the **Processor** application.

```java
SpringApplication.run(
        Processor.class, "--section=sec10", "--config=04-processor"
);
```

Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class, "--section=sec10", "--config=05-producer"
);
```

**Observe:**

* The processor builds delivery objects for each order.
* Events are routed to different consumers based on runtime carrier availability, not just product type.
* When the preferred carrier is unavailable, events are dynamically rerouted to an alternate consumer.
