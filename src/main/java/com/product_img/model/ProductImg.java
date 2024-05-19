package com.product_img.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_img")
public class ProductImg {
	// Instance Variable
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prod_img_id")
	private Integer prodImgId;	// 圖片ID
	
	@Column(name = "prod_id")
	private Integer prodId;	// 商品ID
	
	@Column(name = "img_src")
	private String imgSrc;	// 圖片路徑
	
	@Column(name = "img_name")
	private String imgName;	// 圖片名稱
	
	// Constructor
	public ProductImg() {}

	public ProductImg(Integer prodImgId, Integer prodId, String imgSrc, String imgName) {
		this.prodImgId = prodImgId;
		this.prodId = prodId;
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

	public Integer getProdId() {
		return prodId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}

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
		return String.format("%12d |%9d |%9s |%11s", prodImgId, prodId, imgName, imgSrc);
	}

}
