package com.kanan.library.libraryspringbootapplication.dao;

import com.kanan.library.libraryspringbootapplication.entity.Bill;

public interface BillDao {
	void saveBill(Bill bill);

	void findAll();

	void findBillById(String billId);

	void updateBill(Bill bill);

	void deleteBillById(String billId);
}
