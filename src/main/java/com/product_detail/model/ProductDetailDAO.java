package com.product_detail.model;

import java.util.List;

public interface ProductDetailDAO {
	void add(ProductDetail prodDetail);
	void update(ProductDetail prodDetail);
	void delete(Integer prodDetailId); // Not use
	ProductDetail findByPK(Integer prodDetailId);
	List<ProductDetail> getAll();
	
	// Universal query
//	public List<ProductDetail> getAll(Map<String, String[]> map);
}
