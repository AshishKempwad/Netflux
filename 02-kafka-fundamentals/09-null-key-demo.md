## Null Key Demo

### Goal

Send messages **without a key** to a **2-partition topic** and observe how Kafka distributes the messages across partitions and consumers.

---

### Create a topic with 2 partitions

```bash
docker exec -it kafka bash

./kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --topic null-key-topic \
  --create \
  --partitions 2
```

### Start a consumer group with 2 consumers

#### Terminal 1

```bash
docker exec -it kafka bash

./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic null-key-topic \
  --property print.offset=true \
  --property print.key=true \
  --group null-key-group
```

#### Terminal 2

```bash
docker exec -it kafka bash

./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic null-key-topic \
  --property print.offset=true \
  --property print.key=true \
  --group null-key-group
```

### Start a producer

Produce one message every **50 milliseconds**. Messages are sent **without a key**.

```bash
i=1
while true; do
  echo "msg$i"
  i=$((i+1))
  sleep 0.05
done | ./kafka-console-producer.sh \
  --bootstrap-server localhost:9092 \
  --topic null-key-topic \
  --timeout 0
```

