## Consumer Group

### Start Kafka with a Clean State

- Go to the `01-kafka-setup` directory and restart Kafka to ensure a clean demo. 

```bash
docker compose down
docker compose up
```
- Please ensure that you create the `demo-topic`.

```bash
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic demo-topic
```

### Access the Kafka Container

Open **two terminals** (you will start one consumer in each). In **each terminal**,

```bash
docker exec -it kafka bash
```

### Consumer Group 1: `payment-service`

```bash
./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic demo-topic \
  --property print.offset=true \
  --group payment-service
```

### Consumer Group 2: `inventory-service`

```bash
./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic demo-topic \
  --property print.offset=true \
  --group inventory-service
```
## Start a Console Producer

Open **another terminal**, access the container.

```bash
docker exec -it kafka bash
```

Start the producer:

```bash
./kafka-console-producer.sh \
  --bootstrap-server localhost:9092 \
  --topic demo-topic
```

Type some messages and press **Enter**.

## List all the consumer groups
```bash
./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
```