package com.kanan.library.libraryspringbootapplication.dao.daoImpl;

import com.kanan.library.libraryspringbootapplication.dao.BookCountDao;
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
public class BookCountDaoImpl implements BookCountDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public String incrementBooksCount(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		Update update = new Update().inc("count", 1);
		mongoTemplate.updateFirst(query, update, Count.class);
		return Objects.requireNonNull(mongoTemplate.findById(new ObjectId("655dc557ad9bf6d94e7215b0"), Count.class)).getCount();
	}
}
