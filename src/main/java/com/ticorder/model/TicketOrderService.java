package com.ticorder.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.member.model.MemberVO;
import com.movie.model.MovieVO;
import com.movietime.model.MovieTimeVO;
import com.tictypes.model.TicketTypesVO;

@Service
public class TicketOrderService {
	
	private TicketOrderDAO dao;
	
	private TicketOrderDAO_Redis dao_Redis;

    public TicketOrderService() {
        this.dao = new TicketOrderDAO();
        this.dao_Redis = new TicketOrderDAO_Redis();
    }
    

    public List<TicketOrderVO> getAll() {
        return dao.getAll();
    }

    public List<TicketOrderVO> getAll(Map<String, String[]> map) {
        return dao.getAll(map);
    }
    
    public List<Object[]> getAllShowingMovieNames() {
        return dao_Redis.getAllShowingMovieNames();
    }
    
    public List<Date> getShowTimeDatesByMovieId(Integer movieId) {
        return dao_Redis.getShowTimeDatesByMovieId(movieId);
    }
    
    public List<Integer> getShowTimesByMovieIdAndDate(Integer movieId, Date showTimeDate) {
        return dao_Redis.getShowTimesByMovieIdAndDate(movieId, showTimeDate);
    }
    
    public String getMovieNameById(String movieId) {
    	return dao_Redis.getMovieNameById(movieId);
    }
    
    public MovieTimeVO getShowTimeIdByMovieIdAndDateAndTime(Integer movieId, Date showTimeDate,Integer showTime) {
    	return dao_Redis.getShowTimeIdByMovieIdAndDateAndTime(movieId, showTimeDate, showTime);
    }
    public Integer addOrderData(MemberVO memberId, Integer movieOrderTotal) {
    	return dao_Redis.addOrderData(memberId,movieOrderTotal);
    }
    
    public MemberVO findMemberById(Integer memberId) {
    	return dao.findMemberById(memberId);
    }
    public MovieVO findMovieById(Integer movieId) {
    	return dao.findMovieById(movieId);
    }
    public TicketTypesVO findTicketTypesById(Integer ticketTypesId) {
    	return dao.findTicketTypesById(ticketTypesId);
    }
    public MovieTimeVO findshowTimesById(Integer showTimesId) {
    	return dao.findshowTimesById(showTimesId);
    }
}
