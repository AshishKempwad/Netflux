## Kafka Fault Tolerance in Action

**Note:** To avoid demo inconsistencies, start with a fresh Kafka cluster. Navigate to `04-kafka-cluster` and run the following commands:

```bash
docker compose down
docker compose up
```

Create a topic with **1 partition** and **3 replicas**.

```bash
./kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --topic demo-topic \
  --create \
  --partitions 1 \
  --replication-factor 3
```

Start the **Consumer** application.

```java
SpringApplication.run(
        Consumer.class, "--section=sec11", "--config=01-consumer"
);
```

Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class, "--section=sec11", "--config=02-producer"
);
```

Once the producer starts producing messages, ensure that the consumer is consuming them.

Now bring the Kafka containers up and down to observe the behavior.
You will notice that the **application continues to work without interruption**, demonstrating Kafka’s fault tolerance.
