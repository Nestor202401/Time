package com.product_order.model;

import java.util.List;
import java.util.Map;

import com.product_detail.model.ProductDetailVO;

public interface ProductOrderDAO_interface {
	// p.187 介面裡面宣告的方法，預設由編譯器自動加入以下兩個修飾關鍵字: public abstract
	
	// return (Success ? prodOrdId : null)
	Integer insert(ProductOrderVO prodOrdVO);
	// return (Success ? 1 : null)
	Integer update(ProductOrderVO prodOrdVO);
	// return (Success ? 1 : null)
	Integer delete(Integer prodOrdId);
	
	// Query
	ProductOrderVO findByPK(Integer prodOrdId);
	List<ProductOrderVO> getAll();
	// CompositeQuery 
	List<ProductOrderVO> CompositeQuery(Map<String, String> map);
	// 0609: Query by MemberId
	List<ProductOrderVO> findByMember(Integer memberId);

	// Get details from order
	List<ProductDetailVO> getDetailsByOrderId(Integer prodOrdId);
}
