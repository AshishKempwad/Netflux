
## Print Offset


### Start Kafka with a clean state

- Go to the directory `01-kafka-setup` directory and restart Kafka to ensure a clean demo.
- Restarting Kafka ensures there are no leftover topics or offsets from earlier runs.

```bash
docker compose down
docker compose up
```

### Access the container
- Ensure that you are in the `/opt/kafka/bin` directory.

```bash
docker exec -it kafka bash
```

### Create a topic

```bash
./kafka-topics.sh --bootstrap-server localhost:9092 --topic demo-topic --create
```

### Start a console consumer

```bash
./kafka-console-consumer.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --property print.offset=true
```
- This property `print.offset=true` enables offset printing along with each message

### Start a console producer
- Open another terminal (or another shell in the container)

```bash
./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic demo-topic
```
- Type some messages to observe the consumer output.
