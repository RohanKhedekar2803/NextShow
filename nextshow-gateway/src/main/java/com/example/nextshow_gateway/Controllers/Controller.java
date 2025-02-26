package com.example.nextshow_gateway.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class Controller {

    @GetMapping("/gateway/health")
    public Mono<String> healthCheck() {
        return Mono.just("API Gateway is running and healthy");
    }

}
