package com.product_img.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.util.ProductUtil;

public class ProductImgDAO implements ProductImgDAO_interface {

	// SessionFactory 為 thread-safe，to 宣告為屬性讓請求執行緒們共用
	private SessionFactory factory;
	
	public ProductImgDAO() { // 在呼叫 DAO 同時建立工廠
		factory = ProductUtil.getSessionFactory(); // here
	}
	
	@Override
	public Integer insert(ProductImgVO prodImgVO) {
		Session session = factory.getCurrentSession();
		Integer prodImgId = null;
		try {
			session.beginTransaction();
			// insert success, get prodId and return
			prodImgId = (Integer) session.save(prodImgVO); 
			
			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Exception Position: ProductImgDAO/insert()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return prodImgId;
	}

	@Override
	public Integer update(ProductImgVO prodImgVO) {
		Session session = factory.getCurrentSession();
		Integer success = null;
		try {
			session.beginTransaction();
			
			session.update(prodImgVO); 
			
			session.getTransaction().commit();
			success = 1; // assign 1 if success
		} catch (Exception e) {
			System.err.println("Exception Position: ProductImgDAO/update()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return success;
	}

	@Override
	public Integer delete(Integer prodImgId) {
		Session session = factory.getCurrentSession();
		Integer success = null;
		try {
			session.beginTransaction();
			// 1. Find obj by prodImgId
			ProductImgVO prodImgVO = session.get(ProductImgVO.class, prodImgId);
			if(prodImgVO != null) { // 2. Check if obj exist
				session.delete(prodImgVO); // 3. If step 2 found, delete obj
			}
			
			session.getTransaction().commit();
			success = 1; // assign 1 if success
		} catch (Exception e) {
			System.err.println("Exception Position: ProductImgDAO/delete()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return success;
	}

	@Override
	public ProductImgVO findByPK(Integer prodImgId) {
		Session session = factory.getCurrentSession();
		ProductImgVO prodImgVO  = null;
		try {
			session.beginTransaction();
			
			prodImgVO = session.get(ProductImgVO.class, prodImgId);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Exception Position: ProductImgDAO/FindByPK()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return prodImgVO;
	}

	@Override
	public List<ProductImgVO> getAll() {
		// TODO Auto-generated method stub
		Session session = factory.getCurrentSession();
		List<ProductImgVO> list  = null;
		try {
			session.beginTransaction();
			
			list = session.createQuery("FROM ProductImgVO").list();
			
			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Exception Position: ProductImgDAO/getAll()");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

}
