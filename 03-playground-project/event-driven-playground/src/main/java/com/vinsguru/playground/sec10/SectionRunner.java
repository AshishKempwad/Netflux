package com.vinsguru.playground.sec10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

public class SectionRunner {

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.consumer")
	static class DigitalDeliveryConsumer {

		static void main() {
			SpringApplication.run(DigitalDeliveryConsumer.class, "--section=sec10", "--config=01-digital-consumer");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.consumer")
	static class FedExConsumer {

		static void main() {
			SpringApplication.run(FedExConsumer.class, "--section=sec10", "--config=02-fedex-consumer");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.consumer")
	static class USPSConsumer {

		static void main() {
			SpringApplication.run(USPSConsumer.class, "--section=sec10", "--config=03-usps-consumer");
		}

	}

	@EnableScheduling
	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.processor")
	static class Processor {

		static void main() {
			SpringApplication.run(Processor.class, "--section=sec10", "--config=04-processor");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.producer")
	static class Producer {

		static void main() {
			SpringApplication.run(Producer.class, "--section=sec10", "--config=05-producer");
		}

	}

}
