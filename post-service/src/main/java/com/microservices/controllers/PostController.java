package com.microservices.controllers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservices.models.Post;
import com.microservices.services.PostService;

@RestController
public class PostController {

	private static Logger logger = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private PostService postService;

	@GetMapping("/posts/user/{userId}")
	public ResponseEntity<List<Post>> retrieveAllPosts(@PathVariable("userId") long userId) {
		logger.info("retrieve all posts controller...!");
		List<Post> posts = postService.retrieveAllPosts(userId);
		return new ResponseEntity<List<Post>>(posts, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/posts/search")
	public ResponseEntity<List<Post>> searchPostByTitle(@RequestParam("searchString") String searchString, @RequestHeader("user-id") long userId) {
		logger.info("search posts by titles...!");
		List<Post> posts = postService.searchPostByTitle(userId, searchString);
		return new ResponseEntity<List<Post>>(posts, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/posts/summary")
	public ResponseEntity<Map<Long, String>> getPostsSummary(@RequestHeader("user-id") long userId) {
		logger.info("get posts summary...!");
		Map<Long, String> summary = postService.getPostsSummary(userId);
		return new ResponseEntity<Map<Long, String>>(summary, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/posts/titles")
	public ResponseEntity<Set<String>> retrievePostTitles(@RequestHeader("user-id") long userId) {
		logger.info("retrieve posts titles...!");
		Set<String> titles = postService.retrievePostTitles(userId);
		return new ResponseEntity<Set<String>>(titles, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/posts/{id}")
	public ResponseEntity<Post> retrievePostById(@PathVariable long id) {

		logger.info("retrieve post controller...!");
		Post post = postService.getPost(id);
		return new ResponseEntity<Post>(post, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/posts")
	public ResponseEntity<Post> createPost(@RequestBody Post post) {

		logger.info("create post controller...!");
		Post savedPost = postService.createPost(post);
		return new ResponseEntity<Post>(savedPost, new HttpHeaders(), HttpStatus.CREATED);
	}

	@DeleteMapping("/posts/{id}")
	public HttpStatus deletePost(@PathVariable long id) throws Exception {

		logger.info("delete post controller...!");
		postService.deletePost(id);
		return HttpStatus.ACCEPTED;
	}
	
	@DeleteMapping("/posts/user/{userId}")
	public HttpStatus deletePostByUserId(@PathVariable long userId) {

		logger.info("delete post controller...!");
		postService.deletePostByUserId(userId);
		return HttpStatus.ACCEPTED;
	}

	@PutMapping("/posts")
	public ResponseEntity<Post> updatePost(@RequestBody Post post) {

		logger.info("update post controller...!");
		Post updatedPost = postService.updatePost(post);
		return new ResponseEntity<Post>(updatedPost, new HttpHeaders(), HttpStatus.ACCEPTED);
	}
}
