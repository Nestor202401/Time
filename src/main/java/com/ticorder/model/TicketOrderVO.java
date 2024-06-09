package com.ticorder.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.member.model.*;
import com.ticlist.model.*;


@Entity
@Table(name = "movie_ticket_order")
public class TicketOrderVO implements java.io.Serializable{	//movie_ticket_order電影訂單
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "movie_order_id", updatable = false)
	private Integer movieOrderId; 		//電影訂單ID
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id", referencedColumnName = "member_id")
	private MemberVO memberId;			//會員ID
	
	@Column(name = "movie_order_status")
	private Boolean movieOrderStatus;	//電影訂單狀態 false:未完成 true:已完成
	
	@Column(name = "buy_tickets_date", updatable = false)
	private Timestamp buyTicketsDate;	//購票時間
	
	@Column(name = "movie_order_total")
	private Integer movieOrderTotal;	//總金額
	
	@OneToMany(mappedBy = "movieOrderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<TicketListVO> ticketLists; // 電影票明細清單
	
	public TicketOrderVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TicketOrderVO(Integer movieOrderId, MemberVO memberId, Boolean movieOrderStatus, Timestamp buyTicketsDate,
			Integer movieOrderTotal) {
		super();
		this.movieOrderId = movieOrderId;
		this.memberId = memberId;
		this.movieOrderStatus = movieOrderStatus;
		this.buyTicketsDate = buyTicketsDate;
		this.movieOrderTotal = movieOrderTotal;
	}

	public Integer getMovieOrderId() {
		return movieOrderId;
	}
	public void setMovieOrderId(Integer movieOrderId) {
		this.movieOrderId = movieOrderId;
	}
	public MemberVO getMemberId() {
		return memberId;
	}
	public void setMemberId(MemberVO memberId) {
		this.memberId = memberId;
	}
	public Boolean getMovieOrderStatus() {
		return movieOrderStatus;
	}
	public void setMovieOrderStatus(Boolean movieOrderStatus) {
		this.movieOrderStatus = movieOrderStatus;
	}
	public Timestamp getBuyTicketsDate() {
		return buyTicketsDate;
	}
	public void setBuyTicketsDate(Timestamp buyTicketsDate) {
		this.buyTicketsDate = buyTicketsDate;
	}
	public Integer getMovieOrderTotal() {
		return movieOrderTotal;
	}
	public void setMovieOrderTotal(Integer movieOrderTotal) {
		this.movieOrderTotal = movieOrderTotal;
	}
	public String getMovieOrderStatusText() {
	    return movieOrderStatus ? "已完成" : "未完成";
	}
	public List<TicketListVO> getTicketLists() {
		return ticketLists;
	}

	public void setTicketLists(List<TicketListVO> ticketLists) {
		this.ticketLists = ticketLists;
	}
	
	@Override
	public String toString() {
		return "TicketOrderVO [movieOrderId=" + movieOrderId + ", memberId=" + memberId + ", movieOrderStatus="
				+ movieOrderStatus + ", buyTicketsDate=" + buyTicketsDate + ", movieOrderTotal=" + movieOrderTotal
				+ ", ticketLists=" + ticketLists + "]";
	}
	
}
