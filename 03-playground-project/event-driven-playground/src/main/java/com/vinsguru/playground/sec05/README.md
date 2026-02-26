## Building a Reactive Producer

**Note:** To avoid issues during the demos, it is recommended to spin up a fresh Kafka container for each demo to clean up the state.

```bash
docker compose down
docker compose up
```

### Demo 1: Simple Reactive Producer

1. Start the `SectionRunner.Consumer` with below config.

```java
SpringApplication.run(
        Consumer.class, "--section=sec05", "--config=01-consumer"
);
```

2. Start the `SectionRunner.Producer` with below config.

```java
SpringApplication.run(
        Producer.class, "--section=sec05", "--config=02-producer"
);
```

#### Observe
- The producer publishes messages periodically based on `Flux.interval(duration)`.
- The consumer receives those messages.

## Note
- `Flux` emits continuous stream of messages and not an individual message. So this supplier will be invoked once.

```java
@Bean
public Supplier<Flux<String>> reactiveProducer() {
    return () -> Flux.interval(Duration.ofMillis(500))
                     .map(i -> "msg-" + i)
                     .doOnNext(msg -> log.info("sending: {}", msg));
}
```
