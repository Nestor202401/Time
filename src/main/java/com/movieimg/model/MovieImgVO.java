package com.movieimg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="movie_img")
public class MovieImgVO {

	@Id
	@Column(name="movie_img_id",updatable = false)
	private Integer movieImgId;
	
	@Column(name="movie_id")
	private Integer movieId;
	
	@Column(name="movie_img_name")
	private String movieImgName; 
	
	@Column(name="movie_img_file")
	private String movieImgFile;
	
	
	public Integer getMovieImgId() {
		return movieImgId;
	}
	public void setMovieImgId(Integer movieImgId) {
		this.movieImgId = movieImgId;
	}
	public Integer getMovieId() {
		return movieId;
	}
	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}
	public String getMovieImgName() {
		return movieImgName;
	}
	public void setMovieImgName(String movieImgName) {
		this.movieImgName = movieImgName;
	}
	public String getMovieImgFile() {
		return movieImgFile;
	}
	public void setMovieImgFile(String movieImgFile) {
		this.movieImgFile = movieImgFile;
	}
	public MovieImgVO() {
		super();
	}
	public MovieImgVO(Integer movieImgId, Integer movieId, String movieImgName, String movieImgFile) {
		super();
		this.movieImgId = movieImgId;
		this.movieId = movieId;
		this.movieImgName = movieImgName;
		this.movieImgFile = movieImgFile;
	}
	@Override
	public String toString() {
		return "MovieImgVO [movieImgId=" + movieImgId + ", movieId=" + movieId + ", movieImgName=" + movieImgName
				+ ", movieImgFile=" + movieImgFile + "]";
	}
	
	// for join movie from movieId
		public com.movie.model.MovieVO getMovieVO() {
			com.movie.model.MovieService movieSvc = new com.movie.model.MovieService();
			com.movie.model.MovieVO movieVO = movieSvc.getOneMovie(movieId);
			return movieVO;
		}
	
	
}
