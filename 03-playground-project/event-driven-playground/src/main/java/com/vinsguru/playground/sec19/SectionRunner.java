package com.vinsguru.playground.sec19;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class SectionRunner {

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.consumer")
	public static class DigitalDeliveryConsumer {

		static void main() {
			SpringApplication.run(DigitalDeliveryConsumer.class, "--section=sec19", "--config=01-digital-consumer");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.consumer")
	public static class PhysicalDeliveryConsumer {

		static void main() {
			SpringApplication.run(PhysicalDeliveryConsumer.class, "--section=sec19", "--config=02-physical-consumer");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.processor")
	public static class Processor {

		static void main() {
			SpringApplication.run(Processor.class, "--section=sec19", "--config=03-processor");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.producer")
	public static class Producer {

		static void main() {
			SpringApplication.run(Producer.class, "--section=sec19", "--config=04-producer");
		}

	}

}
