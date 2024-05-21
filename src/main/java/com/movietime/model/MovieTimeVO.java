package com.movietime.model;

import java.sql.Date;


public class MovieTimeVO {
    
    
    private Integer showTimesId ;
    
    private Integer movieId; 
    
    private Integer cinemaId; 
    
    private Integer moviePlaybackType ;
    
    private Integer price; 
    
    private Integer showTime; 
    
    private Date showTimeDate;

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

	public void setMoviePlaybackType(Integer moviePlaybackType) {
		this.moviePlaybackType = moviePlaybackType;
		switch(moviePlaybackType) {
		case 1:
			setPrice(0);
			break;
		case 2:
			setPrice(20);
			break;
		case 3:
			setPrice(40);
			break;
		}
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

	

	
}
