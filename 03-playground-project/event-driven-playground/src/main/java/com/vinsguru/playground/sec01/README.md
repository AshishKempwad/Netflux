## Building a Consumer

**Note:** To avoid issues during the demos, it is recommended to spin up a fresh Kafka container for each demo to clean up the state.

```bash
docker compose down
docker compose up
```

---

### Demo 1: Simple Consumer

1. Open a terminal and check that the topic `demo-topic` does not exist yet.

```bash
docker exec -it kafka bash

./kafka-topics.sh --bootstrap-server localhost:9092 --list
```

2. Start the `SectionRunner` with below config.

```java
SpringApplication.run(
    SectionRunner.class,
    "--section=sec01",
    "--config=01-simple-consumer"
);
```

3. What to observe:

* Spring automatically connects to `localhost:9092`.
* An **anonymous consumer group** is used since no group name is configured.
* `demo-topic` is auto-created.

  > To disable auto-topic creation, set `auto.create.topics.enable=false` in `server.properties` of the Kafka server.

4. Produce messages using the console producer:

```bash
./kafka-console-producer.sh \
   --bootstrap-server localhost:9092 \
   --topic demo-topic \
   --timeout 0
```

> `--timeout 0` ensures messages are sent immediately without buffering.

### Demo 2: Consume From Beginning

1. Start the `SectionRunner` with below config.

```java
SpringApplication.run(
    SectionRunner.class,
    "--section=sec01",
    "--config=02-from-beginning"
);
```

### Demo 3: Consumer Group Name

1. Start the `SectionRunner` with below config.

```java
SpringApplication.run(
    SectionRunner.class,
    "--section=sec01",
    "--config=03-consumer-group"
);
```

### Demo 4: Consuming Multiple Topics

1. Start the `SectionRunner` with below config.

```java
SpringApplication.run(
    SectionRunner.class,
    "--section=sec01",
    "--config=04-multiple-topics"
);
```

2. Open **two separate terminals** and produce messages to two topics:

```bash
./kafka-console-producer.sh \
   --bootstrap-server localhost:9092 \
   --topic demo-topic1 \
   --timeout 0

./kafka-console-producer.sh \
   --bootstrap-server localhost:9092 \
   --topic demo-topic2 \
   --timeout 0
```