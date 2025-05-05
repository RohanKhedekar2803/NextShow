package com.example.shows_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@EnableCaching
@EnableFeignClients
@SpringBootApplication
public class ShowsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShowsServiceApplication.class, args);
	}

}
