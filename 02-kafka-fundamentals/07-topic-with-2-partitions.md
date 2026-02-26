
### Start Kafka with a Clean State

- Go to the `01-kafka-setup` directory and restart Kafka to ensure a clean demo. 

```bash
docker compose down
docker compose up
```

### Create a topic with 2 partitions

- Access the kafka container.

```bash
docker exec -it kafka bash
```

- Create a topic with 2 partitions.

```bash
./kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic order-events \
    --create \
    --partitions 2
```

- Describe the topic

```bash
./kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic order-events \
    --describe
```