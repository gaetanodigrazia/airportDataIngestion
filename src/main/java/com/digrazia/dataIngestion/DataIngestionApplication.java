package com.digrazia.dataIngestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DataIngestionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataIngestionApplication.class, args);
	}

}
