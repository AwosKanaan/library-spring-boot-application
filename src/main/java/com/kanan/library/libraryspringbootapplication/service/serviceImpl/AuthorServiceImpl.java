package com.kanan.library.libraryspringbootapplication.service.serviceImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanan.library.libraryspringbootapplication.dao.AuthorCountDao;
import com.kanan.library.libraryspringbootapplication.dao.AuthorDao;
import com.kanan.library.libraryspringbootapplication.entity.Author;
import com.kanan.library.libraryspringbootapplication.exception.AuthorCollectionException;
import com.kanan.library.libraryspringbootapplication.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.kanan.library.libraryspringbootapplication.validation.Validate.validateAuthor;

@Service
public class AuthorServiceImpl implements AuthorService {

	private final AuthorDao authorDao;
	private final AuthorCountDao authorCountDao;

	private final ObjectMapper objectMapper;

	@Autowired
	public AuthorServiceImpl(AuthorDao authorDao, AuthorCountDao authorCountDao, ObjectMapper objectMapper) {
		this.authorDao = authorDao;
		this.authorCountDao = authorCountDao;
		this.objectMapper = objectMapper;
	}

	@Override
	public void saveAuthor(Author author) throws ConstraintViolationException, AuthorCollectionException {
		validateAuthor(author);

		if (authorDao.isAuthorNameTaken(author.getAuthorName())) {
			throw new AuthorCollectionException(AuthorCollectionException.nameTakenException());
		}


		author.setId(null);
		author.setUpdatedAt(null);
		author.setAuthorId(authorCountDao.incrementAuthorsCount("655dc4e2ad9bf6d94e7215ae"));
		author.setCreatedAt(LocalDateTime.now());

		authorDao.saveAuthor(author);
	}

	@Override
	public List<Author> findAll() {
		List<Author> authors = authorDao.findAll();

		if (!authors.isEmpty()) {
			return authors;
		}
		return new ArrayList<>();
	}

	@Override
	public Author findAuthorById(String authorId) throws AuthorCollectionException {
		Author author = authorDao.findAuthorById(authorId);
		if (author == null) {
			throw new AuthorCollectionException(AuthorCollectionException.notFoundException(authorId));
		}
		return author;
	}

	public void updateAuthorById(Author author) throws AuthorCollectionException {
		if (author.getAuthorId() == null) {
			throw new AuthorCollectionException("Author id cannot be null");
		}
		author.setUpdatedAt(LocalDateTime.now());
		Map<String, Object> authorMap = objectMapper.convertValue(author, new TypeReference<>() {});
		authorDao.updateAuthorById(authorMap);
	}

	@Override
	public void deleteAuthorById(String authorId) throws AuthorCollectionException {
		Author author = authorDao.findAuthorById(authorId);
		if (author == null) {
			throw new AuthorCollectionException(AuthorCollectionException.notFoundException(authorId));
		}
		authorDao.deleteAuthorById(authorId);
	}
}
