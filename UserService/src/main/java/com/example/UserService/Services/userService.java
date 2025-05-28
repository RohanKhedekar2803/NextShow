package com.example.UserService.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.UserService.Entities.User;
import com.example.UserService.Repository.userRepository;

@Service
public class userService {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    // regester from username password
    public void registerUser(String username, String password, List<String> roles) {
        // check with db
        Optional<User> userfromDB = userRepository.findByUsername(username);
        if (userfromDB.isPresent()) {
            throw new IllegalArgumentException("User already exists!");
        }
        if (roles.size() == 0) {
            roles.set(0, "USER");
        }
        userRepository.save(
                User.builder().username(username).password(passwordEncoder.encode(password)).roles(roles).build());
    }

    public void registerUserwithoutpassowrd(String username) {
        // check with db
        Optional<User> userfromDB = userRepository.findByUsername(username);
        if (userfromDB.isPresent()) {
            throw new IllegalArgumentException("User already exists!");
        }

        List<String> roles = new ArrayList<String>();
        roles.add("USER");

        long randomLong = new java.util.Random().nextLong();

        userRepository
                .save(User.builder().username(username).password(passwordEncoder.encode("OAUTH_PASSWORD" + randomLong))
                        .roles(roles).build());

    }

    public Map<String, String> loginUser(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)); // GIVE CONTROLL TO AUTH MANAGER TO HANLE
                                                                              // LOGIN FLOW
        List<String> Role = null;
        Optional<User> userfromDB = userRepository.findByUsername(username);
        if (userfromDB.isPresent()) {
            Role = userfromDB.get().getRoles();
        }
        Map<String, String> mp = new HashMap<>();
        if (authentication.isAuthenticated() && userfromDB.isPresent()) {
            mp.put("JWT-TOKEN", jwtService.generateToken(username, Role));
            mp.put("username", userfromDB.get().getUsername());
            mp.put("user_id", userfromDB.get().getId().toString());
            return mp;
        } else {
            mp.put("error", "Invalid credentials");
            return Map.of();
        }

    }

    // feign client receiver
    public String retrive_email_from_ID(Long id) {
        Optional<User> userFromDB = userRepository.findById(id);
        if (userFromDB.isPresent()) {
            if (userFromDB.get().getUsername().contains("@gmail.com")) {
                return userFromDB.get().getUsername();
            } else {
                return "NOT A VALID EMAIL_ID";
            }
        } else {
            return "NOT A VALID USER_ID";
        }

    }

}
