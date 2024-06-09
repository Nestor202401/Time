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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "movie_order_id", referencedColumnName = "movie_order_id", insertable = false, updatable = false)
	private TicketOrderVO TicketOrderVO;	 //電影訂單ID
	
	@Column(name = "movie_order_id")
	private Integer movieOrderId;	 //電影訂單ID
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "movie_id", referencedColumnName = "movie_id", insertable = false, updatable = false)
	private MovieVO MovieVO;	 //電影ID
	
	@Column(name = "movie_id")
	private Integer movieId;	 //電影ID
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ticket_types_id", referencedColumnName = "ticket_types_id", insertable = false, updatable = false)
	private TicketTypesVO TicketTypesVO; //票種ID
	
	@Column(name = "ticket_types_id")
	private Integer ticketTypesId; //票種ID
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "show_times_id", referencedColumnName = "show_times_id", insertable = false, updatable = false)
	private MovieTimeVO MovieTimeVO;		//場次ID
	
	@Column(name = "show_times_id")
	private Integer showTimesId;		//場次ID
	
	@Column(name = "seat_number")
	private String seatNumber;			//座位編號
	
	@Column(name = "ticket_status")
	private Boolean ticketStatus;		//電影票使用狀態 false:未使用 true:已使用
	
	
	public TicketListVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	private TicketListVO(Integer movieTicketId, Integer movieOrderId,Integer movieId,Integer ticketTypesId, Integer showTimesId, String seatNumber,
			Boolean ticketStatus) {
		super();
		this.movieTicketId = movieTicketId;
		this.movieOrderId = movieOrderId;
		this.movieId = movieId;
		this.ticketTypesId = ticketTypesId;
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

	public Integer getMovieOrderId() {
		return movieOrderId;
	}
	public void setMovieOrderId(Integer movieOrderId) {
		this.movieOrderId = movieOrderId;
	}

	public Integer getMovieId() {
		return movieId;
	}
	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public Integer getTicketTypesId() {
		return ticketTypesId;
	}
	public void setTicketTypesId(Integer ticketTypesId) {
		this.ticketTypesId = ticketTypesId;
	}


	public Integer getShowTimesId() {
		return showTimesId;
	}
	public void setShowTimesId(Integer showTimesId) {
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



	@Override
	public String toString() {
		return "TicketListVO [movieTicketId=" + movieTicketId + ", movieOrderId=" + movieOrderId 
				+ ", movieId=" + movieId + ", ticketTypesId=" + ticketTypesId + ", showTimesId=" 
				+ showTimesId + ", seatNumber=" + seatNumber + ", ticketStatus=" + ticketStatus + "]";
	}
	
}
