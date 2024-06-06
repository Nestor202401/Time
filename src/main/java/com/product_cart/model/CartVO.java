package com.product_cart.model;

public class CartVO {

	private Integer memberId;
	private Integer prodId;
	private String  prodName;
	private Integer unitPrice;
	private Integer prodCount;
	private Integer prodSum;
	
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getProdId() {
		return prodId;
	}
	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public Integer getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getProdCount() {
		return prodCount;
	}
	public void setProdCount(Integer prodCount) {
		this.prodCount = prodCount;
	}
	public Integer getProdSum() {
		return prodSum;
	}
	public void setProdSum(Integer prodSum) {
		this.prodSum = prodSum;
	}
	
	@Override
	public String toString() {
		return "cartVO [memberId=" + memberId + ", prodId=" + prodId + ", prodName=" + prodName + ", unitPrice="
				+ unitPrice + ", prodCount=" + prodCount + ", prodSum=" + prodSum + "]";
	}
	
}
