package com.kanan.library.libraryspringbootapplication.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Document
public class Book {

	@Id
	private String id;

	private String bookId;

	private String title;

	private String authorId;

	@JsonProperty("ISBN")
	private String ISBN;

	private String description;

	private int serialNumber;

	private double price;

	private String genre;

	private Boolean published;

	private int quantity;
}
