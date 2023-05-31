package com.microservices.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class User {

	private Long id;
	private String emailId;
	private String name;
	private String password;

}
