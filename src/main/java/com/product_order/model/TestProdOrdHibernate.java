package com.product_order.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.member.model.Member;
import com.member.model.MemberVO; // fix - 01

public class TestProdOrdHibernate {

	public static void main(String[] args) {
		ProductOrderDAO_interface dao = new ProductOrderDAO();
//		Member member = new Member();
		MemberVO member = new MemberVO(); // fix - 02, 03
		member.setMemberId(1);
		
		// 1. insert 2 new orders
		var order = new ProductOrderVO(
			null, 			// Integer prodOrdId 
//			1,				// Integer memberId
			member,			// Member member (Only set memberId = 1)
			
			new Timestamp(System.currentTimeMillis()), 	// Timestamp estTime 
			0, 				// Integer ordStatus
			1000,			// Integer total 
			"TestMan1", 	// String recipient 
			"Test addr 1",	// String recAddr 
			null			// List<ProductDetailVO> prodDetails
		);
		Integer newOrdId = dao.insert(order);
		
		order.setRecipient("測試人員一");
		order.setRecAddr("測試地址一");
		Integer newOrdId2 = dao.insert(order);
		
		// 2. update new order2
		order.setProdOrdId(newOrdId2);
		order.setRecipient("更新人員二");
		order.setRecAddr("更新地址二");
		System.out.println(dao.update(order) == 1 ? "update success" : "FAIL of update");
		
		// 3. delete new order1
		System.out.println(dao.delete(newOrdId) == 1 ? "delete success" : "FAIL of delete");
		
		// 4. Query
		// 4-1. findByPK(11000001) & details
		Integer pk = 11000001;
		System.out.println("findByPK(" + pk + "):\n");
		printHead();
		System.out.println(dao.findByPK(pk));
		
		// 4-2. findAll
		listAllOrder(dao);
		
		// 4-3. CompositeQuery 
		// Prepare map
		System.out.println("CompositeQuery:");
		Map<String, String> map = new HashMap<>();
		// Add data to the map
		
		// 使用 memberId (Integer: SELECT) 的版本
////		TEST-1: Should be 6
////		1. estTime: 3/27 11:00 ~ 5/1 00:00
//		map.put("startEstTime", "2024-03-27 11:00:00");
//		map.put("endEstTime",   "2024-05-01 00:00:00");
////		2. total: 500~3000
//		map.put("totalLow",  "500");
//		map.put("totalHigh", "3000");
////		3. memberId: 3
//		map.put("memberId", "3");
////		4. ordStatus: 1
//		map.put("ordStatus", "1");
//		System.out.println("TEST-1: Should be 6");
//		printMap(map, dao);
//		map.clear();

		// 修改成 memberName(String: like) 的版本
//		TEST-1: Should be 5
//		1. estTime: 3/27 11:00 ~ 5/1 00:00
		map.put("startEstTime", "2024-03-27 11:00:00");
		map.put("endEstTime",   "2024-05-01 00:00:00");
//		2. total: 500~3000
		map.put("totalLow",  "500");
		map.put("totalHigh", "3000");
//		3. memName: 3
		map.put("memberName", "3");
//		4. ordStatus: 0
		map.put("ordStatus", "0");
		System.out.println("TEST-1: Should be 6");
		printMap(map, dao);
		map.clear();

//		TEST-2: Should be 6, 7, 8
//		6. estTime: 3/28 00:00 ~
		map.put("startEstTime", "2024-03-28 00:00:00");
//		8. total: 2000~
		map.put("totalLow",  "2000");
		System.out.println("TEST-2: Should be 4");
		printMap(map, dao);
		map.clear();
		
//		TEST-3: Should be 2, 3
//		7. estTime: ~ 4/1 00:00
		map.put("endEstTime", "2024-04-01 00:00:00");
//		9. total: ~1200
		map.put("totalHigh", "1200");
		System.out.println("TEST-3: Should be 2, 3");
		printMap(map, dao);
		map.clear();
		
//		TEST-4: Should be 1, 2
//		5. recipient: "小"
		map.put("recipient", "小");
		System.out.println("TEST-4: Should be 1, 2");
		printMap(map, dao);
		map.clear();

		System.out.println("Finish TestProdOrdHibernate.");
	}
	
	private static void printMap(Map<String, String> map, ProductOrderDAO_interface dao) {
		// This is List what(k,v) inside the map
		map.forEach((key, value) -> {
			System.out.println(key + " : " + value);
		});
		
		// Put map to dao for CompositeQuery, return with a list
		List<ProductOrderVO> ComOrdList = dao.CompositeQuery(map);
        printHead();
        for (ProductOrderVO o : ComOrdList) {
        	System.out.println(o);
        }
        System.out.println();
	}

	private static void printHead() {
		System.out.println(
			"ProductOrder:\nprodOrdId | memName |            estTime | ordStatus | total | recipient | recAddr"
		);
		for (int i = 0; i < 99; i++)
			System.out.print("-");
		System.out.println();
	}
	
	private static void listAllOrder(ProductOrderDAO_interface dao) {
		printHead();
		List<ProductOrderVO> prodOrdList = dao.getAll();
		for (ProductOrderVO prodOrdVO : prodOrdList) {
			System.out.println(prodOrdVO);
		}
		System.out.println();
	}

}

// --- Logs ----------------
// Caused by: org.hibernate.AnnotationException: @OneToOne or @ManyToOne on com.product_order.model.ProductOrderVO.member references an unknown entity: com.member.model.Member
// -> member VO 未 mapping 在 hibernate.cfg.xml 中

