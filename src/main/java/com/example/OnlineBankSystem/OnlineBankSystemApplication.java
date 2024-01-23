package com.example.OnlineBankSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineBankSystemApplication {
//TODO: add volumes: init in docker-compose
	public static void main(String[] args) {
		SpringApplication.run(OnlineBankSystemApplication.class, args);
	}

}
