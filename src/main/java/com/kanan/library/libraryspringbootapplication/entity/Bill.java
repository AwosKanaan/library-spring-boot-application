package com.kanan.library.libraryspringbootapplication.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document
public class Bill {

	@Id
	private String id;

	private String personId;

	private LocalDateTime localDateTime;

	private List<String> bookIds = new ArrayList<>();

	private double totalAmount;
}
