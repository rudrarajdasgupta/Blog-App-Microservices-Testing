package com.microservices.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.exceptions.ResourceAlreadyExistsException;
import com.microservices.exceptions.ResourceNotFoundException;
import com.microservices.feign.apis.CommentAPI;
import com.microservices.models.Post;
import com.microservices.repositories.PostRepository;


@Service
public class PostService {

	private static Logger logger = LoggerFactory.getLogger(PostService.class);

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentAPI commentApi;


	public List<Post> retrieveAllPosts(Long userId) throws ResourceNotFoundException{
		logger.info("retrieveAllPosts {}", this.getClass().getName());
		List<Post> posts =postRepository.findByUserId(userId);
		if(!posts.isEmpty()) {
			return posts;
		} else {
			logger.error("retrieveAllPosts {} Record not found", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}

	public List<Post> searchPostByTitle(Long userId, String searchString) throws ResourceNotFoundException{
		logger.info("searchPostByTitle {}", this.getClass().getName());
		List<Post> posts = postRepository.findByUserId(userId);
		if(!posts.isEmpty()) {
			return posts.stream().filter(e -> e.getTitle().contains(searchString)).collect(Collectors.toList());

		} else {
			logger.error("searchPostByTitle {}  Record not found", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}

	public Map<Long, String> getPostsSummary(Long userId) throws ResourceNotFoundException{
		logger.info("getPostsSummary {}", this.getClass().getName());
		List<Post> posts = postRepository.findByUserId(userId);
		if(!posts.isEmpty()) {
			return posts.stream().collect(Collectors.toMap(Post::getId, p -> p.getTitle() + " " + p.getDescription()));
		} else {
			logger.error("getPostsSummary {}", this.getClass().getName() + " Record not found");
			throw new ResourceNotFoundException();
		}
	}

	public Set<String> retrievePostTitles(Long userId) throws ResourceNotFoundException{
		logger.info("retrievePostTitles {}", this.getClass().getName());
		List<Post> posts = postRepository.findByUserId(userId);
		if(!posts.isEmpty()) {
			return posts.stream().map(Post::getTitle).collect(Collectors.toSet());
		} else {
			logger.error("retrievePostTitles {} Record not found ", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}

	public Post getPost(long id) throws ResourceNotFoundException{
		Optional<Post> post = postRepository.findById(id);
		if(post.isPresent()) {
			return post.get();
		} else {
			logger.error("retrievePostTitles {} Record not found ", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}
	@Transactional
	public Post createPost(Post post) throws ResourceAlreadyExistsException{
		logger.info("createPost {}", this.getClass().getName());
		if(post.getId() != null) {
			Optional<Post> p = postRepository.findById(post.getId());
			if(p.isPresent()) {
				logger.error("createPost {} Record not found", this.getClass().getName());
				throw new ResourceAlreadyExistsException();
			}
		}
		return postRepository.save(post);
	}

	@Transactional
	public void deletePost(long id) throws ResourceNotFoundException {
		logger.info("deletePost {}", this.getClass().getName());
		Optional<Post> post = postRepository.findById(id);
		if(post.isPresent()) {
			commentApi.deleteCommentsByPostId(id);
			postRepository.deleteById(id);
		} else {
			logger.error("deletePost {} Record not found ", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}
	@Transactional
	public Post updatePost(Post post) throws ResourceNotFoundException{
		logger.info("updatePost {}", this.getClass().getName());
		Optional<Post> p = postRepository.findById(post.getId());
		if(p.isPresent()) {
			return postRepository.save(post);
		} else {
			logger.error("updatePost {} Record not found ", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}
	@Transactional
	public void deletePostByUserId(Long userId) throws ResourceNotFoundException{
		logger.info("deletePostByUserId {}", this.getClass().getName());
		logger.info("deletePost {}", this.getClass().getName());
		List<Post> posts = postRepository.findByUserId(userId);
		if(!posts.isEmpty()) {
			postRepository.deleteByUserId(userId);
		} else {
			logger.error("deletePost {} Record not found ", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}
}
