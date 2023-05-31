package com.microservices.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservices.models.User;
import com.microservices.services.UserService;

import java.util.List;

@RestController
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Value("${server.port}")
    private String serverPort;

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<User>> retrieveAllUsers() {
		logger.info("retrieve all users controller...!");
		logger.info("Port Number {}",serverPort);
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<List<User>>(users, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> retrieveUserById(@PathVariable long id) {

		logger.info("retrieve user controller...!");
		User user = userService.getUser(id);
		return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {

		logger.info("create user controller...!");
		User savedUser = userService.createUser(user);
		return new ResponseEntity<User>(savedUser, new HttpHeaders(), HttpStatus.CREATED);
	}

	@DeleteMapping("/users/{id}")
	public HttpStatus deleteUser(@PathVariable long id) throws Exception {

		logger.info("delete user controller...!");
		userService.deleteUser(id);
		return HttpStatus.ACCEPTED;
	}

	@PutMapping("/users")
	public ResponseEntity<User> updateUser(@RequestBody User user) {

		logger.info("update user controller...!");
		User updatedUser = userService.updateUser(user);
		return new ResponseEntity<User>(updatedUser, new HttpHeaders(), HttpStatus.ACCEPTED);
	}
}
