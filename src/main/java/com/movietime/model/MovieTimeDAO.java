package com.movietime.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.movie.model.MovieVO;
import com.util.HibernateUtil;

public class MovieTimeDAO {
    
    private SessionFactory factory;

    public MovieTimeDAO() {
        factory = HibernateUtil.getSessionFactory();
    }
    
    private Session getSession() {
        return factory.getCurrentSession();
    }
    
    public void update(MovieTimeVO movieTimeVO) {
		Transaction transaction = getSession().beginTransaction();
		getSession().update(movieTimeVO);
		transaction.commit();
	}

    public List<MovieTimeVO> getAll() {
        Transaction transaction = getSession().beginTransaction();
        List<MovieTimeVO> movieTime = getSession().createQuery(
        "SELECT mt FROM MovieTimeVO mt JOIN FETCH mt.cinemaVO JOIN FETCH mt.movieVO", MovieTimeVO.class).list();
        transaction.commit();
        return movieTime;
    }
    
	public Integer insert(MovieTimeVO movieTimeVO) {
    	Transaction transaction = getSession().beginTransaction();
    	Integer showTimesId =(Integer)getSession().save(movieTimeVO);
        transaction.commit();
        return showTimesId;
	}
	
	public Map<String, List<?>> getMovieKeyword(String keyword) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Map<String, List<?>> result = new HashMap<>();
        
            transaction = session.beginTransaction();

            // 查询MovieTimeVO
            String hql1 = "SELECT mt FROM MovieTimeVO mt JOIN FETCH mt.cinemaVO JOIN FETCH mt.movieVO mv WHERE mv.movieName LIKE :keyword";   
            List<MovieTimeVO> movieTimeList = session.createQuery(hql1, MovieTimeVO.class)
                                                     .setParameter("keyword", "%" + keyword + "%")
                                                     .getResultList();

            // 查询MovieVO
            String hql2 = "SELECT mv FROM MovieVO mv WHERE mv.movieName LIKE :keyword";
            List<MovieVO> movieList = session.createQuery(hql2, MovieVO.class)
                                             .setParameter("keyword", "%" + keyword + "%")
                                             .getResultList();

            transaction.commit();
            result.put("movieTimes", movieTimeList);
            result.put("movies", movieList);
            return result;
    }


//	@Override
//	public void update(MovieVO movieVO) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void delete(Integer movieId) {
//		// TODO Auto-generated method stub
//		
//	}

	public MovieTimeVO findByPrimaryKey(Integer showTimesId) {
	    Transaction transaction = getSession().beginTransaction();
	    MovieTimeVO movieTimeVO = getSession().get(MovieTimeVO.class, showTimesId);
	    transaction.commit();
	    return movieTimeVO;
	}



	
    
    
}
