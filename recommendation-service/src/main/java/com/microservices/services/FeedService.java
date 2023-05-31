package com.microservices.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microservices.dtos.PostDTO;
import com.microservices.dtos.UserDTO;
import com.microservices.feign.apis.CommentAPI;
import com.microservices.feign.apis.PostAPI;
import com.microservices.feign.apis.UserAPI;
import com.microservices.models.Comment;
import com.microservices.models.Post;
import com.microservices.models.User;

@Service
public class FeedService {
	
	@Autowired
	private UserAPI userApi;
	
	@Autowired
	private CommentAPI commentApi;
	
	@Autowired
	private PostAPI postApi;
	
	
	public UserDTO getUserFeed(Long id) {
		
		UserDTO userDto = new UserDTO();
		
		ResponseEntity<User> userResult = userApi.retrieveUserById(id);
		
		User user = userResult.getBody();
		
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmailId(user.getEmailId());
		
		ResponseEntity<List<Post>> postResult = postApi.retrieveAllPosts(id);
		
		List<Post> posts = postResult.getBody();
		
		List<PostDTO> postList = new ArrayList<>();
		
		for(Post post: posts) {
			PostDTO postDto = new PostDTO();
			postDto.setId(post.getId());
			postDto.setTitle(post.getTitle());
			postDto.setDescription(post.getDescription());
			postDto.setUserId(post.getUserId());
			ResponseEntity<List<Comment>> commentResult = commentApi.retrieveAllCommentsOfPost(post.getId());
			List<Comment> comments = commentResult.getBody();
			postDto.setComments(comments);
			postList.add(postDto);
		}
		
		userDto.setPosts(postList);
		
		return userDto;
	}
}
