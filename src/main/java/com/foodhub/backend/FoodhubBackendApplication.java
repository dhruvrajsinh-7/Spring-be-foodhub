package com.foodhub.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FoodhubBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodhubBackendApplication.class, args);
	}

}
