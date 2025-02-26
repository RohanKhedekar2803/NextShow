package com.example.UserService.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestController {

    @GetMapping("user/api")
    public String registerUser() {

        return "welcomeddddd";
    }

}
