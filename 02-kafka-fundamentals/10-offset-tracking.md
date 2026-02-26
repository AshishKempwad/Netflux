## Offset Tracking Demo

### Goal

Understand how Kafka tracks **consumer group offsets** and how consumers resume from the last committed position.

### Create a topic with 2 partitions

```bash
docker exec -it kafka bash

./kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --create \
  --partitions 2
```

### Start a producer

Produce **5 messages with a key** so they are consistently routed to the same partition.

```bash
docker exec -it kafka bash

./kafka-console-producer.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --property parse.key=true \
  --property key.separator=:
```

Example input:

```
1:a
2:a
3:a
4:a
5:a
```

### Start a consumer group with 2 consumers

> We intentionally do **not** use `--from-beginning`.
> Consumers will read **only new messages**.

#### Terminal 1

```bash
docker exec -it kafka bash

./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --property print.offset=true \
  --property print.key=true \
  --group cg
```

#### Terminal 2

```bash
docker exec -it kafka bash

./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --property print.offset=true \
  --property print.key=true \
  --group cg
```

### Describe the consumer group

```bash
docker exec -it kafka bash

./kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --describe \
  --group cg
```
We can observe the following columns

* **LOG-END-OFFSET**

  * The **latest offset** available in the partition
  * Represents **the total data currently available** in Kafka for that partition

* **CURRENT-OFFSET**

  * The **last offset committed** by the consumer group
  * Indicates **how much data the consumer group has processed**

* **LAG**

  * The difference between `LOG-END-OFFSET` and `CURRENT-OFFSET`
  * Shows **how far behind the consumer group is**

```
LAG = LOG-END-OFFSET − CURRENT-OFFSET
```

> Lag tells us whether consumers are keeping up with producers.



