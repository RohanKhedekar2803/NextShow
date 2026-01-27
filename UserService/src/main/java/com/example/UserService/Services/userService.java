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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.UserService.Entities.User;
import com.example.UserService.Repository.userRepository;
import com.thoughtworks.xstream.mapper.Mapper.Null;

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
                .save(User.builder().username(username).password(passwordEncoder.encode("OAUTH_PASSWORD"))
                        .roles(roles).build());

    }

    public Map<String, String> loginUser(String username, String password) {

        Map<String, String> mp = new HashMap<>();
        if (password.equals("OAUTH_PASSWORD")) {
            return Map.of("error", "Invalid credentials"); // case where user has used google as idp for authentication
                                                           // so password used for it is is not valid.
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)); // GIVE CONTROLL TO AUTH MANAGER TO HANLE
                                                                              // LOGIN FLOW
        List<String> Role = null;
        Optional<User> userfromDB = userRepository.findByUsername(username);
        if (userfromDB.isPresent()) {
            Role = userfromDB.get().getRoles();
        }

        Map<String, String> jwtToken = jwtService.generateToken(username, Role);

        if (authentication.isAuthenticated() && userfromDB.isPresent()) {
            mp.put("JWT-ACCESS-TOKEN", jwtToken.get("access_token"));
            mp.put("JWT-REFRESH-TOKEN", jwtToken.get("refresh_token"));
            mp.put("username", userfromDB.get().getUsername());
            mp.put("user_id", userfromDB.get().getId().toString());
            return mp;
        } else {
            return Map.of("error", "Invalid credentials");
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

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
