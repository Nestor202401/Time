package com.product.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.product_detail.model.ProductDetailVO;
import com.product_img.model.ProductImgVO;
import com.product_order.model.ProductOrderVO;
import com.util.ProductUtil;

public class ProductDAO implements ProductDAO_interface {

// SessionFactory 為 thread-safe，to 宣告為屬性讓請求執行緒們共用
	private SessionFactory factory;
	
	public ProductDAO() { // 在呼叫 DAO 同時建立工廠
		factory = ProductUtil.getSessionFactory(); // here
	}
	
	// Q: 這跟直接拿來用有什麼不同? > 直接用也可以
	// Session 為 not thread-safe，所以此方法在各個增刪改查方法裡呼叫
	// 以避免請求執行緒共用了同個 Session
//		private Session getSession() {
//			return factory.getCurrentSession();
//		}
	
	@Override
	public Integer insert(ProductVO prodVO) {
		Session session = factory.getCurrentSession();
		Integer prodId = null;
		try {
			session.beginTransaction();
			// insert success, get prodId and return
			prodId = (Integer) session.save(prodVO); 
			
			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Exception Position: ProductDAO/insert()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return prodId;
	}

	@Override
	public Integer update(ProductVO prodVO) {
		Session session = factory.getCurrentSession();
		Integer success = null; // return Success ? 1 : null
		try {
			session.beginTransaction();
			
			session.update(prodVO); 
			
			session.getTransaction().commit();
			success = 1; // assign 1 if success
		} catch (Exception e) {
			System.err.println("Exception Position: ProductDAO/update()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return success;
	}

	@Override
	public Integer delete(Integer prodId) {
		Session session = factory.getCurrentSession();
		Integer success = null; // return Success ? 1 : null
		try {
			session.beginTransaction();
			// 1. Find obj by prodId
			ProductVO prod = session.get(ProductVO.class, prodId);
			if (prod != null) { // 2. Check if obj exist
				session.delete(prod); // 3. If step 2 found, delete obj
			}
			
			session.getTransaction().commit();
			success = 1; // assign 1 if success
		} catch (Exception e) {
			System.err.println("Exception Position: ProductDAO/delete()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return success;
	}

	// Query
	@Override
	public ProductVO findByPK(Integer prodId) {
		Session session = factory.getCurrentSession();
		ProductVO prodVO = null;
		try {
			session.beginTransaction();

			prodVO = session.get(ProductVO.class, prodId); 
			
			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Exception Position: ProductDAO/findByPK()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return prodVO;
	}

	@Override
	public List<ProductVO> getAll() {
		Session session = factory.getCurrentSession();
		List<ProductVO> list = null; // for return 
		try {
			session.beginTransaction();
			// HQL = SELECT * "FROM [class:ProductVO]"
			// with order =   "FROM ProductVO ORDER BY prodId (DESC)"
			list = session.createQuery("FROM ProductVO", ProductVO.class).list();
			
			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Exception Position: ProductDAO/getAll()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public List<ProductVO> CompositeQuery(Map<String, String> map) {
		// if no condition input, return getAll()
		if (map.size() == 0) return getAll();
		
		/* p.97
		 * 主要三個重點物件結合使用:
		 * 	CriteriaQuery	建立查詢語法 		multiselect()、distinct()、from()、where()、orderBy()、groupBy()、having()
		 * 	CriteriaBuilder	建立查詢條件 		equal()、greaterThan()、like()、isNull()、between()、count()、desc()
		 * 	TypedQuery		設定分頁並取得結果 setFirstResult()、setMaxResults()、getResultList()、getSingleResult()
		 *  Root (p.98)		代表查詢的實體型別
		 *  Predicate (p.98)代表查詢條件
		 */ 
		
		List<ProductVO> list = null; // list for return
		
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ProductVO> criteria = builder.createQuery(ProductVO.class);
			Root<ProductVO> root = criteria.from(ProductVO.class);
			List<Predicate> predicates = new ArrayList<>();
			
			// 複合查詢方式
			// prodId(NP) | prodIntro(NP) | removeDate(NP) 
			// prodName(Like)
			// prodPrice(~):   prodPriceLow     ~ prodPriceHigh
			// releaseDate(~): startReleaseDate ~ EndReleaseDate
			// salesStatus(1) | timeLimitProd(1)
			
			// 1. With both low & high of prodPrice | Remember to check parameter when write JSP
			if (map.containsKey("prodPriceLow") && map.containsKey("prodPriceHigh")) {
				// Convert to correct data-type
				Integer low  = Integer.parseInt(map.get("prodPriceLow"));
				Integer high = Integer.parseInt(map.get("prodPriceHigh"));
				// Path setup
				Path<Integer> prodPricePath = root.get("prodPrice");
				// Predicate condition setup
				Predicate prodPriceBetween = builder.between(prodPricePath, low, high);
				// Add Predicate to condition list
				predicates.add(prodPriceBetween);
			} // END of 1. With both low & high of prodPrice
			
			// 2. With both start & end of releaseDate
			if (map.containsKey("startReleaseDate") && map.containsKey("endReleaseDate")) {
				// Convert to correct data-type
				Date start = Date.valueOf(map.get("startReleaseDate"));
				Date end   = Date.valueOf(map.get("endReleaseDate"));
				// Path setup
				Path<Date> releaseDatePath = root.get("releaseDate");
				// Predicate condition setup
				Predicate betweenReleaseDate = builder.between(releaseDatePath, start, end);
				// Add Predicate to condition list
				predicates.add(betweenReleaseDate);
			} // END of 2. With both start & end of releaseDate
			
			for (Map.Entry<String, String> row : map.entrySet() ) {
				// 3. Like query of prodName
				if ("prodName".equals(row.getKey())) {
					// Convert to correct data-type
					String prodNameSearch = "%" + row.getValue() + "%";
					// Path setup
					Path<String> prodNamePath = root.get("prodName");
					// Predicate condition setup
					Predicate likeProdName = builder.like(prodNamePath, prodNameSearch); 
					// Add Predicate to condition list
					predicates.add(likeProdName);
				} // END of 3. Like query of prodName
				
				// 4. Select ONE salesStatus
				if ("salesStatus".equals(row.getKey())) {
					// Convert to correct data-type
					Integer salesStatusInt = Integer.parseInt(row.getValue());
					// Path setup
					Path<Integer> salesStatusPath = root.get("salesStatus");
					// Predicate condition setup
					Predicate salesStatusSelect = builder.equal(salesStatusPath, salesStatusInt);
					// Add Predicate to condition list
					predicates.add(salesStatusSelect);
				} // END of 4. Select ONE salesStatus
				
				// 5. Select ONE timeLimitProd
				if ("timeLimitProd".equals(row.getKey())) {
					// Convert to correct data-type
					Boolean timeLimitProdBL = Boolean.valueOf(row.getValue());
					// Path setup
					Path<Boolean> timeLimitProdPath = root.get("timeLimitProd");
					// Predicate condition setup
					Predicate timeLimitProdSelect = builder.equal(timeLimitProdPath, timeLimitProdBL);
					// Add Predicate to condition list
					predicates.add(timeLimitProdSelect);
				} // END of 5. Select ONE timeLimitProd
				
				// Range
				// 6. Only low of prodPrice
				if (("prodPriceLow".equals(row.getKey())) && !map.containsKey("prodPriceHigh")) {
					// Convert to correct data-type
					Integer low = Integer.parseInt(row.getValue());
					// Path setup
					Path<Integer> prodPricePath = root.get("prodPrice");
					// Predicate condition setup
					Predicate prodPriceGE = builder.ge(prodPricePath, low); // For numeric
//							  prodPriceGE = builder.greaterThanOrEqualTo(prodPricePath, low); // Equivalent (For any type that implements the Comparable)
					// Add Predicate to condition list
					predicates.add(prodPriceGE);
				} // END of 6. Only low of prodPrice
				
				// 7. Only high of prodPrice
				if (("prodPriceHigh".equals(row.getKey())) && (!map.containsKey("prodPriceLow"))) {
					// Convert to correct data-type
					Integer high = Integer.parseInt(row.getValue());
					// Path setup
					Path<Integer> prodPricePath = root.get("prodPrice");
					// Predicate condition setup
					Predicate prodPriceLE = builder.le(prodPricePath, high); // For numeric
//							  prodPriceLE = builder.lessThanOrEqualTo(prodPricePath, high); // Equivalent (For any type that implements the Comparable)
					// Add Predicate to condition list
					predicates.add(prodPriceLE);
				} // END of 7. Only high of prodPrice
				
				// 8. Only start of releaseDate
				if (("startReleaseDate".equals(row.getKey())) && (!map.containsKey("endReleaseDate"))) {
					// Convert to correct data-type
					Date start = Date.valueOf(row.getValue());
					// Path setup
					Path<Date> releaseDatePath = root.get("releaseDate");
					// Predicate condition setup
					Predicate releaseDateStart = builder.greaterThanOrEqualTo(releaseDatePath, start);
					// Add Predicate to condition list
					predicates.add(releaseDateStart);
				} // 8. Only start of releaseDate
				
				// 9. Only end of releaseDate
				if (("endReleaseDate".equals(row.getKey())) && (!map.containsKey("startReleaseDate"))) {
					// Convert to correct data-type
					Date end = Date.valueOf(row.getValue());
					// Path setup
					Path<Date> releaseDatePath = root.get("releaseDate");
					// Predicate condition setup
					Predicate releaseDateEnd = builder.lessThanOrEqualTo(releaseDatePath, end);
					// Add Predicate to condition list
					predicates.add(releaseDateEnd);
				} // 9. Only end of releaseDate
				
			} // END of for (Map.Entry<String, String> row : map.entrySet() )
			
			criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
			criteria.orderBy(builder.asc(root.get("prodId")));
			list = session.createQuery(criteria).getResultList(); // 會不會有 session close 問題? > 看起來沒有
			
			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Exception Position: ProductDAO/CompositeQuery()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		// DEBUG
		System.out.println("Had run CQ, list:");
		for (ProductVO p : list) System.out.println(p);
		System.out.println();
		// DEBUG
		
		return list;
	}
	
	// ---
	@Override
	public List<ProductImgVO> getImgsByProdId(Integer prodId) {
		Session session = factory.getCurrentSession();
		List<ProductImgVO> list = null;
		try {
			session.beginTransaction();
			// 1. Get ProductVO obj from prodId
			ProductVO prodVO = session.get(ProductVO.class, prodId);
			// 2. use obj get list of imgs
			if (prodVO != null) {
				list = prodVO.getProdImgs();
			}
			// To avoid Lazy init, use below for() / Replace by fetch, ref p.116 / case Exception when delete, use for()
			for(ProductImgVO img : list) {	
			}
			session.getTransaction().commit();
			
		} catch (Exception e) {
			System.err.println("Exception Position: ProductDAO/getImgsByProdId()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}
	// ---
}
