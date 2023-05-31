package com.microservices.dtos;

import java.sql.Timestamp;
import java.util.List;

import com.microservices.models.Comment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDTO {
	private Long id;
	private String title;
	private String description;
	private Long userId;
	private List<Comment> comments;

}
