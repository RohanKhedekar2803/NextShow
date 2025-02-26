package com.example.UserService.feign_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "movies-service")
public interface MoviesClient {

    @GetMapping("/movies/name")
    public String getMovieName();

}
