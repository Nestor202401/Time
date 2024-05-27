package com.cinema.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.movietime.model.MovieTimeVO;

@Entity
@Table(name="cinema")
public class CinemaVO {
	
	@Id
	@Column(name="cinema_id" ,updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cinemaId; 
	
	@Column(name="cinema_name")
    private String cinemaName; 
	
	@Column(name="seat_row")
    private Integer seatRow; 
	
	@Column(name="seat_column")
    private String seatColumn;
	
	@Column(name="seat_status")
    private Integer seatStatus;
	
	@Column(name="seat_number" )
    private String seatNumber;
	
	@OneToMany(mappedBy = "cinemaVO", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieTimeVO> movieTimeVO;
	
    
	public Integer getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(Integer cinemaId) {
		this.cinemaId = cinemaId;
	}
	public String getCinemaName() {
		return cinemaName;
	}
	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}
	public Integer getSeatRow() {
		return seatRow;
	}
	public void setSeatRow(Integer seatRow) {
		this.seatRow = seatRow;
	}
	public String getSeatColumn() {
		return seatColumn;
	}
	public void setSeatColumn(String seatColumn) {
		this.seatColumn = seatColumn;
	}
	public Integer getSeatStatus() {
		return seatStatus;
	}
	public void setSeatStatus(Integer seatStatus) {
		this.seatStatus = seatStatus;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	
	
	public Set<MovieTimeVO> getMovieTimeVO() {
		return movieTimeVO;
	}
	public void setMovieTimeVO(Set<MovieTimeVO> movieTimeVO) {
		this.movieTimeVO = movieTimeVO;
	}
	public CinemaVO(Integer cinemaId, String cinemaName, Integer seatRow, String seatColumn, Integer seatStatus,
			String seatNumber) {
		super();
		this.cinemaId = cinemaId;
		this.cinemaName = cinemaName;
		this.seatRow = seatRow;
		this.seatColumn = seatColumn;
		this.seatStatus = seatStatus;
		this.seatNumber = seatNumber;
	}
	public CinemaVO() {
		super();
	}
    
    
}
