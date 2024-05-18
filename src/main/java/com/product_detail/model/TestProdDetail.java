package com.product_detail.model;

import java.util.List;

public class TestProdDetail {
	// 連線池不能跑 main 方法，別再玩了!!!
	public static void main(String[] args) {
		ProductDetailDAO dao = new ProductDetailDAOImpl();
		ProductDetail prodDetail = null;
		
		prodDetail = new ProductDetail(null, 11000006, 13000009, 1800, 1, 1800);
		
		// Add test
		dao.add(prodDetail); // Add 1st detail, should get p_detail_id: 12000012
		prodDetail = new ProductDetail(null, 11000006, 13000010, 1000, 1, 1000);
		dao.add(prodDetail); // Add 2nd detail, should get p_detail_id: 12000013
		
//		// Update test
//		dao.update(new ProductDetail(12000012, 11000005, 13000006, 500, 3, 1500));
//		
//		// DELETE - Not use
//		dao.delete(12000013);
//		
//		// findByPK test
//		int key = 12000013; // 12000001 | 12000013
//		prodDetail = dao.findByPK(key);
//		System.out.println("findByPK(" + key + ") :"); // HERE
//		printHead();
//		System.out.println(prodDetail);
		
		// getAll test
		listAllDetail(dao);
		
		System.out.println("End of TestProd_detail.");
	}
	
	private static void printHead() {
		System.out.println(
			"Product_detail:\nprod_detail_id | prod_ord_id |  prod_id | "
			+ "unit_price | prod_count | prod_sum"
		);
		for(int i = 0; i < 76; i++)
			System.out.print("-");
		System.out.println();
	}

	private static void listAllDetail(ProductDetailDAO dao) {
		int listOfPage = 5;
		printHead();
		List<ProductDetail> pdList = dao.getAll();
		for (int i = 0; i < pdList.size(); i++) {
			System.out.print((i != 0) && (i % listOfPage == 0) ? "-- " + i + "\n": "");
			System.out.println(pdList.get(i));
		}
		System.out.println();
	}
}
