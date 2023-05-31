package com.microservices.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	private Long id;
	private String comment;
	private Timestamp time;
	private Long postId;
}
