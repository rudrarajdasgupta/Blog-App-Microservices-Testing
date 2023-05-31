package com.microservices.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@NoArgsConstructor
public class Comment {

	private Long id;
	private String comment;
	private Timestamp time;
	private Long postId;
}
