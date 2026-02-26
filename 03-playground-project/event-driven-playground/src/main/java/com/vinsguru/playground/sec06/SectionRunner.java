package com.vinsguru.playground.sec06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


public class SectionRunner {

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.consumer")
	static class Consumer1 {

		static void main() {
			SpringApplication.run(Consumer1.class, "--section=sec06", "--config=01-consumer");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.consumer")
	static class Consumer2 {

		static void main() {
			SpringApplication.run(Consumer2.class, "--section=sec06", "--config=01-consumer");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.consumer")
	static class Consumer3 {

		static void main() {
			SpringApplication.run(Consumer3.class, "--section=sec06", "--config=01-consumer");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.producer")
	static class Producer {

		static void main() {
			SpringApplication.run(Producer.class, "--section=sec06", "--config=02-producer");
		}

	}

}
