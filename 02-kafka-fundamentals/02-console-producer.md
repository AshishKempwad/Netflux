## Kafka Console Producer

Kafka provides a **console producer tool** that allows us to send messages to a topic directly from the command line. This is mainly used for **learning, testing, and debugging** purposes. Not meant for production workloads.

### Access the container
- Ensure that you are in the `/opt/kafka/bin` directory.

```
docker exec -it kafka bash
```

### Create a topic

```bash
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic demo-topic
```

### Produce messages to a topic

```bash
./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic demo-topic
```

- The producer waits for input from the console
- **Each line** followed by pressing **Enter** is sent as a separate message
- Messages are sent in **plain text** by default
- Press `Ctrl + C` to stop the producer.

