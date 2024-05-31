/*
package com.product_order.model;

import java.util.List;
//import java.lang.Optional; // About NULL, How to ?

// DAO: Data Access Object

public interface ProductOrderDAO {
	void add(ProductOrder prodOrd);
	void update(ProductOrder prodOrd);
	void delete(int prodOrdId);
	ProductOrder findByPK(int prodOrdId);
	List<ProductOrder> getAll();
}
*/

// --- Above: 0201 Interface --- | --- Below: Hibernate DAO Implement ---

package com.product_order.model;

//import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.product_detail.model.ProductDetailVO;

import coi618_util.Coi618Util;

public class ProductOrderDAO implements ProductOrderDAO_interface {
	
	// SessionFactory 為 thread-safe，宣告為屬性讓請求執行緒們共用
	private SessionFactory factory;
	
	public ProductOrderDAO() { // 在呼叫 DAO 同時建立工廠
		factory = Coi618Util.getSessionFactory(); 
	}
	
	// Q: 這跟直接拿來用有什麼不同? > 直接用也可以
	// Session 為 not thread-safe，所以此方法在各個增刪改查方法裡呼叫
	// 以避免請求執行緒共用了同個 Session
//	private Session getSession() {
//		return factory.getCurrentSession();
//	}

	@Override
	public Integer insert(ProductOrderVO prodOrdVO) {
		// Get session from single factory 
		Session session = factory.getCurrentSession();
		Integer prodOrdId = null;
		try {
			session.beginTransaction();
			// insert success, get prodOrdId and return
			prodOrdId = (Integer) session.save(prodOrdVO); 
			
			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Exception Position: OrderDAO/insert()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return prodOrdId; // null if failure
	}
	
	// 小吳老師的 hibernate-web/empDAOImpl, 我沒寫濾器，不敢用
//	@Override
//	public int insert(Emp entity) {
//		// 回傳給 service 剛新增成功的自增主鍵值
//		return (Integer) getSession().save(entity);
//	}

	@Override
	public Integer update(ProductOrderVO prodOrdVO) {
		Session session = factory.getCurrentSession();
		Integer success = null; // return Success ? 1 : null
		try {
			session.beginTransaction();
			
			session.update(prodOrdVO);
			
			session.getTransaction().commit();
			success = 1; // assign 1 if success
		} catch (Exception e) {
			System.err.println("Exception Position: OrderDAO/update()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return success;
	}

	@Override
	public Integer delete(Integer prodOrdId) {
		Session session = factory.getCurrentSession();
		Integer success = null; // return Success ? 1 : null
		try {
			session.beginTransaction();
			// 1. Find obj by prodOrdId
			ProductOrderVO prodOrdVO = session.get(ProductOrderVO.class, prodOrdId);
			if(prodOrdVO != null) { // 2. Check if obj exist
				session.delete(prodOrdVO); // 3. if step 2 found, delete object
			}
			session.getTransaction().commit();
			success = 1; // assign 1 if success
		} catch (Exception e) {
			System.err.println("Exception Position: OrderDAO/delete()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return success;
	}

	@Override
	public ProductOrderVO findByPK(Integer prodOrdId) {
		Session session = factory.getCurrentSession();
		ProductOrderVO prodOrdVO = null;
		try {
			session.beginTransaction();
			// get obj by prodOrdId
			prodOrdVO = session.get(ProductOrderVO.class, prodOrdId);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			System.err.println("Exception Position: OrderDAO/findByPK()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return prodOrdVO;
	}

	@Override
	public List<ProductOrderVO> getAll() {
		Session session = factory.getCurrentSession();
		List<ProductOrderVO> list = null;
		try {
			session.beginTransaction();
			// HQL = SELECT * "FROM [class:ProductOrderVO]"
			// with order =   "FROM ProductOrderVO ORDER BY prodOrdId (DESC)"
			list = session.createQuery("FROM ProductOrderVO", ProductOrderVO.class).list();
			
			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Exception Position: OrderDAO/getAll()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public List<ProductDetailVO> getDetailsByOrderId(Integer prodOrdId) {
		Session session = factory.getCurrentSession();
		List<ProductDetailVO> list = null;
		try {
			session.beginTransaction();
			// 1. Get ProductOrderVO obj from prodOrdId
			ProductOrderVO prodOrdVO = session.get(ProductOrderVO.class, prodOrdId);
			// 2. use obj get list of details
			if (prodOrdVO != null) {
				list = prodOrdVO.getProdDetails();
			}
			// To avoid Lazy init, use below for() / Replace by fetch, ref p.116
			for(ProductDetailVO product_order : list) {	
			}
			session.getTransaction().commit();
			
		} catch (Exception e) {
			System.err.println("Exception Position: OrderDAO/getDetailsByOrderId()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public List<ProductOrderVO> CompositeQuery(Map<String, String> map) {
		// TODO Auto-generated method stub
		if (map.size() == 0) return getAll(); // getAll if no condition in
		TypedQuery<ProductOrderVO> query = null; // 回傳的參數 -> 有 session 關閉點的問題，改由下方 list 回傳
		List<ProductOrderVO> list = null; // 回傳的參數
		
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			//	ref p.98 基本使用方式
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ProductOrderVO> criteria = builder.createQuery(ProductOrderVO.class);
			Root<ProductOrderVO> root = criteria.from(ProductOrderVO.class);
			
			List<Predicate> predicates = new ArrayList<>();
			// 	複合查詢方式
			//	prodOrdId(NP) | memberId(1) | estTime(~) | ordStatus(1)  
			//	total(~) | recipient(Like) | recAddr(NP) | prodDetails(NP)
			
			// 1. With both start & end of estTime
			if (map.containsKey("startEstTime") && map.containsKey("endEstTime")) {
				// 取出 開始/結束 時間
				Timestamp start = Timestamp.valueOf(map.get("startEstTime"));
				Timestamp end   = Timestamp.valueOf(map.get("endEstTime"));
				// Path 是什麼? 對應路徑? -> BGM: https://www.youtube.com/watch?v=nk8COybCLuU
				Path<Timestamp> estTimePath = root.get("estTime");
				// 建立 Predicate 條件? The between method checks if the estTime is between the start and end dates.
			    Predicate betweenPredicate = builder.between(estTimePath, start, end);
			    // 加入條件			
			    predicates.add(betweenPredicate);
			} // END of 1. With both start & end of estTime
//				predicates.add(builder.between(root.get("estTime")), java.sql.Date.valueOf(map.get("startEstTime")), java.sql.Date.valueOf(map.get("endEstTime")));
			
			// 2. With both low & high of total 寫網頁時需回來參考 parameters
			if (map.containsKey("totalLow") && map.containsKey("totalHigh")) {
				//	predicates.add(builder.between(root.get("total"), Integer.parseInt(map.get("totalLow")), Integer.parseInt(map.get("totalHigh"))));
				// Get total's range
				Integer low = Integer.parseInt(map.get("totalLow"));
				Integer high = Integer.parseInt(map.get("totalHigh"));
				// Path
				Path<Integer> totalPath = root.get("total");
				// 建立 Predicate 條件
				Predicate betweenTotal = builder.between(totalPath, low, high);
				// 加入條件
				predicates.add(betweenTotal);
			} // END of 2. With both start & end of total 寫網頁時需回來參考 parameters
			
			for (Map.Entry<String, String> row : map.entrySet() ) {
//				// 3. Select ONE memberId (問題: 如何判斷要用什麼型別?)
//				// 用 memberId 查詢的方法
//				if ("memberId".equals(row.getKey())) {
//					//	predicates.add(builder.equal(root.get("memberId"), Integer.parseInt(row.getValue())));
//					// 拿會員id(Integer)
//					Integer memberIdInt = Integer.parseInt(row.getValue());
//					// Path 設定
//					Path<Integer> memberIdPath = root.get("memberId");
//					// 建立 Predicate 條件
//					Predicate matchMemberId = builder.equal(memberIdPath, memberIdInt);
//					// 加入條件
//					predicates.add(matchMemberId);
//				} // END of 3. Select ONE memberId
				
				// --- 改成使用會員姓名 需與 Tony 討論 ---
				// 3. Like query of memberName
				if ("memberName".equals(row.getKey())) {
					// 拿會員姓名(String)
					String search = "%" + row.getValue() + "%";
					// Path 設定					// 類別.get(variable)
					Path<String> searchPath = root.get("member").get("memberName");
					// 建立 Predicate 條件
					Predicate memberNameSearch = builder.like(searchPath, search);
					// 加入條件
					predicates.add(memberNameSearch);
				} // END of 3. Select ONE memberId
				// --- 改成使用會員姓名 ---
				
				// 4. Select ONE ordStatus
				if ("ordStatus".equals(row.getKey())) {
					//	predicates.add(builder.equal(root.get("ordStatus"), Integer.parseInt(row.getValue())));
					// 拿 ordStatus(Integer)
					Integer ordStatusInt = Integer.parseInt(row.getValue());
					// Path setup
					Path<Integer> ordStatusPath = root.get("ordStatus");
					// Predicate condition setup
					Predicate matchOrdStatus = builder.equal(ordStatusPath, ordStatusInt);
					// Add predicate condition
					predicates.add(matchOrdStatus);
				} // END of 4. Select ONE ordStatus
				
				// 5. Like query of recipient > How to change to member name?
				if ("recipient".equals(row.getKey())) {
					//	predicates.add(builder.like(root.get("recipient"), "%" + row.getValue()+ "%"));
					// Get like String
					String search = "%" + row.getValue() + "%";
					// Path setup
					Path<String> recipientPath = root.get("recipient");
					// Predicate condition setup
					Predicate likeRecipient = builder.like(recipientPath, search);
					// Add condition
					predicates.add(likeRecipient);
				} // END of 5. Like query of recipient
				
				// 6. Only start of estTime
				if ("startEstTime".equals(row.getKey()) && !map.containsKey("endEstTime")) {
					//	predicates.add(builder.greaterThanOrEqualTo(root.get("estTime"), Date.valueOf(row.getValue())));
					// Get estTime of start
					Timestamp start = Timestamp.valueOf(row.getValue());
					// Path setup 
					Path<Timestamp> estTimePath = root.get("estTime");
					// Predicate condition setup
					Predicate gtStart = builder.greaterThanOrEqualTo(estTimePath, start);
					// Add Predicate
					predicates.add(gtStart);
				} // END of 6. Only start of estTime

				// 7. Only end of estTime
				if ("endEstTime".equals(row.getKey()) && !map.containsKey("startEstTime")) {
//					predicates.add(builder.lessThanOrEqualTo(root.get("estTime"), Date.valueOf(row.getValue())));
					// Get estTime of end
					Timestamp end = Timestamp.valueOf(row.getValue());
					// Path setup
					Path<Timestamp> estTimePath = root.get("estTime");
					// Predicate condition setup
					Predicate ltEnd = builder.lessThanOrEqualTo(estTimePath, end);
					// Add Predicate
					predicates.add(ltEnd);
				} // END of 7. Only end of estTime
				
				// 8. Only low of total
				if ("totalLow".equals(row.getKey()) && !map.containsKey("totalHigh")) {
					//	predicates.add(builder.greaterThanOrEqualTo(root.get("total"), Integer.parseInt(row.getValue())));
					// Get total range (Integer)
					Integer totalLowInt = Integer.parseInt(row.getValue());
					// Path setup
					Path<Integer> totalPath = root.get("total");
					// Predicate condition setup (What's different?)
					Predicate gtLow = builder.ge(totalPath, totalLowInt);
					//		  gtLow = builder.greaterThanOrEqualTo(totalPath, totalLowInt);
					// 			From Chatgpt:
					//				builder.ge -> for numeric comparisons.
					//				builder.greaterThanOrEqualTo -> comparisons involving any type that implements Comparable.
					// Add Predicate
					predicates.add(gtLow);
				} // END of 8. Only start of total
				
				// 9. Only high of total
				if ("totalHigh".equals(row.getKey()) && !map.containsKey("totalLow")) {
					//	predicates.add(builder.lessThanOrEqualTo(root.get("total"), Integer.parseInt(row.getValue())));
					// Get total range (Integer)
					Integer totalHighInt = Integer.parseInt(row.getValue());
					// Path setup
					Path<Integer> totalPath = root.get("total");
					// Predicate condition setup
					Predicate ltHigh = builder.lessThanOrEqualTo(totalPath, totalHighInt);
					// Add Predicate
					predicates.add(ltHigh);
				} // END of 9. Only end of total
				
			} // END of for (Map.Entry<String, String> row : map.entrySet() ) 

			criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
			criteria.orderBy(builder.asc(root.get("prodOrdId"))); // 先用vo試試看
			query = session.createQuery(criteria); // query 還是 session 裡的東西，無法回傳
			list = query.getResultList(); // 用 list 轉接
			
			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Exception Position: OrderDAO/CompositeQuery()");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		// Exception in thread "main" java.lang.IllegalStateException: Session/EntityManager is closed
//		return (query != null ? query.getResultList() : null);
		return list;
	}	
}
