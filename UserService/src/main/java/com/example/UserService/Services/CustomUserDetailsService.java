package com.example.UserService.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.UserService.Entities.User;
import com.example.UserService.Entities.userPrincipal;
import com.example.UserService.Repository.userRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private userRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found" + username);
        }

        System.out.println("request is in spirng security");
        return new userPrincipal(user.get());
    }

}
