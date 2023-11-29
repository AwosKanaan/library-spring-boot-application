package com.kanan.library.libraryspringbootapplication.service;

import com.kanan.library.libraryspringbootapplication.entity.Author;
import com.kanan.library.libraryspringbootapplication.exception.AuthorCollectionException;

import java.util.List;

public interface AuthorService {

	void saveAuthor(Author author) throws AuthorCollectionException;

	 List<Author> findAll();

	 Author findAuthorById(String authorId) throws AuthorCollectionException;

	void updateAuthorById(Author author) throws AuthorCollectionException;

	void deleteAuthorById(String authorId) throws AuthorCollectionException;
}
