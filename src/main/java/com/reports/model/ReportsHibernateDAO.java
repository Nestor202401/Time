package com.reports.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.util.HibernateUtil;


public class ReportsHibernateDAO implements ReportsDAO_interface {

    private SessionFactory factory;

    public ReportsHibernateDAO() {
        factory = HibernateUtil.getSessionFactory();
    }
    private Session getSession() {
		return factory.getCurrentSession();
	}
    
    
    
    @Override
    public void insert(ReportsVO reportsVO) {
        Transaction tx = getSession().beginTransaction();
        getSession().save(reportsVO);
        tx.commit();
    }
    
//    @Override
//    public void insert(ReportsVO reportsVO) {
//        Session session = factory.openSession();
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            session.save(reportsVO);
//            tx.commit();
//        } catch (RuntimeException e) {
//            if (tx != null)
//                tx.rollback();
//            throw e;
//        } finally {
//            session.close();
//        }
//    }
    
    @Override
    public void update(ReportsVO reportsVO) {
        Transaction tx = getSession().beginTransaction();
        getSession().update(reportsVO);
        tx.commit();
    }

//    @Override
//    public void update(ReportsVO reportsVO) {
//        Session session = factory.openSession();
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            session.update(reportsVO);
//            tx.commit();
//        } catch (RuntimeException e) {
//            if (tx != null)
//                tx.rollback();
//            throw e;
//        } finally {
//            session.close();
//        }
//    }

    
    
    @Override
    public void delete(Integer reportId) {
    	Session session = getSession();
    	Transaction tx = session.beginTransaction();
    	try {
    	      tx = session.beginTransaction();
    	      ReportsVO reportsVO = session.get(ReportsVO.class, reportId);
    	      session.delete(reportsVO);
    	      tx.commit();
    	  } catch (RuntimeException e) {
    	      if (tx != null)
    	          tx.rollback();
    	      throw e;
    	  } finally {
    	      session.close();
    	  }
    }
    
//    @Override
//    public void delete(Integer reportId) {
//        Session session = factory.openSession();
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            ReportsVO reportsVO = session.get(ReportsVO.class, reportId);
//            session.delete(reportsVO);
//            tx.commit();
//        } catch (RuntimeException e) {
//            if (tx != null)
//                tx.rollback();
//            throw e;
//        } finally {
//            session.close();
//        }
//    }

    @Override
    public ReportsVO findByPrimaryKey(Integer reportId) {
        Session session = factory.openSession();
        ReportsVO reportsVO = null;
        try {
            reportsVO = session.get(ReportsVO.class, reportId);
        } finally {
            session.close();
        }
        return reportsVO;
    }

    @Override
    public List<ReportsVO> getAll() {
        Session session = factory.openSession();
        List<ReportsVO> list = null;
        try {
            list = session.createQuery("from ReportsVO").list();
        } finally {
            session.close();
        }
        return list;
    }
	@Override
	public void updateReport_status(Integer reportId) {
		// TODO Auto-generated method stub
		
	}

//    public static void main(String[] args) {
//        ReportsHibernateDAO dao = new ReportsHibernateDAO();
//
//        // 测试新增
//        ReportsVO reportsVO1 = new ReportsVO();
//        // 设置相应的属性值
//        dao.insert(reportsVO1);
//
//        // 测试更新
//        ReportsVO reportsVO2 = dao.findByPrimaryKey(1);
//        // 设置需要更新的属性值
//        dao.update(reportsVO2);
//
//        // 测试删除
//        dao.delete(1);
//
//        // 测试查询所有
//        List<ReportsVO> list = dao.getAll();
//        for (ReportsVO reportsVO : list) {
//            // 输出相应的属性值
//        }
//    }
}
