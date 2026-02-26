package com.vinsguru.playground.sec02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SectionRunner {

	static void main(String[] args) {
		SpringApplication.run(
				SectionRunner.class,
				"--section=sec02",
				"--config=01-reactive-consumer"
		);
	}

}
