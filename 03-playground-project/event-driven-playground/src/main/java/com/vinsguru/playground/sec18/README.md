## Kafka Transactions

To avoid demo inconsistencies, **always start with a fresh Kafka state**.

```bash
docker compose down
docker compose up
```

**Reference:**
[https://docs.spring.io/spring-cloud-stream/reference/kafka/kafka-binder/transactional.html](https://docs.spring.io/spring-cloud-stream/reference/kafka/kafka-binder/transactional.html)


### Retry and DLQ Behavior (Transactional)

* When a transaction fails, **Spring retries the message**
* This retry is **transactional**, not normal framework retry

Key differences:

* Normal retry → framework-controlled
* Transaction retry → **broker-driven redelivery**
* `deliveryAttempt` header is **not updated**
* Retry attempts behave as `max-attempts - 1`
* After retries are exhausted, the message is sent to the **DLQ**

### Mandatory Configuration

Kafka transactions require a **non-empty transaction ID prefix**.

```properties
spring.cloud.stream.kafka.binder.transaction.transactionIdPrefix=tx-
```

For multiple application instances, the value **must be unique**.
Recommended pattern:

```properties
spring.cloud.stream.kafka.binder.transaction.transactionIdPrefix=tx-${spring.application.name}-${random.uuid}-
```

Ensure the producer waits for all in-sync replicas:

```properties
spring.cloud.stream.kafka.binder.transaction.producer.configuration.acks=all
```

### Start the Processor Application

```java
SpringApplication.run(
        SectionRunner.class,
        "--section=sec18",
        "--config=01-processor"
);
```

### Open 3 Terminals

- Console Producer

```bash
./kafka-console-producer.sh \
  --bootstrap-server localhost:9092 \
  --topic transfer-requests \
  --property key.separator=: \
  --property parse.key=true
```

- Consumer – `read_committed`

```bash
./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic transaction-requests \
  --property print.key=true \
  --isolation-level=read_committed
```

- Consumer – `read_uncommitted`

```bash
./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic transaction-requests \
  --property print.key=true \
  --isolation-level=read_uncommitted
```

- Produce Messages (One by One)

```text
1:{"fromAccount":"ac001","toAccount":"ac010","amount":100}
2:{"fromAccount":"ac002","toAccount":"ac020","amount":200}
3:{"fromAccount":"ac003","toAccount":"ac030","amount":300}
```

