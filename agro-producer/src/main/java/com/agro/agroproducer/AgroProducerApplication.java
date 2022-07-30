package com.agro.agroproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AgroProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgroProducerApplication.class, args);
	}

}
