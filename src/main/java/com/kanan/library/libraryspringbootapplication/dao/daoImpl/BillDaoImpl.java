package com.kanan.library.libraryspringbootapplication.dao.daoImpl;

import com.kanan.library.libraryspringbootapplication.dao.BillDao;
import com.kanan.library.libraryspringbootapplication.entity.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BillDaoImpl implements BillDao {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public BillDaoImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void saveBill(Bill bill) {
		mongoTemplate.save(bill);
	}

	@Override
	public void findAll() {

	}

	@Override
	public void findBillById(String billId) {

	}

	@Override
	public void updateBill(Bill bill) {

	}

	@Override
	public void deleteBillById(String billId) {

	}
}
