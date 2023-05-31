package com.microservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.models.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findByUserId(Long userId);

    public void deleteByUserId(Long userId);
}
