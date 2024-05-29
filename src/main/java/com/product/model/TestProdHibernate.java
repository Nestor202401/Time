package com.product.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.product_img.model.ProductImgVO;

public class TestProdHibernate {
	public static void main(String[] args) {
		
		// 0. Declare a DAO obj
		ProductDAO_interface prodDao = new ProductDAO();
		
		// 1. Insert 2 products
		ProductVO p1 = new ProductVO(
			null, 					// Integer prodId (PK)
			"測試商品1", 				// String prodName
			"測試商品介紹1", 			// String prodIntro
			100, 					// Integer prodPrice
			Date.valueOf(LocalDate.now()),	//  Date releaseDate
			null, 					// Date removeDate
			0,						// Integer salesStatus 銷售狀態, 0:銷售中, 1:停售
			false, 					// Boolean timeLimitProd
			null, null);			// List<ProductDetailVO> prodDetails / List<ProductImgVO> prodImgs
		
		ProductVO p2 = new ProductVO(
			null, 					// Integer prodId (PK)
			"測試商品2", 				// String prodName
			"測試商品介紹2", 			// String prodIntro
			200, 					// Integer prodPrice
			Date.valueOf(LocalDate.now()),	//  Date releaseDate
			null, 					// Date removeDate
			1,						// Integer salesStatus 銷售狀態, 0:銷售中, 1:停售
			true, 					// Boolean timeLimitProd
			null, null);			// List<ProductDetailVO> prodDetails / List<ProductImgVO> prodImgs
			
		Integer p1Id = prodDao.insert(p1);
		Integer p2Id = prodDao.insert(p2);
		
		// 2. Delete 1st insert product
		System.out.println(prodDao.delete(p1Id) == 1 ? "Delete success" : "Fail of delete");
		
		// 3. Update 2nd insert product
		p2.setProdId(p2Id); // Set Id for match update condition
		p2.setProdName("測試商品2改");
		p2.setProdIntro("測試商品介紹2改");
		
		System.out.println(prodDao.update(p2) == 1 ? "Update success" : "Fail of update");
		
		// Query
		// 4. Find by PK 13000001
		Integer pk = p2Id;
		printHead();
		System.out.println(prodDao.findByPK(pk));
		
		// 5. GetAll
		listAllProd(prodDao);
		
		// 6. CompositeQuery
		// Prepare map
		System.out.println("CompositeQuery:");
		Map<String, String> map = new HashMap<>();
		// Add data to the map
		
//		TEST-1
//		1. prodPrice: 200~1000
		map.put("prodPriceLow",  "200");
		map.put("prodPriceHigh", "1000");
//		2. releaseDate: 24/1/1~24/5/1
		map.put("startReleaseDate", "2024-01-01");
		map.put("endReleaseDate",   "2024-05-01");
//		4. salesStatus: 0
		map.put("salesStatus", "0");
//		5. timeLimitProd: false
		map.put("timeLimitProd", "false"); // Anything else of [TRUE, true] are false
//		Should be: 2, 6
		System.out.println("TEST-1: Should be: 2, 6");
		printMap(map, prodDao);
		map.clear();
		
//		TEST-2
//		6. prodPrice: 1000~
		map.put("prodPriceLow", "1000");
//		8. releaseDate: 2024/1/1~
		map.put("startReleaseDate", "2024-01-01");
//		Should be: 2, 4, 7, 10
		System.out.println("TEST-2: Should be: 2, 4, 7, 10");
		printMap(map, prodDao);
		map.clear();
		
//		Test-3
//		7. prodPrice: ~900
		map.put("prodPriceHigh", "900");
//		9. releaseDate: ~23/1/1
		map.put("endReleaseDate", "2023-01-01");
//		Should be 3
		System.out.println("TEST-3: Should be 3");
		printMap(map, prodDao);
		map.clear();
		
//		Test-4
//		3. prodName: "玩偶"
		map.put("prodName", "玩偶");
//		Should be 4, 10 
		System.out.println("TEST-4: Should be 4, 10");
		printMap(map, prodDao);
		map.clear();
		
		// 7. getImgsByProdId()
		List<ProductImgVO> list = prodDao.getImgsByProdId(13000001);
		System.out.println("getImgsByProdId(13000001):");
		for (ProductImgVO img : list) {
			System.out.println(img);
		}
		System.out.println();
	}
	private static void printMap(Map<String, String> map, ProductDAO_interface prodDao) {
		// This is List what(k,v) inside the map
		map.forEach((key, value) -> {
			System.out.println(key + " : " + value);
		});
		
		// Put map to dao for CompositeQuery, return with a list
		List<ProductVO> ComProdList = prodDao.CompositeQuery(map);
		printHead();
		for (ProductVO p : ComProdList) {
			System.out.println(p);
		}
		System.out.println();
	}
	
	private static void printHead() {
		System.out.println(
			"Product:\n"
			+ "   prodId | prodPrice | releaseDate | removeDate | salesStatus | timeLimitProd | prodName | prodIntro"
		);
		for (int i = 0; i < 99; i++)
			System.out.print("-");
		System.out.println();
	}
	
	private static void listAllProd(ProductDAO_interface dao) {
		printHead();
		List<ProductVO> prodList = dao.getAll();
		for (ProductVO prodVO : prodList) {
			System.out.println(prodVO);
		}
		System.out.println();
	}

}