package com.product_order.model;
import java.sql.Timestamp;
import java.io.Serializable;

/* VO: Value Object || DTO: Data Transfer Object */


/* Check how sql.Timestamp work
 * import java.sql.Timestamp;
 * test = Timestamp.valueOf("2024-4-16 08:50:50"); test ==> 2024-04-16 08:50:50.0
 * test.toString(); $11 ==> "2024-04-16 08:50:50.0"
 * jshell> test.getTime(); $12 ==> 1713228650000
 * */

public class ProductOrder implements Serializable {
	/* Instance Variable */
	private Integer prodOrdId;/* 商品訂單ID */
	private Integer memberId;		/* 會員ID */
	private Timestamp estTime;	/* 訂單成立時間 */
	private Integer ordStatus;	/* 訂單狀態 */
	private Integer total;		/* 總額 */
	private String recipient;	/* 收件人姓名 */
	private String recAddr;	/* 收件人地址 */
	
	/* Constructor */
	public ProductOrder() {}
	
	public ProductOrder(Integer prodOrdId, Integer memberId, Timestamp estTime, Integer ordStatus, 
			Integer total, String recipient, String recAddr) {
		this.prodOrdId = prodOrdId;
		this.memberId = memberId;
		this.estTime = estTime;
		this.ordStatus = ordStatus;
		this.total = total;
		this.recipient = recipient;
		this.recAddr = recAddr;
	}
	
	/* Method */
	public Integer getProdOrdId() {
		return prodOrdId;
	}
	public void setProdOrdId(Integer prodOrdId) {
		this.prodOrdId = prodOrdId;
	}
	public Integer getMemberId() { 
		return memberId;
	}
	public void setMemberId(Integer memberId) { 
		this.memberId = memberId;
	}
	public Timestamp getEstTime() {
		return estTime;
	}
	public void setEstTime(Timestamp estTime) {
		this.estTime = estTime;
	}
	public Integer getOrdStatus() {
		return ordStatus;
	}
	public void setOrdStatus(Integer ordStatus) {
		this.ordStatus = ordStatus;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getRecAddr() {
		return recAddr;
	}
	public void setRecAddr(String recAddr) {
		this.recAddr = recAddr;
	}
	
	@Override
	public String toString() {
		String result = String.format(
			"%9d |%9d |%10tF %tT |%10d |%6d |%8s |%10s ", 
			prodOrdId, memberId, estTime, estTime, ordStatus, total, recipient, recAddr
		);
		return result;
	}
}
