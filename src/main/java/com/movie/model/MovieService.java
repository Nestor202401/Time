package com.movie.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.movieimg.model.MovieImgService;

public class MovieService {

	private MovieDAO_interface dao;
	private RedisDAO redisDAO;
	private MovieImgService movieImgService;

	 public MovieService() {
	        dao = new MovieJDBCDAO();
	        redisDAO = new RedisDAO();
	        movieImgService = new MovieImgService();
	    }

	public MovieVO addMovie(String movieName, Integer movieRating, String director, String actor,
			java.util.Date releaseDate, java.util.Date endDate, Integer runtime, String introduction) {

		MovieVO movieVO = new MovieVO();

		movieVO.setMovieName(movieName);
		movieVO.setMovieRating(movieRating);
		movieVO.setDirector(director);
		movieVO.setActor(actor);
		movieVO.setReleaseDate(new java.sql.Date(releaseDate.getTime()));
		movieVO.setEndDate(new java.sql.Date(endDate.getTime()));
		movieVO.setRuntime(runtime);
		movieVO.setIntroduction(introduction);

        // 插入電影並設置自動生成的電影ID
		int movieId = dao.insert(movieVO);
		movieVO.setMovieId(movieId);

		redisDAO.deleteMovies();

		return movieVO;
	}

	public MovieVO updateMovie(Integer movieId, String movieName, Integer movieRating, String director, String actor,
			Date releaseDate, Date endDate, Integer runtime, String introduction) {

		MovieVO movieVO = new MovieVO();
		movieVO.setMovieId(movieId);
		movieVO.setMovieName(movieName);
		movieVO.setMovieRating(movieRating);
		movieVO.setDirector(director);
		movieVO.setActor(actor);
		movieVO.setReleaseDate(releaseDate);
		movieVO.setEndDate(endDate);
		movieVO.setRuntime(runtime);
		movieVO.setIntroduction(introduction);
		dao.update(movieVO);

		redisDAO.deleteMovies();

		return movieVO;
	}

	public void deleteMovie(Integer movieId) {
		redisDAO.deleteMovies();
		dao.delete(movieId);
	}

	public MovieVO getOneMovie(Integer movieId) {
		List<MovieVO> movies = redisDAO.getMovies();
		if (movies == null) { // redis找不到資料
			movies = dao.getAll(); // 找mysql資料庫
			if (movies != null) {
//	            	mysql找到資料同步到redis並設置消亡時間
				redisDAO.saveMovies(movies);
			}
		}

		// 如果redis有資料就從資料庫查找資料
		for (MovieVO movie : movies) {
			if (movie.getMovieId().equals(movieId)) {
				return movie;
			}
		}
		return null;
	}

	public List<MovieVO> getAll() {
		List<MovieVO> movies = redisDAO.getMovies();
		if (movies == null) { // redis找不到資料
			movies = dao.getAll(); // 找mysql資料庫
			if (movies != null) {
//            	mysql找到資料同步到redis並設置消亡時間
				redisDAO.saveMovies(movies);
			}
		}
		return movies;
	}
	
	

	public List<MovieVO> getAll(Map<String, String[]> map) {
		List<MovieVO> movies = dao.getAll(map);
		return movies;
	}

	public List<MovieVO> findHotMovie() {
		return dao.findHotMovie();
	}

	public List<MovieVO> findOutMovie() {
		return dao.findOutMovie();
	}

	public List<MovieVO> comingSoonMovie() {
		return dao.comingSoonMovie();
	}
	
	public List<MovieVO> getHotMoviesWithImg() {
	    List<MovieVO> movies = this.findHotMovie();
	    for (MovieVO movie : movies) {
	        String firstImage = movieImgService.getFirstImageByMovieId(movie.getMovieId());
	        movie.setFirstImage(firstImage);
	    }
	    return movies;
	}
	
	public List<MovieVO> getcomingSoonWithImg() {
	    List<MovieVO> movies = this.comingSoonMovie();
	    for (MovieVO movie : movies) {
	        String firstImage = movieImgService.getFirstImageByMovieId(movie.getMovieId());
	        movie.setFirstImage(firstImage);
	    }
	    return movies;
	}

	public List<MovieVO> getHotMoviesFourImg() {
	    List<MovieVO> movies = getHotMoviesWithImg();
	    // 只返回前4個數據
	    if (movies.size() > 4) {
	        return movies.subList(0, 4);
	    } else {
	        return movies;
	    }
	}

}
