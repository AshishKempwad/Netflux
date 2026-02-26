## Running Kafka using Docker Compose

Use the provided **Docker Compose YAML** to start Kafka. Wait until the Kafka container is fully up and running. 

```bash
docker compose up
```
- You can use `-d` option to run in the detached mode.

## Accessing the Kafka container

- Exec into the Kafka container

```bash
docker exec -it kafka bash
```

- You will land in:

```
/opt/kafka/bin
```

## Kafka CLI tools

- List the available command-line tools

```bash
ls -l
```

- You will see Kafka CLI scripts such as

* `kafka-topics.sh`
* `kafka-console-producer.sh`
* `kafka-console-consumer.sh`


### Important note about PATH

This directory is **not part of the `PATH` environment variable**. So, you must invoke tools/scripts using `./`:

```bash
./kafka-topics.sh
```

## Kafka configuration files

- Kafka configuration files are located at

```
/opt/kafka/config
```

- Key files you may notice:

* `server.properties`
* `broker.properties`
* `controller.properties`
* `producer.properties`
* `consumer.properties`

## Official configuration reference

Please refer to the official documentation [https://kafka.apache.org/41/configuration/](https://kafka.apache.org/41/configuration/)