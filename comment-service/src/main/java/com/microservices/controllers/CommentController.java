package com.microservices.controllers;

import java.util.List;

import com.microservices.models.Comment;
import com.microservices.services.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

	private static Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	private CommentService commentService;

	@GetMapping("/comments/search")
	public ResponseEntity<List<Comment>> searchComments(@RequestParam("searchString") String searchString) {
		logger.info("search comments of post ...!");
		List<Comment> comments = commentService.searchComments(searchString);
		return new ResponseEntity<List<Comment>>(comments, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/comments/sorted/asc")
	public ResponseEntity<List<Comment>> sortedAscComments() {
		logger.info("get sorted comments asc ...!");
		List<Comment> comments = commentService.sortedAscComments();
		return new ResponseEntity<List<Comment>>(comments, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/comments/post/{postId}")
	public ResponseEntity<List<Comment>> retrieveAllCommentsOfPost(@PathVariable long postId) {
		logger.info("retrieve all comments of post ...!");
		List<Comment> comments = commentService.getAllCommentsOfPost(postId);
		return new ResponseEntity<List<Comment>>(comments, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/comments")
	public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {

		logger.info("add comment controller...!");
		Comment savedComment = commentService.addComment(comment);
		return new ResponseEntity<Comment>(savedComment, new HttpHeaders(), HttpStatus.CREATED);
	}

	@DeleteMapping("/comments/{id}")
	public HttpStatus deleteComment(@PathVariable long id) throws Exception {

		logger.info("delete comment controller...!");
		commentService.deleteComment(id);
		return HttpStatus.ACCEPTED;
	}
	
	@DeleteMapping("/comments/post/{postId}")
	public HttpStatus deleteCommentsByPostId(@PathVariable long postId) throws Exception {

		logger.info("deleteCommentsByPostId controller...!");
		commentService.deleteCommentsByPostId(postId);
		return HttpStatus.ACCEPTED;
	}
	
	@PutMapping("/comments")
	public ResponseEntity<Comment> updateComment(@RequestBody Comment comment) {

		logger.info("update comment controller...!");
		Comment updatedComment = commentService.updateComment(comment);
		return new ResponseEntity<Comment>(updatedComment, new HttpHeaders(), HttpStatus.ACCEPTED);
	}
}