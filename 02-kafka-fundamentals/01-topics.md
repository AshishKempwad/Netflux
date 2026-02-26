## Topics

Kafka provides the **`kafka-topics.sh`** CLI tool to manage topics.

### Access the container
- Ensure that you are in the `/opt/kafka/bin` directory.

```
docker exec -it kafka bash
```

### Create a topic

> All commands require `--bootstrap-server` because the CLI must first connect to a broker to discover the cluster.

```bash
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic order-events
```

- Let’s create a few more topics

```bash
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic payment-events
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic shipping-events
```

### List all topics in the cluster

```bash
./kafka-topics.sh --bootstrap-server localhost:9092 --list
```

* Shows all topics currently present in the cluster

### Describe a topic

```bash
./kafka-topics.sh --bootstrap-server localhost:9092 --topic order-events --describe
```

### Delete a topic

```bash
./kafka-topics.sh --bootstrap-server localhost:9092 --topic order-events --delete
```
