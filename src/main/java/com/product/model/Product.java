package com.product.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "product")
public class Product implements Serializable {
	// Instance Variable 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="prod_id")
	private Integer prodId;
	@Column(name="prod_name")
	private String prodName;
	@Column(name="prod_intro")
	private String prodIntro;
	@Column(name="prod_price")
	private Integer prodPrice;
	@Column(name="release_date")
	private Date releaseDate;
	@Column(name="remove_date")
	private Date removeDate;
	@Column(name="sales_status")
	private Integer salesStatus;
	@Column(name="time_limit_prod")
	private Boolean timeLimitProd;
	
	// Constructor 
	public Product() {}
	
	public Product(Integer prodId, String prodName, String prodIntro, Integer prodPrice, Date releaseDate,
			Date removeDate, Integer salesStatus, Boolean timeLimitProd) {
		this.prodId = prodId;
		this.prodName = prodName;
		this.prodIntro = prodIntro;
		this.prodPrice = prodPrice;
		this.releaseDate = releaseDate;
		this.removeDate = removeDate;
		this.salesStatus = salesStatus;
		this.timeLimitProd = timeLimitProd;
	}

	// Method
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

	public String getProdIntro() {
		return prodIntro;
	}

	public void setProdIntro(String prodIntro) {
		this.prodIntro = prodIntro;
	}

	public Integer getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(Integer prodPrice) {
		this.prodPrice = prodPrice;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getRemoveDate() {
		return removeDate;
	}

	public void setRemoveDate(Date removeDate) {
		this.removeDate = removeDate;
	}

	public Integer getSalesStatus() {
		return salesStatus;
	}

	public void setSalesStatus(Integer salesStatus) {
		this.salesStatus = salesStatus;
	}

	public Boolean getTimeLimitProd() {
		return timeLimitProd;
	}

	public void setTimeLimitProd(Boolean timeLimitProd) {
		this.timeLimitProd = timeLimitProd;
	}

	@Override
	public String toString() {
		int size = 9;
		
		return String.format("%9d |%11d |%13tF |%12tF |%7d |%11b | %7s | %s", 
			prodId, prodPrice, releaseDate, removeDate, salesStatus, timeLimitProd,  
			(prodName.length()  <= size ? prodName : prodName.substring(0, size)), 
			prodIntro
		);
	}
}
