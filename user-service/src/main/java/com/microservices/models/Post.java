
package com.microservices.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Post {

	private Long id;
	private String title;
	private String description;
	private Long userId;
}
