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
