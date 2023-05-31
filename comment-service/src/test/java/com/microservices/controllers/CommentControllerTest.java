package com.microservices.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.microservices.models.Comment;
import com.microservices.services.CommentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

	@Mock
	private CommentService commentService;
	private Comment comment1;
	private Comment comment2;
	private List<Comment> commentList;
	
	@InjectMocks
	private CommentController commentController;
	
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		comment1 = new Comment(1L, "comment1", new Timestamp(new Date().getTime()), 1L);
		comment2 = new Comment(2L, "comment2", new Timestamp(new Date().getTime()), 1L);
		commentList = new ArrayList<>();
		commentList.add(comment1);
		commentList.add(comment2);
		mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
	}

	@AfterEach
	void tearDown() throws Exception {
		comment1 = comment2 = null;
		commentList = null;
	}

	@Test
	void testSearchComments() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        List<Comment> cL = new ArrayList<>();
        cL.add(comment1);
        
		when(commentService.searchComments("comment1")).thenReturn(cL);
         
        ResponseEntity<List<Comment>> responseEntity = commentController.searchComments("comment1");
         
        assertEquals(200, responseEntity.getStatusCodeValue());
	}

	@Test
	void testSortedAscComments() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        
		when(commentService.sortedAscComments()).thenReturn(commentList);
         
        ResponseEntity<List<Comment>> responseEntity = commentController.sortedAscComments();
         
        assertEquals(200, responseEntity.getStatusCodeValue());
	}

	@Test
	void testRetrieveAllCommentsOfPost() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        
		when(commentService.getAllCommentsOfPost(1L)).thenReturn(commentList);
         
        ResponseEntity<List<Comment>> responseEntity = commentController.retrieveAllCommentsOfPost(1L);
         
        assertEquals(200, responseEntity.getStatusCodeValue());
	}

	@Test
	void testAddComment() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        when(commentService.addComment(any(Comment.class))).thenReturn(any());
         
        ResponseEntity<Comment> responseEntity = commentController.addComment(comment1);
         
        assertEquals(201, responseEntity.getStatusCodeValue());
	}

	@Test
	void testDeleteComment() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
         
        HttpStatus httpStatus = commentController.deleteComment(1L);
         
        assertEquals(202, httpStatus.value());
	}

	@Test
	void testDeleteCommentsByPostId() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
         
        HttpStatus httpStatus = commentController.deleteCommentsByPostId(1L);
        
        assertEquals(202, httpStatus.value());
	}

	@Test
	void testUpdateComment() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        when(commentService.updateComment(any(Comment.class))).thenReturn(any());
         
        ResponseEntity<Comment> responseEntity = commentController.updateComment(comment1);
         
        assertEquals(202, responseEntity.getStatusCodeValue());
	}

}
