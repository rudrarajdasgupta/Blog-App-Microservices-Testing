package com.microservices.feign.apis;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.models.Post;

@FeignClient(name="post-service")
public interface PostAPI {
	@GetMapping("posts/user/{userId}")
	ResponseEntity<List<Post>> retrieveAllPosts(@PathVariable("userId") long userId);
}
