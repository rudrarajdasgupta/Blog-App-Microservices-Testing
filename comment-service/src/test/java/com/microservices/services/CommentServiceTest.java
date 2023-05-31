package com.microservices.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.microservices.exceptions.ResourceNotFoundException;
import com.microservices.models.Comment;
import com.microservices.repositories.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
	
	@InjectMocks
	@Autowired
	CommentService commentService;

	@Mock
	CommentRepository commentRepository;
	
	
	private Comment comment1;
	private Comment comment2;
	private List<Comment> commentList;

	
	@BeforeEach
	void setUp() throws Exception {
		comment1 = new Comment(1L, "comment1", new Timestamp(new Date().getTime()), 1L);
		comment2 = new Comment(2L, "comment2", new Timestamp(new Date().getTime()), 1L);
		commentList = new ArrayList<>();
		commentList.add(comment1);
		commentList.add(comment2);
	}

	@AfterEach
	void tearDown() throws Exception {
		comment1 = comment2 = null;
		commentList = null;
	}

	@Test
	void testGetAllCommentsOfPost() {
		commentRepository.saveAll(commentList);
		when(commentRepository.findByPostId(1L)).thenReturn(commentList);
		assertEquals(2, commentService.getAllCommentsOfPost(1L).size());
	}

	@Test
	void testSearchComments() {
		commentRepository.saveAll(commentList);
		List<Comment> cL = new ArrayList<Comment>();
		cL.add(comment1);
		when(commentRepository.searchComment("comment1")).thenReturn(cL);
		Assertions.assertEquals("comment1", commentService.searchComments("comment1").get(0).getComment());
	}

	@Test
	void testSortedAscComments() {
		commentRepository.saveAll(commentList);
		when(commentRepository.ascComment()).thenReturn(commentList);
		Assertions.assertEquals("comment1", commentService.sortedAscComments().get(0).getComment());
	}

	@Test
	void testAddComment() {
		when(commentRepository.save(any(Comment.class))).thenReturn(comment1);
		commentService.addComment(comment1);
		verify(commentRepository,times(1)).save(any(Comment.class));
	}

	@Test
	void testDeleteComment() throws Exception {
		assertThrows(ResourceNotFoundException.class, () -> commentService.deleteComment(comment1.getId()));
	}

	@Test
	void testDeleteCommentsByPostId() throws Exception {
		assertThrows(ResourceNotFoundException.class, () -> commentService.deleteCommentsByPostId(comment1.getPostId()));
	}

	@Test
	void testUpdateComment() {
		assertThrows(ResourceNotFoundException.class, () -> commentService.updateComment(comment1));
	}

}
