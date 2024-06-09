package com.ticorder.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.member.model.MemberVO;
import com.movie.model.MovieVO;
import com.movietime.model.MovieTimeVO;
import com.tictypes.model.TicketTypesVO;
import com.util.HibernateUtil;

public class TicketOrderDAO implements TicketOrderDAO_interface {
	
	private Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}

	@Override
    public List<TicketOrderVO> getAll() {
        List<TicketOrderVO> list = new ArrayList<>();
        Transaction transaction = getSession().beginTransaction();
        Query<TicketOrderVO> query = getSession().createQuery(
            "FROM TicketOrderVO t LEFT JOIN FETCH t.memberId", TicketOrderVO.class);
        list = query.getResultList();
        transaction.commit();
        return list;
    }

	@Override
    public List<TicketOrderVO> getAll(Map<String, String[]> map) {
        List<TicketOrderVO> list = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM TicketOrderVO t LEFT JOIN FETCH t.memberId WHERE 1 = 1");

            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                String key = entry.getKey();
                String[] values = entry.getValue();
                if (values != null && values.length > 0) {
                    if ("movieOrderId".equals(key)) {
                        hql.append(" AND CONCAT(t.movieOrderId, '') LIKE :movieOrderId");
                    } else if ("memberAccount".equals(key)) {
                        hql.append(" AND CONCAT(t.memberId.memberAccount, '') LIKE :memberAccount");
                    }
                }
            }

            Query<TicketOrderVO> query = session.createQuery(hql.toString(), TicketOrderVO.class);

            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                String key = entry.getKey();
                String[] values = entry.getValue();
                if (values != null && values.length > 0) {
                    if ("movieOrderId".equals(key)) {
                        query.setParameter("movieOrderId", "%" + values[0] + "%");
                    } else if ("memberAccount".equals(key)) {
                        query.setParameter("memberAccount", "%" + values[0] + "%");
                    }
                }
            }

            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public MemberVO findMemberById(Integer memberId) {
        MemberVO member = null;
        Transaction transaction = getSession().beginTransaction();
        member = getSession().get(MemberVO.class, memberId);
        transaction.commit();
        return member;
    }
	
	public MovieVO findMovieById(Integer movieId) {
		MovieVO movie = null;
        Transaction transaction = getSession().beginTransaction();
        movie = getSession().get(MovieVO.class, movieId);
        transaction.commit();
        return movie;
    }
	
	public TicketTypesVO findTicketTypesById(Integer ticketTypesId) {
		TicketTypesVO ticketTypes = null;
        Transaction transaction = getSession().beginTransaction();
        ticketTypes = getSession().get(TicketTypesVO.class, ticketTypesId);
        transaction.commit();
        return ticketTypes;
    }
	
	public MovieTimeVO findshowTimesById(Integer showTimesId) {
		MovieTimeVO movieTime = null;
        Transaction transaction = getSession().beginTransaction();
        movieTime = getSession().get(MovieTimeVO.class, showTimesId);
        transaction.commit();
        return movieTime;
    }

	
}
