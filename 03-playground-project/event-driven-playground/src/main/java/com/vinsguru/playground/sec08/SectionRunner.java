package com.vinsguru.playground.sec08;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


public class SectionRunner {

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.consumer")
	static class Consumer {

		static void main() {
			SpringApplication.run(Consumer.class, "--section=sec08", "--config=01-consumer");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.processor")
	static class Processor {

		static void main() {
			SpringApplication.run(Processor.class, "--section=sec08", "--config=05-notification-processor");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.producer")
	static class Producer {

		static void main() {
			SpringApplication.run(Producer.class, "--section=sec08", "--config=02-producer");
		}

	}

}
