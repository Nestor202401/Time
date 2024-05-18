package com.product_order.model;

import java.sql.Timestamp;
import java.util.List;

public class TestProdOrd {

	public static void main(String[] args) {
		// New a Data Access Obj
		ProductOrderDAO dao = new ProductOrderDAOImpl();
		
		// 新增 
		ProductOrder prodOrd = new ProductOrder(
			null,			// prodOrd_id	// Check if work? 
			4,				// member_id 
			Timestamp.valueOf("2024-04-18 13:27:20"),	// est_time  // check if error? => Worked 
			0,				// ord_status > 0: yet, 1: FINISH 
			1000,			// total // fake data, not verify
			"新增測試人名",	// recipient 
			"新增測試人地址"	// rec_addr
		);
		// Use DAO call add()
		dao.add(prodOrd);	// prodOrd_id is 11000007
		
		// Modify, add as another data
		prodOrd.setRecipient("新增測試人名2");
		prodOrd.setRecAddr("新增測試人地址2");
		dao.add(prodOrd);	// prodOrd_id is 11000008
		
		// 修改: prodOrd_id => 11000008
		prodOrd.setProdOrdId(11000008);
		prodOrd.setRecipient("修改測試人名2");
		prodOrd.setRecAddr("修改測試人地址2");
		dao.update(prodOrd);
		
		// 刪除: prodOrd_id => 11000007
		dao.delete(11000007);
		
		// 查一筆: by key
		int key = 11000001;
		System.out.println("findByPK(" + key + ")" + dao.findByPK(key));
		
		// 查全部
		listAllOrder(dao);
	}
	
	private static void printHead() {
		System.out.println(
			"ProductOrder:\nprodOrdId | memberId |            estTime | ordStatus | total | recipient | recAddr"
		);
		for (int i = 0; i < 99; i++)
			System.out.print("-");
		System.out.println();
	}
	
	private static void listAllOrder(ProductOrderDAO dao) {
		printHead();
		List<ProductOrder> prodOrdList = dao.getAll();
		for (ProductOrder prodOrd : prodOrdList) {
			System.out.println(prodOrd);
		}
	}
}
