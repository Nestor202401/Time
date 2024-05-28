/*
package com.product_detail.model;

import java.util.List;

public class ProductDetailService {
	
	private ProductDetailDAO detailDao;
	
	// <Constructor>: Call an implement obj when new
	public ProductDetailService() {
		detailDao = new ProductDetailDAOImpl();
	}
	
	// addDetail overload (多載)
	public ProductDetail addDetail(ProductDetail prodDetail) {
		detailDao.add(prodDetail);
		return prodDetail;
	}	
	public ProductDetail addDetail(Integer prodOrdId, Integer prodId, 
			Integer unitPrice, Integer prodCount, Integer prodSum) {
		
		ProductDetail prodDetail = new ProductDetail(
			null, prodOrdId, prodId, unitPrice, prodCount, prodSum	
		);
		
		detailDao.add(prodDetail);
		
		return prodDetail;
	}
	
	// updateDetail overload (多載)
	public ProductDetail updateDetail(ProductDetail prodDetail) {
		detailDao.update(prodDetail);
		return prodDetail;
	}
	public ProductDetail updateDetail(Integer prodDetailId, Integer prodOrdId, 
			Integer prodId,	Integer unitPrice, Integer prodCount, Integer prodSum) {
		
		ProductDetail prodDetail = new ProductDetail(
			prodDetailId, prodOrdId, prodId, unitPrice, prodCount, prodSum
		);
		
		detailDao.update(prodDetail);
		
		return prodDetail;
	}
	
	public void deleteDetail(Integer prodDetailId) {
		detailDao.delete(prodDetailId);
	}
	
	public ProductDetail getOneDetail(Integer prodDetailId) {
		return detailDao.findByPK(prodDetailId); 
	}
	
	public List<ProductDetail> getAll() {
		return detailDao.getAll();
	}

}
*/
//--- Above: 0201 Service --- | --- Below: Hibernate Service ---
package com.product_detail.model;

import java.util.List;

import com.product.model.ProductVO;
import com.product_order.model.ProductOrderVO;

public class ProductDetailService {
	
	// Declare interface, for switch different DAO implements
	private ProductDetailDAO_interface detailDao;
	
	// <Constructor>: Call an implement obj when new
	public ProductDetailService() {
		// Should able switch to different DAO implements: JDBC/JNDI/Hibernate etc
		detailDao = new ProductDetailDAO(); // Hibernate version
	}
	
	// addDetail overload (多載)
	public ProductDetailVO addDetail(ProductDetailVO prodDetailVO) {
		detailDao.insert(prodDetailVO);
		return prodDetailVO; // Why return obj? Check when write controller
	}	
//	public ProductDetailVO addDetail(Integer prodOrdId, Integer prodId, Integer unitPrice, Integer prodCount, Integer prodSum) {
	public ProductDetailVO addDetail(ProductOrderVO prodOrdVO, ProductVO prodVO, 
			Integer unitPrice, Integer prodCount, Integer prodSum) {	
		ProductDetailVO prodDetailVO = new ProductDetailVO(
			null, prodOrdVO, prodVO, unitPrice, prodCount, prodSum	
		);
		
		detailDao.insert(prodDetailVO);
		
		return prodDetailVO;
	}
	
	// updateDetail overload (多載)
	public ProductDetailVO updateDetail(ProductDetailVO prodDetailVO) {
		detailDao.update(prodDetailVO);
		return prodDetailVO;
	}
//	public ProductDetailVO updateDetail(Integer prodDetailId, Integer prodOrdId, Integer prodId, Integer unitPrice, Integer prodCount, Integer prodSum) {
	public ProductDetailVO updateDetail(Integer prodDetailId, ProductOrderVO prodOrdVO, 
			ProductVO prodVO, Integer unitPrice, Integer prodCount, Integer prodSum) {	
		ProductDetailVO prodDetailVO = new ProductDetailVO(
			prodDetailId, prodOrdVO, prodVO, unitPrice, prodCount, prodSum
		);
		
		detailDao.update(prodDetailVO);
		
		return prodDetailVO;
	}
	
	// 刪
	public void deleteDetail(Integer prodDetailId) {
		detailDao.delete(prodDetailId);
	}
	
	// 查
	public ProductDetailVO getOneDetail(Integer prodDetailId) {
		return detailDao.findByPK(prodDetailId); 
	}
	// 查 (getAll)
	public List<ProductDetailVO> getAll() {
		return detailDao.getAll();
	}

}