## Building a Reactive Consumer

### Do NOT use `Consumer<Flux<T>>`

```java
@Bean
public Consumer<Flux<String>> consumer() {
    return flux -> flux
        .doOnNext(msg -> log.info("received: {}", msg))
        .subscribe();
}
```

**Why this is wrong:**

* You are **subscribing manually**
* Errors and backpressure are **outside Spring Cloud Stream control**


### Use `Function`

```java
@Bean
public Function<Flux<String>, Mono<Void>> consumer() {
    return flux -> flux
        .doOnNext(msg -> log.info("received: {}", msg))
        .then();
}
```

**Why this is correct:**

* Spring Cloud Stream manages the subscription
* Backpressure is respected
* Errors are propagated correctly
* Aligns with reactive programming principles

### Demo: Reactive Consumer

1. Start the application. Run `SectionRunner` with the following configuration.

```java
SpringApplication.run(
        SectionRunner.class,
        "--section=sec02",
        "--config=01-reactive-consumer"
);
```

2. Produce messages and observe them being logged by the reactive consumer.

```bash
./kafka-console-producer.sh \
  --bootstrap-server localhost:9092 \
  --topic demo-topic \
  --timeout 0
```