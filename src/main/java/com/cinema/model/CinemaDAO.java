package com.cinema.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.movietime.model.MovieTimeVO;
import com.util.HibernateUtil;

public class CinemaDAO {

	private SessionFactory factory;

	public CinemaDAO() {
		factory = HibernateUtil.getSessionFactory();
	}

	private Session getSession() {
		return factory.getCurrentSession();
	}

	public void insert(List<CinemaVO> seats) {
        Transaction transaction = getSession().beginTransaction();
            for (CinemaVO seat : seats) {
            	getSession().save(seat);
            }
            transaction.commit();
	}
	
	public void updateSeatStatusBatch(String cinemaName, List<String> seatNumbers, int newStatus) {
	    Session session = getSession();
	    Transaction transaction = null;
	    try {
	        transaction = session.beginTransaction();
	        String hql = "UPDATE CinemaVO SET seatStatus = :newStatus WHERE cinemaName = :cinemaName AND seatNumber IN (:seatNumbers)";
	        
	        // 添加日志以帮助调试
	        System.out.println("HQL: " + hql);
	        System.out.println("newStatus: " + newStatus);
	        System.out.println("cinemaName: " + cinemaName);
	        System.out.println("seatNumbers: " + seatNumbers);

	        int updated = session.createQuery(hql)
	            .setParameter("newStatus", newStatus)
	            .setParameter("cinemaName", cinemaName)
	            .setParameterList("seatNumbers", seatNumbers)
	            .executeUpdate();
	        transaction.commit();
	        System.out.println("Updated rows: " + updated);
	    } catch (Exception e) {
	        if (transaction != null) transaction.rollback();
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}

	public List<CinemaVO> getAllCinemas() {
		Transaction transaction = getSession().beginTransaction();
		List<CinemaVO> cinemas = getSession().createQuery("FROM CinemaVO", CinemaVO.class).getResultList();
		transaction.commit();
		return cinemas;
	}

	

	public List<CinemaVO> findByCinemaName(String cinemaName) {
		Transaction transaction = getSession().beginTransaction();
		String hql = "FROM CinemaVO WHERE cinemaName = :cinemaName";
		List<CinemaVO> cinema = getSession().createQuery(hql, CinemaVO.class).setParameter("cinemaName", cinemaName)
				.getResultList();
		transaction.commit();
		return cinema;
	}

	public CinemaVO findByCinemaId(Integer cinemaId) {
		Transaction transaction = getSession().beginTransaction();
		CinemaVO cinema = getSession().get(CinemaVO.class, cinemaId);
		transaction.commit();
		return cinema;
	}

	public List<CinemaVO> getCinemaType() {
		Transaction transaction = getSession().beginTransaction();

		// 子查询：獲取去重複的電影院名稱
		String subQuery = "SELECT DISTINCT cinemaName FROM CinemaVO";

		// 使用子查詢獲取去重複的電影院對象
		String hql = "FROM CinemaVO c WHERE c.cinemaName IN (" + subQuery + ")";
		List<CinemaVO> cinemas = getSession().createQuery(hql, CinemaVO.class).getResultList();

		transaction.commit();
		return cinemas;
	}
}
