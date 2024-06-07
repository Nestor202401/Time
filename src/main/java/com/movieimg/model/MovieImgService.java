package com.movieimg.model;

import java.util.List;

public class MovieImgService {

	private MovieImgDAO_interface dao;

	public MovieImgService() {
		dao = new MovieImgJDBCDAO();
	}

	public MovieImgVO addMovieImg(Integer movieId, String movieImgName,String movieImgFile) {

		MovieImgVO movieImgVO = new MovieImgVO();

		movieImgVO.setMovieId(movieId);
		movieImgVO.setMovieImgName(movieImgName);
		movieImgVO.setMovieImgFile(movieImgFile);
		dao.insert(movieImgVO);

		return movieImgVO;
	}

	public MovieImgVO updateMovieImg(Integer movieImgId, Integer movieId, String movieImgName,String movieImgFile) {

		MovieImgVO movieImgVO = new MovieImgVO();
		movieImgVO.setMovieImgId(movieImgId);
		movieImgVO.setMovieId(movieId);
		movieImgVO.setMovieImgName(movieImgName);
		movieImgVO.setMovieImgFile(movieImgFile);
		
		dao.update(movieImgVO);

		return movieImgVO;
	}

	public void deleteMovieImg(Integer movieImgId) {
		dao.delete(movieImgId);
	}

	public MovieImgVO getOneMovieImg(Integer movieImgId) {
		return dao.findByPrimaryKey(movieImgId);
	}

	public List<MovieImgVO> getAll() {
		return dao.getAll();
	}
	public List<MovieImgVO> getKeyword(String keyword) {
		return dao.getKeyword(keyword);
	}
	public String getFirstImageByMovieId(Integer movieId) {
        return dao.getFirstImageByMovieId(movieId);
    }
	public List<MovieImgVO> getMovieImagesByMovieId(Integer movieId) {
        return dao.getImgByMovieId(movieId);
    }
}
