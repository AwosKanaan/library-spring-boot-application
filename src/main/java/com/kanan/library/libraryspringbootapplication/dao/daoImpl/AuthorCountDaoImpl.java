package com.kanan.library.libraryspringbootapplication.dao.daoImpl;

import com.kanan.library.libraryspringbootapplication.dao.AuthorCountDao;
import com.kanan.library.libraryspringbootapplication.entity.Author;
import com.kanan.library.libraryspringbootapplication.entity.Count;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class AuthorCountDaoImpl implements AuthorCountDao {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public AuthorCountDaoImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public String incrementAuthorsCount(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		Update update = new Update().inc("count", 1);
		mongoTemplate.updateFirst(query, update, Count.class);
		return Objects.requireNonNull(mongoTemplate.findById(new ObjectId("655dc4e2ad9bf6d94e7215ae"), Count.class)).getCount();
	}
}
