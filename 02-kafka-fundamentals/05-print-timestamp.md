
## Print Timestamp

```bash
./kafka-console-consumer.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --property print.offset=true \
    --property print.timestamp=true \
    --from-beginning
```