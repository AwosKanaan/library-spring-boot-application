package com.kanan.library.libraryspringbootapplication.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@NoArgsConstructor
public class Author {

	@Id
	private String id;

	private String authorId;

	private String authorName;

	private String description;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}

