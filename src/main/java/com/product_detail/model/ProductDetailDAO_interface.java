package com.product_detail.model;

import java.util.List;
//import java.util.Map;

public interface ProductDetailDAO_interface {
	// p.187 介面裡面宣告的方法，預設由編譯器自動加入以下兩個修飾關鍵字: public abstract
	
	// return (Success ? prodDetailId : null)
	Integer insert(ProductDetailVO prodDetailVO);
	// return (Success ? 1 : null)
	Integer update(ProductDetailVO prodDetailVO);
	// return (Success ? 1 : null)
	Integer delete(Integer prodDetailId);
	
	// Query
	ProductDetailVO findByPK(Integer prodDetailId);
	List<ProductDetailVO> getAll();
	
	// CompositeQuery // 暫不實作
//	List<ProductDetailVO> CompositeQuery(Map<String, String> map);
}
