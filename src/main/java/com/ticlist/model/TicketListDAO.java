package com.ticlist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.util.HibernateUtil;

public class TicketListDAO implements TicketListDAO_interface {

	private Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	@Override
    public List<TicketListVO> getAll() {
		Transaction transaction = getSession().beginTransaction();
        List<TicketListVO> list = new ArrayList<>();
        Query<TicketListVO> query = getSession().createQuery(
            "FROM TicketOrderVO t LEFT JOIN FETCH t.memberId", TicketListVO.class);
        list = query.getResultList();
        transaction.commit();
        return list;
    }
	
}
