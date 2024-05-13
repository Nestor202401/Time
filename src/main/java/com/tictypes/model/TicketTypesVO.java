package com.tictypes.model;

public class TicketTypesVO implements java.io.Serializable{
	private Integer ticketTypesId;
	private String ticketTypeName;
	private Integer ticketPrice;
	
	public Integer getTicketTypesId() {
		return ticketTypesId;
	}
	public void setTicketTypesId(Integer ticketTypesId) {
		this.ticketTypesId = ticketTypesId;
	}
	public String getTicketTypeName() {
		return ticketTypeName;
	}
	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}
	
	public Integer getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(Integer ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
}
