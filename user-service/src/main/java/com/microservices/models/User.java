package com.microservices.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String emailId;
	private String name;
	private String password;

	public User(String emailId, String name, String password) {
		this.emailId = emailId;
		this.name = name;
		this.password = password;
	}
}
