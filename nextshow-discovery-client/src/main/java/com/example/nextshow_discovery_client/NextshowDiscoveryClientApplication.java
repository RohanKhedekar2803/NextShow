package com.example.nextshow_discovery_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@SpringBootApplication
@EnableEurekaServer
public class NextshowDiscoveryClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(NextshowDiscoveryClientApplication.class, args);
	}

}
