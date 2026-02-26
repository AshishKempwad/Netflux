## Consumer Group Demo (Topic with 2 Partitions)

### Prerequisite

* Ensure you have completed the **previous demo**
* The topic `order-events` must already exist **with 2 partitions**

---

### Start a Consumer Group with Two Consumers

We will start **two consumers in the same consumer group**.
Each consumer will run in a **separate terminal**.

#### Terminal 1

```bash
docker exec -it kafka bash

./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic order-events \
  --property print.offset=true \
  --property print.key=true \
  --group payment-service
```

#### Terminal 2

```bash
docker exec -it kafka bash

./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic order-events \
  --property print.offset=true \
  --property print.key=true \
  --group payment-service
```

* Both consumers belong to the **same consumer group**
* Kafka will distribute partitions between them

### Start a Console Producer

Open **another terminal** and access the Kafka container:

```bash
docker exec -it kafka bash
```

Start the console producer.
We will send messages **with a key**, using the format `key:value`
(`:` is used as the key separator).

```bash
./kafka-console-producer.sh \
  --bootstrap-server localhost:9092 \
  --topic order-events \
  --property parse.key=true \
  --property key.separator=:
```

### Observe

* Produce multiple messages
* Notice how messages are **distributed across the two consumers**
* Each partition is consumed by **only one consumer in the group**