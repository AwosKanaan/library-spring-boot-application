package com.kanan.library.libraryspringbootapplication.service.serviceImpl;

import com.kanan.library.libraryspringbootapplication.dao.BillDao;
import com.kanan.library.libraryspringbootapplication.entity.Bill;
import com.kanan.library.libraryspringbootapplication.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BillServiceImpl implements BillService {

	private final BillDao billDao;

	@Autowired
	public BillServiceImpl(BillDao billDao) {
		this.billDao = billDao;
	}
	@Override
	public void saveBill(Bill bill) {
		billDao.saveBill(bill);
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
