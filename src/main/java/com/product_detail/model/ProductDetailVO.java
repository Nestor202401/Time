package com.product_detail.model;
// Replace ProductDetail.java
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.product.model.ProductVO;
// Many(s): 
import com.product_order.model.ProductOrderVO;

@Entity
@Table(name="product_detail")
public class ProductDetailVO implements Serializable {
	// Instance Variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="prod_detail_id")
	private Integer prodDetailId;	// 明細ID (PK)

//	@Column(name="prod_ord_id")
//	private Integer prodOrdId;	// 商品訂單ID
	// Replace by One's configure: Many details in an order
	@ManyToOne
	@JoinColumn(name = "prod_ord_id", referencedColumnName = "prod_ord_id") // name is DB's PK / ref is also DB's PK 
	private ProductOrderVO prodOrdVO;
	
//	@Column(name="prod_id")
//	private Integer prodId;		// 商品ID
	// Replace by One's configure: Many details have a product
	@ManyToOne
	@JoinColumn(name = "prod_id", referencedColumnName = "prod_id") // name is DB's PK / ref is also DB's PK
	private ProductVO prodVO;
	
	@Column(name="unit_price")
	private Integer unitPrice;		// 單價
	
	@Column(name="prod_count")
	private Integer prodCount;		// 數量
	
	@Column(name="prod_sum")
	private Integer prodSum;		// 小計
	
	// Constructor
	public ProductDetailVO() {}
	
//	public ProductDetailVO(Integer prodDetailId, Integer prodOrdId, Integer prodId, Integer unitPrice, Integer prodCount, Integer prodSum) {
//	Replace with One(order, product)'s configure 
	public ProductDetailVO(Integer prodDetailId, ProductOrderVO prodOrdVO, ProductVO prodVO, 
			Integer unitPrice, Integer prodCount, Integer prodSum) {
		setProdDetailId(prodDetailId);
//		setProdOrdId(prodOrdId);
		setProdOrd(prodOrdVO);
		
//		setProdId(prodId);
		setProdVO(prodVO);
		
		setUnitPrice(unitPrice);
		setProdCount(prodCount);
		setProdSum(prodSum);
	}

	// Method 
	void setProdDetailId(Integer prodDetailId) {
		this.prodDetailId = prodDetailId;
	}

	public Integer getProdDetailId() {
		return prodDetailId;
	}

//	public Integer getProdOrdId() {
//		return prodOrdId;
//	}
//
//	public void setProdOrdId(Integer prodOrdId) {
//		this.prodOrdId = prodOrdId;
//	}
//	Replace by Many (ProductOrderVO)	
	public ProductOrderVO getProdOrd() {
		return prodOrdVO;
	}
	public void setProdOrd(ProductOrderVO prodOrdVO) {
		this.prodOrdVO = prodOrdVO;
	}
//	END of Replace by Many (ProductOrderVO)
	
//	public Integer getProdId() {
//		return prodId;
//	}
//
//	public void setProdId(Integer prodId) {
//		this.prodId = prodId;
//	}
//	Replace by Many (ProductVO)
	public ProductVO getProdVO() {
		return prodVO;
	}
	public void setProdVO(ProductVO prodVO) {
		this.prodVO = prodVO;
	}
//	END of Replace by Many (ProductVO)

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
//		String result = String.format("%14d |%12d |%9d |%11d |%11d |%9d",prodDetailId, prodOrdId, prodId, unitPrice, prodCount, prodSum);
//		Replace with Many(ProductOrderVO)
		String result = String.format("%14d |%12d |%9d |%11d |%11d |%9d",
				prodDetailId, prodOrdVO.getProdOrdId(), prodVO.getProdId(), unitPrice, prodCount, prodSum);

		return result;
	}
}
