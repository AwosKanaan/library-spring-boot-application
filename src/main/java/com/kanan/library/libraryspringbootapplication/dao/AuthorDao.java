package com.kanan.library.libraryspringbootapplication.dao;

import com.kanan.library.libraryspringbootapplication.entity.Author;

import java.util.List;
import java.util.Map;

public interface AuthorDao {
	 void saveAuthor(Author author);

	 List<Author> findAll();

	 Author findAuthorById(String authorId);

	 void updateAuthorById(Map<String, Object> updatedAuthor);

	 void deleteAuthorById(String id);

	 boolean isAuthorNameTaken(String authorName);
}
