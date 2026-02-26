## Scaling Consumers with Consumer Groups

**Note:**
To avoid demo inconsistencies, start with a **fresh Kafka state** for each run.

```bash
docker compose down
docker compose up
```

---

### Step 1: Create a Topic with Multiple Partitions

```bash
docker exec -it kafka bash

./kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --topic demo-topic \
  --create \
  --partitions 3
```

### Step 2: Start the Producer

Start the producer that continuously emits messages.

```java
SpringApplication.run(
        Producer.class, "--section=sec06", "--config=02-producer"
);
```

### Step 3: Start Consumers (Same Consumer Group)

Launch **multiple consumer instances one by one** to observe partition rebalancing.

```java
SpringApplication.run(
        Consumer1.class, "--section=sec06", "--config=01-consumer"
);

SpringApplication.run(
        Consumer2.class, "--section=sec06", "--config=01-consumer"
);

SpringApplication.run(
        Consumer3.class, "--section=sec06", "--config=01-consumer"
);
```

### Observe

* Partitions are **redistributed** as consumers join the group
* Each partition is assigned to **only one consumer at a time**
* **No messages are lost** during rebalancing

> **Total messages produced = Total messages consumed (across all consumers)**
