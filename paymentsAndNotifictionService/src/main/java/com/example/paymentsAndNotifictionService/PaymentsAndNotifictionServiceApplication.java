package com.example.paymentsAndNotifictionService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@EnableFeignClients // Enables Feign
@SpringBootApplication
public class PaymentsAndNotifictionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentsAndNotifictionServiceApplication.class, args);
	}

}
