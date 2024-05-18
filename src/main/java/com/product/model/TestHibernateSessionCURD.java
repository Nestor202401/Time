package com.product.model;

import org.hibernate.Session;

import myutil.MyUtil;

public class TestHibernateSessionCURD {

	public static void main(String[] args) {
		Session session = MyUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Product prod = session.get(Product.class, 13000001);
			System.out.println(prod);
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
