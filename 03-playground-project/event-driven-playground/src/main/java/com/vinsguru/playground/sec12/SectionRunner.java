package com.vinsguru.playground.sec12;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


public class SectionRunner {

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.consumer")
	static class Consumer {

		static void main() {
			SpringApplication.run(
					Consumer.class, "--section=sec12", "--config=01-consumer"
			);
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.producer")
	static class Producer {

		static void main() {
			SpringApplication.run(
					Producer.class, "--section=sec12", "--config=02-producer"
			);
		}

	}

}
