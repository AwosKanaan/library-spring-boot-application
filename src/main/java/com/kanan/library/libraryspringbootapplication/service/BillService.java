package com.kanan.library.libraryspringbootapplication.service;

import com.kanan.library.libraryspringbootapplication.entity.Bill;

public interface BillService {

	void saveBill(Bill bill);

	void findAll();

	void findBillById(String billId);

	void updateBill(Bill bill);

	void deleteBillById(String billId);
}
