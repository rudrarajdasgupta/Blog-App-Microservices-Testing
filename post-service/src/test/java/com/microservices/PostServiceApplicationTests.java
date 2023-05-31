package com.microservices;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.models.Post;
import com.microservices.repositories.PostRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PostServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	PostRepository postRepository;

	private Post post1;

	@BeforeEach
	void setUp() throws Exception {
		post1 = new Post("Title", "Description", 1L);
	}


	@Test
	void testAddPost() throws Exception {

		mockMvc.perform(post("/posts")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(post1)))
				.andExpect(status().isCreated());
	}

	@Test
	void testAddPostWithoutBody() throws Exception {

		mockMvc.perform(post("/posts")
						.contentType("application/json"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testRetrieveAllPosts() throws Exception {

		Post post = postRepository.save(post1);

		mockMvc.perform(get("/posts/user/" + post.getUserId())
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(containsString("Title")));
	}

	@Test
	void testRetrievePostTitles() throws Exception {

		Post post = postRepository.save(post1);

		mockMvc.perform(get("/posts/titles")
						.header("user-id", post.getUserId())
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(containsString("Title")));
	}

	@Test
	void testGetPostsSummary() throws Exception {

		Post post = postRepository.save(post1);

		mockMvc.perform(get("/posts/summary")
						.header("user-id", post.getUserId())
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(containsString("Title")));
	}

	@Test
	void testSearchPostByTitle() throws Exception {

		postRepository.save(post1);

		mockMvc.perform(get("/posts/search")
						.header("user-id", "1")
						.param("searchString", "Title")
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(containsString("Title")));
	}

	@Test
	void testDeletePost() throws Exception {

		Post post = postRepository.save(post1);

		mockMvc.perform(delete("/posts/" + post.getId())
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(containsString("ACCEPTED")));
	}

	@Test
	void testDeletePostByUserId() throws Exception {

		Post post = postRepository.save(post1);

		mockMvc.perform(delete("/posts/user/" + post.getUserId())
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(containsString("ACCEPTED")));
	}

	@Test
	void testUpdatePost() throws Exception {

		Post post = postRepository.save(post1);

		mockMvc.perform(put("/posts")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(post)))
				.andExpect(status().isAccepted());
	}
}
