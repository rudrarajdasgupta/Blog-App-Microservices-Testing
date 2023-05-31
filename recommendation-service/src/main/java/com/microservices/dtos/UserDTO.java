package com.microservices.dtos;

import java.sql.Timestamp;
import java.util.List;

import com.microservices.models.Comment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
	private Long id;
	private String emailId;
	private String name;
	private List<PostDTO> posts;

}
