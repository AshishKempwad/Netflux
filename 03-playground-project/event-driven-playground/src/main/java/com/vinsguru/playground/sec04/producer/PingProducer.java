package com.vinsguru.playground.sec04.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class PingProducer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(PingProducer.class);
    private static final String PING_OUT = "ping-out";
    private final StreamBridge streamBridge;

    public PingProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void run(String... args) throws Exception {
        // windows  : "ping", "-n", "10", "google.com"
        // linux/mac: "ping", "-c", "10", "google.com"
        var process = new ProcessBuilder("ping", "-c", "10", "google.com")
                .redirectErrorStream(true)
                .start();
        try (var reader = process.inputReader()) {
            reader.lines()
                  .forEach(line -> {
                      log.info("sending: {}", line);
                      this.streamBridge.send(PING_OUT, line);
                  });
        }
    }

}