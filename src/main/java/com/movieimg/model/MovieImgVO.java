package com.movieimg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.movie.model.MovieVO;

@Entity
@Table(name = "movie_img")
public class MovieImgVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_img_id", updatable = false)
    private Integer movieImgId;

    @Column(name = "movie_id")
    private Integer movieId;
    
    @Column(name = "movie_img_name")
    private String movieImgName;

    @Column(name = "movie_img_file")
    private String movieImgFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private MovieVO movieVO;

    

    // getter和setter方法
    public Integer getMovieImgId() {
        return movieImgId;
    }

    public void setMovieImgId(Integer movieImgId) {
        this.movieImgId = movieImgId;
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

    public MovieVO getMovie() {
        return movieVO;
    }

    public void setMovie(MovieVO movieVO) {
        this.movieVO = movieVO;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
    
 // for join movie from movieId
 		public com.movie.model.MovieVO getMovieVO() {
 			com.movie.model.MovieService movieSvc = new com.movie.model.MovieService();
 			com.movie.model.MovieVO movieVO = movieSvc.getOneMovie(movieId);
 			return movieVO;
 		}
    
}
