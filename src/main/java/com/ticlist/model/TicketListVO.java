package com.ticlist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ticorder.model.*;
import com.tictypes.model.*;
import com.movie.model.*;
import com.cinema.model.*;
import com.movietime.model.*;

@Entity
@Table(name = "ticket_list")
public class TicketListVO implements java.io.Serializable{	//ticket_list電影票明細
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "movie_ticket_id", updatable = false)
	private Integer movieTicketId;		//電影票明細ID
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_order_id", referencedColumnName = "movie_order_id")
	private TicketOrderVO movieOrderId;	 //電影ID
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id", referencedColumnName = "movie_id")
	private MovieVO movieId;	 //電影訂單ID
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_types_id", referencedColumnName = "ticket_types_id")
	private TicketTypesVO ticketTypesId; //票種ID
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cinema_id", referencedColumnName = "cinema_id")
	private CinemaVO cinemaId;		//影廳ID
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "show_times_id", referencedColumnName = "show_times_id")
	private MovieTimeVO showTimesId;		//場次ID
	
	@Column(name = "seat_number")
	private String seatNumber;			//座位編號
	
	@Column(name = "ticket_status")
	private Boolean ticketStatus;		//電影票使用狀態 false:未使用 true:已使用
	
	
	public TicketListVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TicketListVO(Integer movieTicketId, TicketOrderVO movieOrderId, MovieVO movieId,
			TicketTypesVO ticketTypesId, CinemaVO cinemaId, MovieTimeVO showTimesId, String seatNumber,
			Boolean ticketStatus) {
		this.movieTicketId = movieTicketId;
		this.movieOrderId = movieOrderId;
		this.movieId = movieId;
		this.ticketTypesId = ticketTypesId;
		this.cinemaId = cinemaId;
		this.showTimesId = showTimesId;
		this.seatNumber = seatNumber;
		this.ticketStatus = ticketStatus;
	}

	public Integer getMovieTicketId() {
		return movieTicketId;
	}
	public void setMovieTicketId(Integer movieTicketId) {
		this.movieTicketId = movieTicketId;
	}
	public TicketOrderVO getMovieOrderId() {
		return movieOrderId;
	}
	public void setMovieOrderId(TicketOrderVO movieOrderId) {
		this.movieOrderId = movieOrderId;
	}
	public MovieTimeVO getShowTimesId() {
		return showTimesId;
	}
	public void setShowTimesId(MovieTimeVO showTimesId) {
		this.showTimesId = showTimesId;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	public Boolean getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(Boolean ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	public TicketTypesVO getTicketTypesId() {
		return ticketTypesId;
	}
	public void setTicketTypesId(TicketTypesVO ticketTypesId) {
		this.ticketTypesId = ticketTypesId;
	}

	public MovieVO getMovieId() {
		return movieId;
	}

	public void setMovieId(MovieVO movieId) {
		this.movieId = movieId;
	}

	public CinemaVO getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(CinemaVO cinemaId) {
		this.cinemaId = cinemaId;
	}
	
	@Override
	public String toString() {
		return "TicketListVO [movieTicketId=" + movieTicketId + ", movieOrderId=" + movieOrderId + ", movieId="
				+ movieId + ", ticketTypesId=" + ticketTypesId + ", cinemaId=" + cinemaId + ", showTimesId="
				+ showTimesId + ", seatNumber=" + seatNumber + ", ticketStatus=" + ticketStatus
				+ "]";
	}
	
}
