package com.movietime.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieTimeService {
    private MovieTimeDAO dao;

    public MovieTimeService() {
        dao = new MovieTimeDAO();
    }

    public List<MovieTimeVO> getAllMovieTime() {
        return dao.getAll();
    }
    
    public Map<String, List<?>> getMovieKeyword(String keyword) {
        return dao.getMovieKeyword(keyword);
    }
    
    public MovieTimeVO addMovieTime(Integer movieId, Integer cinemaId, Integer moviePlaybackType,
            Integer price, Integer showTime, Date showTimeDate) {

    	MovieTimeVO movieTimeVO = new MovieTimeVO();
    	
    	movieTimeVO.setMovieId(movieId);
		movieTimeVO.setCinemaId(cinemaId);
		movieTimeVO.setMoviePlaybackType(moviePlaybackType);
		movieTimeVO.setPrice(price);
		movieTimeVO.setShowTime(showTime);
		movieTimeVO.setShowTimeDate(showTimeDate);
    	
		Integer ShowTimesId = dao.insert(movieTimeVO);
        movieTimeVO.setShowTimesId(ShowTimesId); // Assuming `showTimesId` is the primary key

        return movieTimeVO;
    }
    
    public MovieTimeVO updateMovieTime(Integer showTimesId,Integer movieId, Integer cinemaId, Integer moviePlaybackType,
            Integer price, Integer showTime, Date showTimeDate) {
    	MovieTimeVO movieTimeVO = new MovieTimeVO();
    	
    	movieTimeVO.setShowTimesId(showTimesId);
    	movieTimeVO.setMovieId(movieId);
		movieTimeVO.setCinemaId(cinemaId);
		movieTimeVO.setMoviePlaybackType(moviePlaybackType);
		movieTimeVO.setPrice(price);
		movieTimeVO.setShowTime(showTime);
		movieTimeVO.setShowTimeDate(showTimeDate);
    	
    	dao.update(movieTimeVO);
    	return movieTimeVO;
	}
    
    public MovieTimeVO getOneMovieTime(Integer showTimesId) {
		return dao.findByPrimaryKey(showTimesId);
	}
    
    public Map<String, List<Integer>> getShowtimesByMovieId(Integer movieId) {
        List<MovieTimeVO> showtimes = dao.getShowtimesByMovieId(movieId);
        Map<String, List<Integer>> result = new HashMap<>();

        for (MovieTimeVO showtime : showtimes) {
            String date = showtime.getShowTimeDate().toString();
            if (!result.containsKey(date)) {
                result.put(date, new ArrayList<>());
            }
            result.get(date).add(showtime.getShowTime());
        }

        return result;
    }
    
    
}
