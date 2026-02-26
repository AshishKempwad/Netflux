package com.vinsguru.playground.sec18;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SectionRunner {

	static void main() {
		SpringApplication.run(SectionRunner.class, "--section=sec18", "--config=01-processor");
	}

}
