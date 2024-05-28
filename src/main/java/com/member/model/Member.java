package com.member.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;	// 多方新增 ProductOrderVO

import javax.persistence.CascadeType;	// 多方新增 ProductOrderVO
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;	// 多方新增 ProductOrderVO
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.product_order.model.ProductOrderVO;	// 多方新增 ProductOrderVO


@Entity
@Table(name="member")
public class Member implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id", updatable = false)
	private Integer memberId;
	
	@Column(name="member_account")
	private String memberAccount;
	@Column(name="member_password")
	private String memberPassword;


	@Column(name="member_name")
	private String memberName;
	
	@Column(name="member_phone")
	private String memberPhone;
	
	@Column(name="member_email")
	private String memberEmail;

// p.56 columnDefinition:定義該欄位於資料庫的型別:
// ProductOredr 設定時報錯
//	@Column(name="member_register_datetime",updatable = false) // bk
	//Caused by: org.hibernate.tool.schema.spi.SchemaManagementException: Schema-validation: wrong column type encountered in column [member_register_datetime] in table [member]; found [datetime (Types#TIMESTAMP)], but expecting [date (Types#DATE)]
	// 用 columnDefinition = "DATETIME" 修正
//	@Column(name="member_register_datetime",updatable = false, columnDefinition = "DATETIME")
//	private Date memberRegisterDatetime;
	
	//---
	@Column(name = "member_register_datetime", updatable = false)
	@CreationTimestamp
	private LocalDateTime  memberRegisterDatetime;
	
	public LocalDateTime getMemberRegisterDatetime() {
		return memberRegisterDatetime;
	}

	public void setMemberRegisterDatetime(LocalDateTime memberRegisterDatetime) {
		this.memberRegisterDatetime = memberRegisterDatetime;
	}
	//---
	
// ProductOredr 設定時報錯
//	@Column(name="member_img",updatable = false) // bk
	// Caused by: org.hibernate.tool.schema.spi.SchemaManagementException: Schema-validation: wrong column type encountered in column [member_img] in table [member]; found [longblob (Types#LONGVARBINARY)], but expecting [tinyblob (Types#VARBINARY)]
	// 用 columnDefinition = "LONGBLOB" 修正
	@Column(name="member_img",updatable = false, columnDefinition = "LONGBLOB")
	private byte[] memberImg;
	
	@Column(name="is_admin" ,updatable = false)
	private boolean isAdmin;
	
	// --- ProductOrder by coi618 ---
	// 新增變數: Set of prodOrders | productOrderVO 要有一個 member 變數 | CascadeType.ALL: 所有操作均讓關聯的物件跟著影響
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private Set<ProductOrderVO> prodOrders;

	// Set/Get
	public Set<ProductOrderVO> getProdOrders() {
		return prodOrders;
	}

	public void setProdOrders(Set<ProductOrderVO> prodOrders) {
		this.prodOrders = prodOrders;
	}
	// --- ProductOrder by coi618 ---

	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Member(String memberAccount, String memberPassword, String memberName, String memberPhone,
			String memberEmail) {
		super();
		this.memberAccount = memberAccount;
		this.memberPassword = memberPassword;
		this.memberName = memberName;
		this.memberPhone = memberPhone;
		this.memberEmail = memberEmail;
	}


	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getMemberAccount() {
		return memberAccount;
	}
	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}
	public String getMemberPassword() {
		return memberPassword;
	}
	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	// ---
//	public Date getMemberRegisterDatetime() {
//		return memberRegisterDatetime;
//	}
//	public void setMemberRegisterDatetime(Date memberRegisterDatetime) {
//		this.memberRegisterDatetime = memberRegisterDatetime;
//	}
	//---
	public byte[] getMemberImg() {
		return memberImg;
	}
	public void setMemberImg(byte[] member_img) {
		this.memberImg = member_img;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean is_admin) {
		this.isAdmin = is_admin;
	}
	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", memberAccount=" + memberAccount + ", memberPassword="
				+ memberPassword + ", memberName=" + memberName + ", memberPhone=" + memberPhone + ", memberEmail="
				+ memberEmail + ", memberRegisterDatetime=" + memberRegisterDatetime + ", member_img="
				+ Arrays.toString(memberImg) + ", is_admin=" + isAdmin + "]";
	}

}