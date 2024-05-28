/*
package com.product_order.model;

import java.sql.Timestamp;
import java.util.List;
import com.product_detail.model.*;

public class ProductOrderService {
	
	private ProductOrderDAO ordDao;
	
	// <Constructor>: Call an implement obj when new
	public ProductOrderService() {
		ordDao = new ProductOrderDAOImpl();
	}
	
	// 增
	// addOrder overload(多載)
	public ProductOrder addOrder(ProductOrder prodOrd) {
		ordDao.add(prodOrd);
		return prodOrd;
	}
	public ProductOrder addOrder(Integer memId, Timestamp estTime,
			Integer ordStatus, Integer total, String recipient, String recAdd) {
		var prodOrd = new ProductOrder(
			null, memId, estTime, ordStatus, total, recipient, recAdd);
		
		ordDao.add(prodOrd);
		return prodOrd;
	}
	
	// 改
	// updateOrder overload(多載)
	public ProductOrder updateOrder(ProductOrder prodOrd) {
		ordDao.update(prodOrd);
		return prodOrd;
	}
	public ProductOrder updateOrder(Integer prodOrdId, Integer memId, Timestamp estTime,
			Integer ordStatus, Integer total, String recipient, String recAdd) {
		var prodOrd = new ProductOrder(prodOrdId, memId, estTime,
			ordStatus, total, recipient, recAdd); 
		
		ordDao.update(prodOrd);
		return prodOrd;
	}
	
	// 刪
	public void deleteOrder(Integer prodOrdId) {
		ordDao.delete(prodOrdId);
	}
	
	// 查
	public ProductOrder getOneOrder(Integer prodOrdId) {
		return ordDao.findByPK(prodOrdId);
	}
	// 查(All)
	public List<ProductOrder> getAll() {
		return ordDao.getAll();
	}
}
*/
//--- Above: 0201 Service --- | --- Below: Hibernate Service ---
package com.product_order.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.member.model.Member;
//import com.member.model.MemberVO; // fix - 01
//import com.product_detail.model.*;
import com.product_detail.model.ProductDetailVO;

public class ProductOrderService {
	// Should declare by an Interface, for switch different dao implements
	private ProductOrderDAO_interface ordDao;
	
	// <Constructor>: Call an implement DAO when new
	public ProductOrderService() {
		// new different implement DAO: JDBC/JNDI/Hibernate etc
		ordDao = new ProductOrderDAO();
	}
	
	// 增
	// addOrder overload(多載)
	public ProductOrderVO addOrder(ProductOrderVO prodOrdVO) {
		ordDao.insert(prodOrdVO);
		return prodOrdVO; // 為什麼要回傳 obj? 寫 controller時確認
	}
//	public ProductOrderVO addOrder(MemberVO member, Timestamp estTime, // fix - 02
	public ProductOrderVO addOrder(Member member, Timestamp estTime,
			Integer ordStatus, Integer total, String recipient, 
			String recAdd, List<ProductDetailVO> prodDetails) {
		var prodOrdVO = new ProductOrderVO(
			null, member, estTime, ordStatus, total, recipient, recAdd, prodDetails);
		// 可能的錯誤: 2. Member(一方宣告)、-1. prodDetails(多方屬性)
		
		ordDao.insert(prodOrdVO);
		return prodOrdVO; 
	}
	
	// 改
	// updateOrder overload(多載)
	public ProductOrderVO updateOrder(ProductOrderVO prodOrdVO) {
		ordDao.update(prodOrdVO);
		return prodOrdVO; // 同上: 為什麼要回傳 obj? 寫 controller時確認
	}
	public ProductOrderVO updateOrder(Integer prodOrdId, Member member, Timestamp estTime,
//	public ProductOrderVO updateOrder(Integer prodOrdId, MemberVO member, Timestamp estTime, // fix - 03
			Integer ordStatus, Integer total, String recipient, String recAdd, List<ProductDetailVO> prodDetails) {
		var prodOrdVO = new ProductOrderVO(prodOrdId, member, estTime,
			ordStatus, total, recipient, recAdd, prodDetails); 
		
		ordDao.update(prodOrdVO);
		return prodOrdVO;
	}
	
	// 刪
	public void deleteOrder(Integer prodOrdId) {
		ordDao.delete(prodOrdId);
	}
	
	// 查
	public ProductOrderVO getOneOrder(Integer prodOrdId) {
		return ordDao.findByPK(prodOrdId);
	}
	// 查(All)
	public List<ProductOrderVO> getAll() {
		return ordDao.getAll();
	}
	// 複合查詢 -> 未測試
	// 參考小吳老師 Web/EmpServiceImpl.java，如果有需要，將 Service 介面與實作分離
	// 這裡傳進的參數 map 由 Servlet/req.getParameterMap 導入
	List<ProductOrderVO> CompositeQuery(Map<String, String[]> map) {
		Map<String, String> query = new HashMap<>(); // 準備丟給 DAO
		
		// Map.Entry 即代表一組 key-value
		Set<Map.Entry<String, String[]>> entry = map.entrySet();
		
		for (Map.Entry<String, String[]> row : entry) {
			String key = row.getKey();
			
			// 因為請求參數裡包含了 action，先去除
			if ("action".equals(key)) continue;
			
			// 清洗資料
			String value = row.getValue()[0]; // getValue() 拿到一個 String[], 取[0]做元素檢查
			if (value == null || value.isEmpty()) continue; // 若是 value 為空即代表沒有輸入查詢條件，去除
			
			query.put(key, value);
		}
		// DEBUG
		System.out.println("Query send to ordDao is:");
		System.out.println(query);
		
		return ordDao.CompositeQuery(query);
	}
	// 訂單Id 查所有明細
	List<ProductDetailVO> getDetailsByOrderId(Integer prodOrdId) {
		return ordDao.getDetailsByOrderId(prodOrdId);
	}
	
}