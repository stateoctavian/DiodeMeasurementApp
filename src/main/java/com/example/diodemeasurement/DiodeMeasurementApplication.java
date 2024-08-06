package com.example.diodemeasurement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DiodeMeasurementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiodeMeasurementApplication.class, args);
	}

}
