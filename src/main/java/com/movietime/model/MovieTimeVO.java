package com.movietime.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cinema.model.CinemaVO;
import com.movie.model.MovieVO;

@Entity
@Table(name="movie_time")
public class MovieTimeVO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_times_id")
    private Integer showTimesId;
    
    @Column(name = "movie_id")
    private Integer movieId; 
    
    @Column(name = "cinema_id")
    private Integer cinemaId; 
    
    @Column(name = "movie_playback_type")
    private Integer moviePlaybackType;
    
    @Column(name = "price")
    private Integer price; 
    
    @Column(name = "show_time")
    private Integer showTime; 
    
    @Column(name = "show_time_date")
    private Date showTimeDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private MovieVO movieVO;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id", insertable = false, updatable = false)
    private CinemaVO cinemaVO;
    
    

    // getters and setters
    public MovieVO getMovieVO() {
        return movieVO;
    }

    public void setMovieVO(MovieVO movieVO) {
        this.movieVO = movieVO;
    }

    public Integer getShowTimesId() {
        return showTimesId;
    }

    public void setShowTimesId(Integer showTimesId) {
        this.showTimesId = showTimesId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Integer getMoviePlaybackType() {
        return moviePlaybackType;
    }
    
    

    public CinemaVO getCinemaVO() {
		return cinemaVO;
	}

	public void setCinemaVO(CinemaVO cinemaVO) {
		this.cinemaVO = cinemaVO;
	}

	public void setMoviePlaybackType(Integer moviePlaybackType) {
        this.moviePlaybackType = moviePlaybackType;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getShowTime() {
        return showTime;
    }

    public void setShowTime(Integer showTime) {
        this.showTime = showTime;
    }

    public Date getShowTimeDate() {
        return showTimeDate;
    }

    public void setShowTimeDate(Date showTimeDate) {
        this.showTimeDate = showTimeDate;
    }

	

	public MovieTimeVO() {
		super();
	}

	public MovieTimeVO(Integer movieId, Integer cinemaId, Integer moviePlaybackType, Integer price, Integer showTime,
			Date showTimeDate) {
		super();
		this.movieId = movieId;
		this.cinemaId = cinemaId;
		this.moviePlaybackType = moviePlaybackType;
		this.price = price;
		this.showTime = showTime;
		this.showTimeDate = showTimeDate;
	}

	
    
    
}
