package com.product_img.model;
import java.io.Serializable;

// Replace ProductImg.java
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.product.model.ProductVO;

@Entity
@Table(name="product_img")
public class ProductImgVO implements Serializable {
	// Instance Variable
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prod_img_id")
	private Integer prodImgId;	// 圖片ID
	
//	@Column(name = "prod_id")
//	private Integer prodId;	// 商品ID
	// Replace by One's configure
	@ManyToOne
	@JoinColumn(name = "prod_id", referencedColumnName = "prod_id")
	private ProductVO prodVO;
	
	@Column(name = "img_src")
	private String imgSrc;	// 圖片路徑
	
	@Column(name = "img_name")
	private String imgName;	// 圖片名稱
	
	// Constructor
	public ProductImgVO() {}

//	public ProductImgVO(Integer prodImgId, Integer prodId, String imgSrc, String imgName) {
//	Replace with One(product)'s configure
	public ProductImgVO(Integer prodImgId, ProductVO prodVO, String imgSrc, String imgName) {
		this.prodImgId = prodImgId;
		this.prodVO = prodVO;
		this.imgSrc = imgSrc;
		this.imgName = imgName;
	}

	// Set/Get Method
	public Integer getProdImgId() {
		return prodImgId;
	}

	public void setProdImgId(Integer prodImgId) {
		this.prodImgId = prodImgId;
	}

//	public Integer getProdId() {
//		return prodId;
//	}
//
//	public void setProdId(Integer prodId) {
//		this.prodId = prodId;
//	}
//	Replace by Many(ProductVO)
	public ProductVO getProdVO() {
		return prodVO;
	}
	public void setProdVO(ProductVO prodVO) {
		this.prodVO = prodVO;
	}
//	END of Replace by Many(ProductVO)
	
	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	@Override
	public String toString() {
		int size = 9;
//		return String.format("%12d |%9d |%9s |%11s", prodImgId, prodId, imgName, imgSrc);
//		Replace with Many(ProductVO)		
//		return String.format("%12d |%9d |%9s |%11s", prodImgId, prod.getProdId(), imgName, imgSrc);
		return String.format(
			"%12d |%9d |%9s |%9s |%11s", 
			prodImgId, prodVO.getProdId(), 
			(prodVO.getProdName().length() <= size ? prodVO.getProdName() : prodVO.getProdName().substring(0, size)), 
			imgName, imgSrc
		);
	}

}
