package com.product_order.model;
// Replace ProductOrder.java
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;	// 一方修改 Member
import javax.persistence.ManyToOne;	// 一方修改 Member
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import com.member.model.Member;	// 一方修改 Member
import com.member.model.MemberVO;	// 一方修改 Member // fix - 01
import com.product_detail.model.ProductDetailVO;

/* VO: Value Object || DTO: Data Transfer Object */


/* Check how sql.Timestamp work
 * import java.sql.Timestamp;
 * test = Timestamp.valueOf("2024-4-16 08:50:50"); test ==> 2024-04-16 08:50:50.0
 * test.toString(); $11 ==> "2024-04-16 08:50:50.0"
 * jshell> test.getTime(); $12 ==> 1713228650000
 * */
@Entity
@Table(name="product_order")
public class ProductOrderVO implements Serializable {
	/* Instance Variable */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="prod_ord_id")
	private Integer prodOrdId;	// 商品訂單ID (PK)
	
	// -- 一方修改 Member -----------
//	@Column(name="member_id")
//	private Integer memberId;	// 會員ID > 應為會員多方，與 Tony 討論
	@ManyToOne	// product_order/member_id | member/member_id
	@JoinColumn(name = "member_id", referencedColumnName = "member_id")
//	private Member member;
	private MemberVO member; // fix - 02
	// -- 一方修改 Member -----------
	
	@Column(name="est_time")
	private Timestamp estTime;	/* 訂單成立時間 */
	
	@Column(name="ord_status")
	private Integer ordStatus;	/* 訂單狀態 */

	@Column(name="total")
	private Integer total;		/* 總額 */
	
	@Column(name="recipient")
	private String recipient;	/* 收件人姓名 */
	
	@Column(name="rec_addr")
	private String recAddr;	/* 收件人地址 */
	
	// For 1-To-*, * class must have var: prodOrdVO, CascadeType=All, fetch 直接拿，不要等 / ref p.116
	// 因為在 detail 刪除時，會關聯到 order, 造成 detail 無法刪除。改回 fetch lazy, 同時修改 ProductOrderDAO/getDetailsByOrderId() 以 Empty-for 做為 solution 
//	@OneToMany(mappedBy = "prodOrdVO", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OneToMany(mappedBy = "prodOrdVO", cascade = CascadeType.ALL)
	private List<ProductDetailVO> prodDetails;
	// fetch 為 getDetailsByOrderId() 設定
	
	/* Constructor */
	public ProductOrderVO() {}
	
//	public ProductOrderVO(Integer prodOrdId, Integer memberId, Timestamp estTime, Integer ordStatus, Integer total, String recipient, String recAddr, List<ProductDetailVO> prodDetails) {
//	public ProductOrderVO(Integer prodOrdId, Member member, Timestamp estTime, Integer ordStatus, Integer total, String recipient, String recAddr, List<ProductDetailVO> prodDetails) {
	public ProductOrderVO(Integer prodOrdId, MemberVO member, Timestamp estTime, Integer ordStatus, Integer total, String recipient, String recAddr, List<ProductDetailVO> prodDetails) { // fix - 03
		this.prodOrdId = prodOrdId;
		// Modify One's variable // 一方修改 Member
//		this.memberId = memberId;
		this.member = member;
		
		this.estTime = estTime;
		this.ordStatus = ordStatus;
		this.total = total;
		this.recipient = recipient;
		this.recAddr = recAddr;
		// Add Many's variable
		this.prodDetails = prodDetails;
	}
	
	/* Method */
	public Integer getProdOrdId() {
		return prodOrdId;
	}
	public void setProdOrdId(Integer prodOrdId) {
		this.prodOrdId = prodOrdId;
	}
	// 一方修改 Member
//	public Integer getMemberId() { 
//		return memberId;
//	}
//	public void setMemberId(Integer memberId) { 
//		this.memberId = memberId;
//	}
//	public Member getMember() {
	public MemberVO getMember() { // fix - 04
		return member;
	}
//	public void setMember(Member member) {
	public void setMember(MemberVO member) { // fix - 05
		this.member = member;
	}
	// 一方修改 Member
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
	
	// Add Many's methods
	public List<ProductDetailVO> getProdDetails () {
		return prodDetails;
	}
	public void setProdDetails(List<ProductDetailVO> prodDetails) {
		this.prodDetails = prodDetails;
	}
	
	// toString() not add Many's info now(05/21)
	@Override
	public String toString() {
		String result = String.format(
//			"%9d |%9d |%10tF %tT |%10d |%6d |%8s |%10s ", 
//			prodOrdId, memberId, estTime, estTime, ordStatus, total, recipient, recAddr
			"%9d |%7s |%10tF %tT |%10d |%6d |%8s |%10s ", 
			prodOrdId, member.getMemberName(), estTime, estTime, ordStatus, total, recipient, recAddr
		);
		return result;
	}
}
