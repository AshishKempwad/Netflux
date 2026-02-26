## Kafka Console Consumer

Kafka provides a **console consumer tool** to read messages from a topic directly from the command line. This is mainly used for **learning, testing, and debugging**.

### Access the container
- Ensure that you are in the `/opt/kafka/bin` directory.

```
docker exec -it kafka bash
```

### Consume messages from a topic

>Please ensure that you have followed the instructions to create this topic and produced some messages as mentioned in [02-console-producer.md](./02-console-producer.md)

```bash
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic demo-topic
```

- Starts consuming **new messages only**
- The consumer waits and prints messages as they arrive
- Press `Ctrl + C` to stop

### Consume messages from the beginning

```bash
./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic demo-topic \
  --from-beginning
```

- Reads **all existing messages** in the topic from beginning
- Useful for demos and validation
