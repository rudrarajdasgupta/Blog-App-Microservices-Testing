package com.microservices.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microservices.exceptions.ResourceAlreadyExistsException;
import com.microservices.exceptions.ResourceNotFoundException;
import com.microservices.feign.apis.CommentAPI;
import com.microservices.feign.apis.PostAPI;
import com.microservices.models.Post;
import com.microservices.models.User;
import com.microservices.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class UserService {


	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentAPI commentApi;

	@Autowired
	private PostAPI postApi;

	public List<User> getAllUsers() throws ResourceNotFoundException{
		List<User> users = userRepository.findAll();
		if(!users.isEmpty()) {
			logger.info("getAllUsers {}", this.getClass().getName());
			return users;
		} else {
			logger.error("getAllUsers {} User not Found!", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}

	public User getUser(Long id) throws ResourceNotFoundException{
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			logger.info("getUser {}", this.getClass().getName());
			return user.get();
		} else {
			logger.error("getUser {} User not Found!", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}

	public User createUser(User user) throws ResourceAlreadyExistsException {
		if(user.getId() != null) {
			Optional<User> u = userRepository.findById(user.getId());
			if (u.isPresent()) {
				logger.error("createUser {} User already exists!", this.getClass().getName());
				throw new ResourceAlreadyExistsException();
			}
		}
		logger.info("createUser {}", this.getClass().getName());
		return userRepository.save(user);	
	}

	@Transactional
	public void deleteUser(Long id) throws ResourceNotFoundException{
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			ResponseEntity<List<Post>> postsResult = postApi.retrieveAllPosts(id);
			List<Post> userPosts = postsResult.getBody();
			for(Post post: userPosts) {
				commentApi.deleteCommentsByPostId(post.getId());
			}
			postApi.deletePostByUserId(id);
			userRepository.deleteById(id);
		} else {
			logger.error("deleteUser {} User not Found!", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}

	@Transactional
	public User updateUser(User user) throws ResourceNotFoundException{
		Optional<User> u = userRepository.findById(user.getId());
		if(u.isPresent()) {
			logger.info("updateUser {}", this.getClass().getName());
			return userRepository.save(user);
		} else {
			logger.error("updateUser {} User not Found!", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}

}
