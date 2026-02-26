package com.vinsguru.playground.sec17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

public class SectionRunner {

	@EnableScheduling
	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.consumer")
	static class Consumer {

		static void main() {
			SpringApplication.run(
					Consumer.class, "--section=sec17", "--config=01-consumer"
			);
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.producer")
	static class Producer {

		static void main() {
			SpringApplication.run(
					Producer.class, "--section=sec17", "--config=02-producer"
			);
		}

	}

}
