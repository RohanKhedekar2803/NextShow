package com.example.booking_service.Feign;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.example.booking_service.Security.JwtContext;

import feign.RequestInterceptor;
import feign.RequestTemplate;

// @Configuration
// public class FeignClientInterceptor implements RequestInterceptor {

// @Override
// public void apply(RequestTemplate template) {
//     // Get the current authentication object from security context
//     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//     // Debugging output
//     System.out.println("Feign - Authentication: " + authentication);

//     // Check if credentials are available and are a String (JWT token)
//     if (authentication != null && authentication.getCredentials() instanceof String token) {
//         System.out.println("Feign - JWT Token: " + token); // Debugging output
//         template.header("Authorization", "Bearer " + token);
//     }
// }
// }
@Component
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            Object token = requestAttributes.getAttribute("jwt", RequestAttributes.SCOPE_REQUEST);
            if (token instanceof String jwt) {
                template.header("Authorization", "Bearer " + jwt);
            }
        }
    }
}
