## StreamBridge

**Note:** To avoid issues during the demos, it is recommended to spin up a fresh Kafka container for each demo to clean up the state.

```bash
docker compose down
docker compose up
```

## Ping Command (Demo Trigger)

```bash
ping -c 10 google.com   # Linux / macOS
ping -n 10 google.com   # Windows
```

* `ping` checks host reachability and reports network latency.
* Each line of output represents a **ping result**.


## Demo 1: Producing Messages Dynamically (StreamBridge)

1. Start Consumer

```java
SpringApplication.run(
        Consumer.class, "--section=sec04", "--config=01-consumer"
);
```

2. Start Producer

```java
SpringApplication.run(
        Producer.class, "--section=sec04", "--config=02-producer"
);
```

#### Observe

* The producer executes the `ping` command.
* **Each ping output line is emitted as a Kafka message** using `StreamBridge`.
* The consumer receives and logs these messages from the Kafka topic.
