package com.example.nextshow_discovery_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NextshowDiscoveryClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(NextshowDiscoveryClientApplication.class, args);
	}

}
