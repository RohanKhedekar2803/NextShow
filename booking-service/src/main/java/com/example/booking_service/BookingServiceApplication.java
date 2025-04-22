package com.example.booking_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@EnableFeignClients
@SpringBootApplication
public class BookingServiceApplication {

	public static void main(String[] args) {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
		ConfigurableApplicationContext context = SpringApplication.run(BookingServiceApplication.class, args);

		Environment env = context.getEnvironment();
		System.out.println("====== Application Properties ======");
		System.out.println("server.port = " + env.getProperty("kafka.producer.bootstrap-servers"));
		System.out.println("=====================================");
	}

}
