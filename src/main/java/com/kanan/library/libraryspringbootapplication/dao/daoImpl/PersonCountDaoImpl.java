package com.kanan.library.libraryspringbootapplication.dao.daoImpl;

import com.kanan.library.libraryspringbootapplication.dao.PersonCountDao;
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
public class PersonCountDaoImpl implements PersonCountDao {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public PersonCountDaoImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public String incrementPersonsCount(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		Update update = new Update().inc("count", 1);
		mongoTemplate.updateFirst(query, update, Count.class);
		return Objects.requireNonNull(mongoTemplate.findById(new ObjectId("65662522fa0099dfd8f31e52"), Count.class)).getCount();
	}
}
