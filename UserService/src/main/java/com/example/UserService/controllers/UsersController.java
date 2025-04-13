package com.example.UserService.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserService.Services.userService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("auth")
public class UsersController {

    @Autowired
    private userService userService;

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password,
            @RequestParam(defaultValue = "USER") List<String> roles) {
        userService.registerUser(username, password, roles);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public Map<String, String> loginUser(@RequestParam String username, @RequestParam String password) {
        log.info("in login");
        Map<String, String> map = userService.loginUser(username, password);
        return map;
    }

    @GetMapping("/getuser/{userId}")
    public ResponseEntity<String> getEmail(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.retrive_email_from_ID(userId));
    }

}
