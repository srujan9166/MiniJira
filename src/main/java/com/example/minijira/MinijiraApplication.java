package com.example.minijira;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
 @EnableCaching
public class MinijiraApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinijiraApplication.class, args);
	}

}
