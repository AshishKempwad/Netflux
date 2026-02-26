## Offset Reset

### Prerequisite

Please ensure you have completed the [Offset Tracking Demo](./10-offset-tracking.md) and the consumer group `cg` exists.

### Offset Reset Options

Kafka allows resetting the offsets **tracked for a consumer group** using different strategies.

| Option                                  | Description                                         |
| --------------------------------------- | --------------------------------------------------- |
| `--shift-by 3`                          | Move the offset **forward** by 3                    |
| `--shift-by -2`                         | Move the offset **backward** by 2                   |
| `--by-duration PT5M`                    | Reset offsets to **5 minutes ago**                  |
| `--to-datetime 2026-01-01T00:00:00.000` | Reset offsets to a **specific date-time**           |
| `--to-earliest`                         | Reset offsets to the **beginning** of the partition |
| `--to-latest`                           | Reset offsets to the **end** of the partition       |

### Dry Run vs Execute

* `--dry-run`

  * Does **not** change offsets
  * Shows the **new offsets** that would be applied

* `--execute`

  * Actually **resets** the offsets

### Commands

#### Dry run (preview the change)

```bash
./kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --group cg \
  --reset-offsets \
  --shift-by -3 \
  --dry-run
```

#### Reset offsets by shifting backward

```bash
./kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --group cg \
  --reset-offsets \
  --shift-by -3 \
  --execute
```

#### Reset offsets by duration

```bash
./kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --group cg \
  --reset-offsets \
  --by-duration PT5M \
  --execute
```

#### Reset offsets to the beginning

```bash
./kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --group cg \
  --reset-offsets \
  --to-earliest \
  --execute
```

#### Reset offsets to the end

```bash
./kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --group cg \
  --reset-offsets \
  --to-latest \
  --execute
```

#### Reset offsets to a specific date-time

```bash
./kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --group cg \
  --reset-offsets \
  --to-datetime 2023-01-01T01:00:00.000 \
  --execute
```
