package com.ticorder.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.member.model.MemberVO;
import com.movietime.model.*;
import com.ticlist.model.*;
import com.ticorder.model.TicketOrderDAO;
import com.util.HibernateUtil;

public class TicketOrderDAO_Redis {
	
	private Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}

	// 前端下拉選單動態抓取上映電影功能

			public List<Object[]> getAllShowingMovieNames() {
			    Transaction transaction = getSession().beginTransaction();
			    List<Object[]> results = getSession().createQuery(
			            "SELECT DISTINCT mt.movieId, mv.movieName FROM MovieTimeVO mt JOIN mt.movieVO mv").list();
			    transaction.commit();
			    return results;
			}
			

			public List<Date> getShowTimeDatesByMovieId(Integer movieId) {
			    Transaction transaction = getSession().beginTransaction();
			    List<Date> showTimeDates = getSession().createQuery(
			            "SELECT DISTINCT mt.showTimeDate FROM MovieTimeVO mt WHERE mt.movieId = :movieId")
			            .setParameter("movieId", movieId)
			            .list();
			    transaction.commit();
			    return showTimeDates;
			}
			

			public List<Integer> getShowTimesByMovieIdAndDate(Integer movieId, Date showTimeDate) {
			    Transaction transaction = getSession().beginTransaction();
			    List<Integer> showTimes = getSession().createQuery(
			            "SELECT mt.showTime FROM MovieTimeVO mt WHERE mt.movieId = :movieId AND mt.showTimeDate = :showTimeDate")
			            .setParameter("movieId", movieId)
			            .setParameter("showTimeDate", showTimeDate)
			            .list();
			    transaction.commit();
			    return showTimes;
			}
			
			public MovieTimeVO getShowTimeIdByMovieIdAndDateAndTime(Integer movieId, Date showTimeDate,Integer showTime) {
			    Transaction transaction = getSession().beginTransaction();
	            Query<MovieTimeVO> query = getSession().createQuery(
	            		"FROM MovieTimeVO WHERE movieId = :movieId AND showTimeDate = :showTimeDate AND showTime = :showTime", MovieTimeVO.class);
	            query.setParameter("movieId", movieId);
	            query.setParameter("showTimeDate", showTimeDate);
	            query.setParameter("showTime", showTime);
	            MovieTimeVO movieTimeVO = query.uniqueResult();
			    transaction.commit();
			    return movieTimeVO;
			}
			
			public String getMovieNameById(String movieId) {
			    List<Object[]> movieList = getAllShowingMovieNames();
			    for (Object[] movie : movieList) {
			        if (movie[0].toString().equals(movieId)) {
			            return movie[1].toString();
			        }
			    }
			    return null; 
			}
			
//			public TicketOrderVO addOrderData(MemberVO memberId, Integer movieOrderTotal) {
//				TicketOrderVO movieOrderId = null;
//			    Transaction transaction = getSession().beginTransaction();
//			    
//			    TicketOrderVO ticketOrder = new TicketOrderVO();
//		        ticketOrder.setMemberId(memberId);
//		        ticketOrder.setMovieOrderStatus(false);
//		        ticketOrder.setBuyTicketsDate(new Timestamp(System.currentTimeMillis()));
//		        ticketOrder.setMovieOrderTotal(movieOrderTotal);      
//		        getSession().save(ticketOrder);
//		        movieOrderId = getSession().get(TicketOrderVO.class, movieOrderId);
//			    transaction.commit();
//			    
//			    return movieOrderId;
//			}
			
			public Integer addOrderData(MemberVO memberId, Integer movieOrderTotal) {
			    Transaction transaction = getSession().beginTransaction();
			    
			    TicketOrderVO ticketOrder = new TicketOrderVO();
		        ticketOrder.setMemberId(memberId);
		        ticketOrder.setMovieOrderStatus(false);
		        ticketOrder.setBuyTicketsDate(new Timestamp(System.currentTimeMillis()));
		        ticketOrder.setMovieOrderTotal(movieOrderTotal);      
		        getSession().save(ticketOrder);
		        Integer movieOrderId = ticketOrder.getMovieOrderId();
			    transaction.commit();
			    return movieOrderId;
			}
			
	
}
