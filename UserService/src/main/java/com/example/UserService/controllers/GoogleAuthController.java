package com.example.UserService.controllers;

import java.net.URI;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.UserService.Entities.User;
import com.example.UserService.Repository.userRepository;
import com.example.UserService.Services.CustomUserDetailsService;
import com.example.UserService.Services.JWTService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth/google")
public class GoogleAuthController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private userRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${app.nextshow_domain}")
    private String nextshowDomain;

    @Value("${app.gateway_port}")
    private String gatewayPort;

    @Value("${app.frontend.base-url}")
    private String frontendBaseUrl;

    @GetMapping("/callback")
    public ResponseEntity<Void> handleGoogleCallback(@RequestParam("code") String code) {

        try {
            // 1️⃣ Exchange authorization code for tokens
            String tokenEndpoint = "https://oauth2.googleapis.com/token";

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add(
                    "redirect_uri",
                    "http://" + nextshowDomain + ":" + gatewayPort + "/auth/google/callback");
            params.add("grant_type", "authorization_code");
            System.out.println("redirect url is" + "redirect_uri" + "http://" + nextshowDomain + ":" + gatewayPort
                    + "/auth/google/callback");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<Map<String, Object>> tokenResponse = restTemplate.postForEntity(tokenEndpoint, request,
                    (Class<Map<String, Object>>) (Class<?>) Map.class);

            if (tokenResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String idToken = (String) tokenResponse.getBody().get("id_token");

            // 2️⃣ Get user info from Google
            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;

            ResponseEntity<Map<String, Object>> userInfoResponse = restTemplate.getForEntity(userInfoUrl,
                    (Class<Map<String, Object>>) (Class<?>) Map.class);

            if (userInfoResponse.getStatusCode() != HttpStatus.OK ||
                    userInfoResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String email = (String) userInfoResponse.getBody().get("email");
            log.info("Google login email: {}", email);

            // 3️⃣ Create user if not exists
            try {
                userDetailsService.loadUserByUsername(email);
            } catch (Exception ex) {
                User user = new User();
                user.setUsername(email);
                user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                user.setRoles(List.of("USER"));
                userRepository.save(user);
            }

            // 4️⃣ Generate JWTs
            Map<String, String> jwtToken = jwtService.generateToken(email, List.of("USER"));

            // 5️⃣ Redirect with BOTH tokens in fragment
            String redirectUrl = frontendBaseUrl
                    + "/homepage"
                    + "#accessToken=" + jwtToken.get("access_token")
                    + "&refreshToken=" + jwtToken.get("refresh_token");

            HttpHeaders redirectHeaders = new HttpHeaders();
            redirectHeaders.setLocation(URI.create(redirectUrl));

            return new ResponseEntity<>(redirectHeaders, HttpStatus.FOUND);

        } catch (Exception e) {
            log.error("Exception in Google callback", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
