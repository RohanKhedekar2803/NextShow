package com.example.paymentsAndNotifictionService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients // Enables Feign
@SpringBootApplication
public class PaymentsAndNotifictionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentsAndNotifictionServiceApplication.class, args);
	}

}
