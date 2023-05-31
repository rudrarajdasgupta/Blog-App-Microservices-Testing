package com.microservices.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.exceptions.ResourceAlreadyExistsException;
import com.microservices.exceptions.ResourceNotFoundException;
import com.microservices.models.Comment;
import com.microservices.repositories.CommentRepository;

@Service
public class CommentService {

	private static Logger logger = LoggerFactory.getLogger(CommentService.class);

	@Autowired
	private CommentRepository commentRepository;


	public List<Comment> getAllCommentsOfPost(Long postId) throws ResourceNotFoundException{
		logger.info("getAllCommentsOfPost {}", this.getClass().getName());
		List<Comment> comments = commentRepository.findByPostId(postId);
		if(!comments.isEmpty()) {
			return comments;
		} else {
			logger.error("getAllCommentsOfPost {} Record not found", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}

	public List<Comment> searchComments(String searchString) throws ResourceNotFoundException{
		logger.info("searchComments {}", this.getClass().getName());
		List<Comment> comments = commentRepository.searchComment(searchString);
		if(!comments.isEmpty()) {
			return comments;
		} else {
			logger.error("getAllCommentsOfPost {} Record not found", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}

	public List<Comment> sortedAscComments() throws ResourceNotFoundException{
		logger.info("sortedComments {}", this.getClass().getName());
		List<Comment> comments = commentRepository.ascComment();
		if(!comments.isEmpty()) {
			return comments;
		} else {
			logger.error("getAllCommentsOfPost {} Record not found", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}

	@Transactional
	public Comment addComment(Comment comment) throws ResourceAlreadyExistsException{
		logger.info("addComment {}", this.getClass().getName());
		if(comment.getId() != null) {
			Optional<Comment> c = commentRepository.findById(comment.getId());
			if(c.isPresent()) {
				logger.error("addComment {} Record not found", this.getClass().getName());
				throw new ResourceAlreadyExistsException();
			}
		}
		Timestamp timestamp = new Timestamp(new Date().getTime());
		comment.setTime(timestamp);
		return commentRepository.save(comment);
	}

	@Transactional
	public void deleteComment(long id) throws ResourceNotFoundException{
		logger.info("deleteComment {}", this.getClass().getName());
		Optional<Comment> comment = commentRepository.findById(id);
		if (comment.isPresent()) {
			commentRepository.deleteById(id);
		} else {
			logger.error("deleteComment {} Comment not found", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}

	@Transactional
	public void deleteCommentsByPostId(long postId) throws ResourceNotFoundException {
		logger.info("updateComment {}", this.getClass().getName());
		List<Comment> comments = commentRepository.findByPostId(postId);
		if (!comments.isEmpty()) {
			commentRepository.deleteByPostId(postId);
		}else {
			logger.error("updateComment {}  Comment not found", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}



	@Transactional
	public Comment updateComment(Comment comment) throws ResourceNotFoundException{
		logger.info("updateComment {}", this.getClass().getName());
		Optional<Comment> c = commentRepository.findById(comment.getId());
		if (c.isPresent()) {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			comment.setTime(timestamp);
			return commentRepository.save(comment);
		} else {
			logger.error("deleteComment {}  Comment not found", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}
}