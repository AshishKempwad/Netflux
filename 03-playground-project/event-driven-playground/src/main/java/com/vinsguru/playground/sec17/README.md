## Dynamic Pause & Resume of Bindings

**Note:** To avoid demo inconsistencies, always start with a fresh Kafka state.

```bash
docker compose down
docker compose up
```

---

### Start the Consumer Application

```java
SpringApplication.run(
        Consumer.class,
        "--section=sec17",
        "--config=01-consumer"
);
```

### Start the Producer Application

```java
SpringApplication.run(
        Producer.class,
        "--section=sec17",
        "--config=02-producer"
);
```

### Observe the Behavior

* The producer publishes **one message every second**
* The consumer initially consumes messages normally
* Every **10 seconds**, `ConsumerLifecycleManager` toggles the binding state:

  * **RESUMED → PAUSED**
  * **PAUSED → RESUMED**

#### When the binding is **PAUSED**

* No new messages are consumed
* Offsets are not advanced
* Messages remain in Kafka

#### When the binding is **RESUMED**

* Consumption continues
* Messages are processed **from the last committed offset**
* No messages are lost or skipped
