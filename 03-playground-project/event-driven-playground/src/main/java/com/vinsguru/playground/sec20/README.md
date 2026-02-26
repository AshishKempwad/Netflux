## Kafka Security

**Prerequisite**

Go to `01-kafka-setup` and stop any running kafka broker.

```
docker compose down
```

## Demo 1: SASL_PLAINTEXT

**Goal:** Enable authentication only (no encryption)

### Start Kafka

- Go to

```
05-kafka-security/01-sasl-plaintext
```

- Run

```
docker compose up
```

### Start Consumer

```
SpringApplication.run(
    Consumer.class,
    "--section=sec20",
    "--config=01-sasl-plaintext-consumer"
);
```

### Start Producer

```
SpringApplication.run(
    Producer.class,
    "--section=sec20",
    "--config=02-sasl-plaintext-producer"
);
```

### Observe

* Producer publishes messages periodically
  (controlled by `spring.cloud.stream.poller.fixed-delay`)
* Consumer receives the messages successfully

- Stop the cluster

```
docker compose down
```

## Demo 2: SASL_SSL

**Goal:** Enable authentication + encryption

### Start Kafka

- Go to

```
05-kafka-security/02-sasl-ssl
```

- Run

```
docker compose up
```

### Start Consumer

```
SpringApplication.run(
    Consumer.class,
    "--section=sec20",
    "--config=03-sasl-ssl-consumer"
);
```

### Start Producer

```
SpringApplication.run(
    Producer.class,
    "--section=sec20",
    "--config=04-sasl-ssl-producer"
);
```

### Observe

* Producer publishes messages periodically
* Consumer receives the messages securely (encrypted connection)

