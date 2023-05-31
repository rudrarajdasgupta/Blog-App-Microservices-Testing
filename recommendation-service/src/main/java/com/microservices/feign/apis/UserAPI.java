package com.microservices.feign.apis;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.models.User;

@FeignClient(name="user-service")
public interface UserAPI {
	@GetMapping("/users")
	public ResponseEntity<List<User>> retrieveAllUsers();
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> retrieveUserById(@PathVariable long id);
}
