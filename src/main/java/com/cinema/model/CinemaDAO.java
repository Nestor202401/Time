package com.cinema.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.util.HibernateUtil;

public class CinemaDAO {
    
    private SessionFactory factory;

    public CinemaDAO() {
        factory = HibernateUtil.getSessionFactory();
    }
    
    private Session getSession() {
        return factory.getCurrentSession();
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
        List<CinemaVO> cinema = getSession().createQuery(hql, CinemaVO.class)
                            .setParameter("cinemaName", cinemaName)
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
        
        // 子查询：获取去重后的电影院名称
        String subQuery = "SELECT DISTINCT cinemaName FROM CinemaVO";
        
        // 使用子查询获取去重后的电影院对象
        String hql = "FROM CinemaVO c WHERE c.cinemaName IN (" + subQuery + ")";
        List<CinemaVO> cinemas = getSession().createQuery(hql, CinemaVO.class).getResultList();
        
        transaction.commit();
        return cinemas;
    }
}
