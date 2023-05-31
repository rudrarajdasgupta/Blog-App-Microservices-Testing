package com.microservices.feign.apis;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.models.Post;

@FeignClient("POST-SERVICE")
public interface PostAPI {
	
	@DeleteMapping("/posts/user/{userId}")
	public HttpStatus deletePostByUserId(@PathVariable long userId);
	
	@GetMapping("/posts/user/{userId}")
	public ResponseEntity<List<Post>> retrieveAllPosts(@PathVariable("userId") long userId);
}
