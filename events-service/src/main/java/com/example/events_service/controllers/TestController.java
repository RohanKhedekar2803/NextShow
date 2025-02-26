package com.example.events_service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("eventsAPI/Restricted")
public class TestController {

    @GetMapping("hey")
    public String getAllEvents() {
        return "hey";
    }

    @GetMapping("hello")
    public String getAllEvents1() {
        return "hello";
    }
}