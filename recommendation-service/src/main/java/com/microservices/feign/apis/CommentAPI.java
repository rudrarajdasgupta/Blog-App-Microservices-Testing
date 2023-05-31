package com.microservices.feign.apis;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.models.Comment;

@FeignClient(name="comment-service")
public interface CommentAPI {

	@GetMapping("/comments/post/{postId}")
	public ResponseEntity<List<Comment>> retrieveAllCommentsOfPost(@PathVariable long postId);

	
}
