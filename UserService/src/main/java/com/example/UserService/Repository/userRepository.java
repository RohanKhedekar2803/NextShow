package com.example.UserService.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.UserService.Entities.User;

public interface userRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
