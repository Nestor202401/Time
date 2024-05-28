/*
package com.product_detail.model;

import java.util.List;

public interface ProductDetailDAO {
	void add(ProductDetail prodDetail);
	void update(ProductDetail prodDetail);
	void delete(Integer prodDetailId); // Not use
	ProductDetail findByPK(Integer prodDetailId);
	List<ProductDetail> getAll();
	
	// Universal query
//	public List<ProductDetail> getAll(Map<String, String[]> map);
}
*/

// --- Above: 0201 Interface --- | --- Below: Hibernate DAO Implement ---

package com.product_detail.model;

import java.util.List;
//import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import coi618_util.Coi618Util;

public class ProductDetailDAO implements ProductDetailDAO_interface {

	// SessionFactory 為 thread-safe，to 宣告為屬性讓請求執行緒們共用
	private SessionFactory factory;
	
	public ProductDetailDAO() { // 在呼叫 DAO 同時建立工廠
		factory = Coi618Util.getSessionFactory(); 
	}
	
	// Q: 這跟直接拿來用有什麼不同? > 直接用也可以
	// Session 為 not thread-safe，所以此方法在各個增刪改查方法裡呼叫
	// 以避免請求執行緒共用了同個 Session
//	private Session getSession() {
//		return factory.getCurrentSession();
//	}
	
	@Override
	public Integer insert(ProductDetailVO prodDetailVO) {
		Session session = factory.getCurrentSession();
		Integer prodDetailId = null;
		try {
			session.beginTransaction();
			// insert success, get prodDetailId and return 
			prodDetailId = (Integer) session.save(prodDetailVO);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Exception Position: DetailDAO/insert()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return prodDetailId;
	}

	@Override
	public Integer update(ProductDetailVO prodDetailVO) {
		Session session = factory.getCurrentSession();
		Integer success = null; // return (Success ? 1 : null)
		try {
			session.beginTransaction();
			
			session.update(prodDetailVO);
			
			session.getTransaction().commit();
			success = 1;
		} catch (Exception e) {
			System.err.println("Exception Position: DetailDAO/update()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return success;
	}

	@Override
	public Integer delete(Integer prodDetailId) {
		Session session = factory.getCurrentSession();
		Integer success = null; // return (Success ? 1 : null)
		try {
			session.beginTransaction();
			// 1. Find obj by prodDetailId
			ProductDetailVO detail = session.get(ProductDetailVO.class, prodDetailId);
			if(detail != null) { // 2. Check if obj exist
				session.delete(detail); // 3. If step2 find, delete obj
			}
			session.getTransaction().commit();
			success = 1;
		} catch (Exception e) {
			System.err.println("Exception Position: DetailDAO/delete()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return success;
	}

	@Override
	public ProductDetailVO findByPK(Integer prodDetailId) {
		Session session = factory.getCurrentSession();
		ProductDetailVO detail = null;
		try {
			session.beginTransaction();
			
			detail = session.get(ProductDetailVO.class, prodDetailId);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Exception Position: DetailDAO/findByPK()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return detail;
	}

	@Override
	public List<ProductDetailVO> getAll() {
		// TODO Auto-generated method stub
		Session session = factory.getCurrentSession();
		List<ProductDetailVO> list = null;
		try {
			session.beginTransaction();
			// HQL = SELECT * "FROM [class:ProductDetailVO]"
			// with order =   "FROM ProductDetailVO ORDER BY prodDetailId (DESC)"
			list = session.createQuery("FROM ProductDetailVO", ProductDetailVO.class).list();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Exception Position: DetailDAO/getAll()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	// CompositeQuery // 暫不實作
//	@Override
//	public List<ProductDetailVO> CompositeQuery(Map<String, String> map) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}