package com.ticorder.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.Query;

import coi618_util.*;

public class TicketOrderDAO implements TicketOrderDAO_interface {

	@Override
    public List<TicketOrderVO> getAll() {
        List<TicketOrderVO> list = new ArrayList<>();
        try (Session session = Coi618Util.getSessionFactory().openSession()) {
            Query<TicketOrderVO> query = session.createQuery(
                "FROM TicketOrderVO t LEFT JOIN FETCH t.memberId", TicketOrderVO.class);
            list = query.getResultList();
        }
        return list;
    }

	@Override
    public List<TicketOrderVO> getAll(Map<String, String[]> map) {
        List<TicketOrderVO> list = new ArrayList<>();
        try (Session session = Coi618Util.getSessionFactory().openSession()) {
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
}
