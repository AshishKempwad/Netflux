## Kafka Cluster Demo

This demo walks through starting a **3-broker Kafka cluster**, creating a topic, and observing leader changes.

### Step 1: Stop Any Existing Kafka Containers

If you previously started Kafka containers from **`01-kafka-setup`**, stop them first to avoid port and cluster conflicts.

Run this command from the `01-kafka-setup` directory:

```bash
docker compose down
```

### Step 2: Start the Kafka Cluster

Launch the cluster:

```bash
docker compose up
```

Wait ~10 seconds for the brokers to initialize.

Verify that all three containers (`kafka1`, `kafka2`, `kafka3`) are running:

```bash
docker ps -a
```

You should see all three Kafka containers in the `Up` state.

### Step 3: Access the Kafka CLI (kafka1)

Exec into the `kafka1` container:

```bash
docker exec -it kafka1 bash
```

### Step 4: Create a Topic

Create a topic with:

* **2 partitions**
* **Replication factor = 3**

```bash
./kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --create \
    --partitions 2 \
    --replication-factor 3
```

### Step 5: Describe the Topic

Describe the topic to inspect its metadata:

```bash
./kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --describe
```

Observe:

* Number of partitions
* Partition leaders
* Replica assignments
* In-sync replicas (ISR)

Stop one of the brokers:

```bash
docker stop kafka2
```

Describe the topic again and observe:

* How partition leaders change
* How replicas and ISR are updated

You can repeat this by stopping and starting different brokers to see how Kafka maintains availability.

>Note: Please ensure that at least 2 nodes are running in a 3-node cluster for the cluster to function properly, as a majority is required to elect a controller.
