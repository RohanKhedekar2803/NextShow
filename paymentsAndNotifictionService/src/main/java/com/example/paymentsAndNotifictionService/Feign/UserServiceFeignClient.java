package com.example.paymentsAndNotifictionService.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users-service")
public interface UserServiceFeignClient {

    @GetMapping("auth/getuser/{id}")
    String getUserById(@PathVariable("id") Long id);
}