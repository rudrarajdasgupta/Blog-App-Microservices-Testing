package com.microservices.feign.apis;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.models.Comment;

@FeignClient("COMMENT-SERVICE")
public interface CommentAPI {

	@DeleteMapping("/comments/post/{postId}")
	public HttpStatus deleteCommentsByPostId(@PathVariable long postId);
}
