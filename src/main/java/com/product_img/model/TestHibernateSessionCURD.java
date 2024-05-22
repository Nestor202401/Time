package com.product_img.model;

import org.hibernate.Session;

import myutil.MyUtil;

public class TestHibernateSessionCURD {

	public static void main(String[] args) {
		Session session = MyUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			ProductImg prodImg = session.get(ProductImg.class, 14000001);
			System.out.println(prodImg);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			System.err.println("Position: TestHibernateSessionCURD | get");
		}
		finally {
			MyUtil.shutdownSessionFactory();
		}
		
	
	}

}
