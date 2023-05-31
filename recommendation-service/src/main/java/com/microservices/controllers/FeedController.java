package com.microservices.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.dtos.UserDTO;
import com.microservices.models.User;
import com.microservices.services.FeedService;

@RestController
public class FeedController {
	
	@Autowired
	private FeedService feedService;
	
	private static Logger logger = LoggerFactory.getLogger(FeedController.class);
	
	@GetMapping("/feed/{userId}")
	public ResponseEntity<UserDTO> getFeed(@PathVariable("userId") long userId) {

		logger.info("user feed...!");
		UserDTO user = feedService.getUserFeed(userId);
		return new ResponseEntity<UserDTO>(user, new HttpHeaders(), HttpStatus.OK);
	}
}
