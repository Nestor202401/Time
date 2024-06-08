package com.tictypes.model;

//以下使用"JPA套件"而不用"hibernate套件"原因為日後移植方便
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity  //標示此類別為一個永續類別
@Table(name = "ticket_types")  //標示此永續類別對應到的資料庫表格為何 (若類別名與表格名相同可省略，建議還是加上，大小寫也建議一致)
public class TicketTypesVO implements java.io.Serializable{	//ticket_types票種
	
	@Id  //標示此屬性為表格內的Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //讓hibernate知道此欄位為自增主鍵
	@Column(name = "ticket_types_id", updatable = false)  	//註解同 "@Table"
	private Integer ticketTypesId;		//票種ID
	
	@Column(name = "ticket_type_name")  //註解同 "@Table"
	private String ticketTypeName;		//票種名稱
	
	@Column(name = "ticket_price")  	//註解同 "@Table"
	private Integer ticketPrice;		//票價
	
	@Transient
	private int quantity;
	
	public TicketTypesVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TicketTypesVO(Integer ticketTypesId, String ticketTypeName, Integer ticketPrice) {
		super();
		this.ticketTypesId = ticketTypesId;
		this.ticketTypeName = ticketTypeName;
		this.ticketPrice = ticketPrice;
	}
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
	public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
	@Override
	public String toString() {
		return "TicketTypesVO [ticketTypesId=" + ticketTypesId + ", ticketTypeName=" + ticketTypeName + ", ticketPrice="
				+ ticketPrice + "]";
	}
	
}
