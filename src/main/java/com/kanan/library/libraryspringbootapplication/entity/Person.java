package com.kanan.library.libraryspringbootapplication.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document
public class Person {
	@Id
	private String id;

	private String personId;

	private String name;

	private List<String> bookIds = new ArrayList<>();

	public void addToShoppingCart(String bookId) {
		bookIds.add(bookId);
	}

	public void removeFromShoppingCart(String bookId) {
		bookIds.remove(bookId);
	}

	public void clearShoppingList() {
		bookIds.clear();
	}

}
