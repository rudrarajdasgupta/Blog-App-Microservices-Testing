package com.microservices.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.microservices.models.Comment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class CommentRepositoryTest {
	
	
	@Autowired
	private CommentRepository commentRepository;
	
	private Comment comment1;
	private Comment comment2;

	@BeforeEach
	void setUp() throws Exception {
		comment1 = new Comment(1L, "comment1", new Timestamp(new Date().getTime()), 1L);
		comment2 = new Comment(2L, "comment2", new Timestamp(new Date().getTime()), 1L);
		commentRepository.save(comment1);
		commentRepository.save(comment2);
	}

	@AfterEach
	void tearDown() throws Exception {
		commentRepository.deleteAll();
		comment1 = null;
		
	}

	@Test
	void testFindByPostId() {
		List<Comment> commentList = commentRepository.findByPostId(1L);
		assertEquals(1L, commentList.get(0).getPostId());
		
	}

	@Test
	void testDeleteByPostId() {
		commentRepository.deleteByPostId(1L);
		List<Comment> commentList = commentRepository.findByPostId(1L);
		assertEquals(0 , commentList.size());
	}

	@Test
	void testSearchComment() {
		List<Comment> commentList = commentRepository.searchComment("comment1");
		assertEquals("comment1", commentList.get(0).getComment());
	}

	@Test
	void testAscComment() {
		List<Comment> commentList = commentRepository.ascComment();
		assertEquals("comment1", commentList.get(0).getComment());
	}

}
