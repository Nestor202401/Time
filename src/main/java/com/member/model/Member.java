package com.member.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


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
	
	@Column(name="member_register_datetime",updatable = false)
	
	private Date memberRegisterDatetime;
	@Column(name="member_img",updatable = false)
	
	private byte[] memberImg;
	@Column(name="is_admin" ,updatable = false)
	private boolean isAdmin;
	


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
	public Date getMemberRegisterDatetime() {
		return memberRegisterDatetime;
	}
	public void setMemberRegisterDatetime(Date memberRegisterDatetime) {
		this.memberRegisterDatetime = memberRegisterDatetime;
	}
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