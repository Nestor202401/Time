package com.product.model;
// Replace Product.java
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.product_detail.model.ProductDetailVO;
import com.product_img.model.ProductImgVO;



@Entity
@Table(name = "product")
public class ProductVO implements Serializable {
	// Instance Variable 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="prod_id")
	private Integer prodId;	// 商品ID (PK)
	
	@Column(name="prod_name")
	private String prodName;	// 商品名稱
	
	@Column(name="prod_intro")
	private String prodIntro;	// 商品介紹
	
	@Column(name="prod_price")
	private Integer prodPrice;	// 商品價格
	
	@Column(name="release_date")
	private Date releaseDate;	// 上架日期
	
	@Column(name="remove_date")
	private Date removeDate;	// 下架日期
	
	@Column(name="sales_status")
	private Integer salesStatus;	// 銷售狀態, 0:銷售中, 1:停售
	
	@Column(name="time_limit_prod")
	private Boolean timeLimitProd;	// 限時商品
	
	// For 1-To-*(detail), ProductDetailVO.java must have var: prodVO, CascadeType=All
	@OneToMany(mappedBy = "prodVO", cascade = CascadeType.ALL)
	private List<ProductDetailVO> prodDetails;
	
	// For 1-To-*(img), ProductImgVO.java must have var: prod, CascadeType=All fetch = FetchType.EAGER
//	@OneToMany(mappedBy = "prodVO", cascade = CascadeType.ALL)
	@OneToMany(mappedBy = "prodVO", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ProductImgVO> prodImgs;
	
	// Constructor 
	public ProductVO() {}
	
//	public ProductVO(Integer prodId, String prodName, String prodIntro, Integer prodPrice, Date releaseDate, Date removeDate, Integer salesStatus, Boolean timeLimitProd) {
	// Add Many(detail)'s variable 
	public ProductVO(Integer prodId, String prodName, String prodIntro, Integer prodPrice, 
			Date releaseDate, Date removeDate, Integer salesStatus, Boolean timeLimitProd, 
			List<ProductDetailVO> prodDetails, List<ProductImgVO> prodImgs) {
		this.prodId = prodId;
		this.prodName = prodName;
		this.prodIntro = prodIntro;
		this.prodPrice = prodPrice;
		this.releaseDate = releaseDate;
		this.removeDate = removeDate;
		this.salesStatus = salesStatus;
		this.timeLimitProd = timeLimitProd;
		// Add Many's variable
		this.prodDetails = prodDetails;
		this.prodImgs = prodImgs;
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

	// Add Many's methods (detail)
	public List<ProductDetailVO> getProdDetails() {
		return prodDetails;
	}
	public void setProdDetails(List<ProductDetailVO> prodDetails) {
		this.prodDetails = prodDetails;
	}
	// Many's methods (Img)
	public List<ProductImgVO> getProdImgs() {
		return prodImgs;
	}
	public void setProdImgs(List<ProductImgVO> prodImgs) {
		this.prodImgs = prodImgs;
	}

	@Override
	public String toString() {
		int size = 9;
		
		return String.format("%9d |%10d |%12tF |%11tF |%12d |%14b | %7s | %s", 
			prodId, prodPrice, releaseDate, removeDate, salesStatus, timeLimitProd,  
			(prodName.length()  <= size ? prodName : prodName.substring(0, size)), 
			prodIntro
		);
	}
}
