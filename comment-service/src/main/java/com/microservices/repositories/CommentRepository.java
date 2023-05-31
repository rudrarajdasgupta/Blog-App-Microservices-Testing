package com.microservices.repositories;

import java.util.List;

import com.microservices.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	public List<Comment> findByPostId(Long postId);
	
	public void deleteByPostId(Long postId);

	@Query(nativeQuery = true,value = "SELECT * FROM comment c WHERE c.comment=:string")
	public List<Comment> searchComment(@Param("string") String comment);

	@Query(nativeQuery = true,value = "SELECT * FROM comment c ORDER BY comment")
	public List<Comment> ascComment();
}
