package com.product.model;

import java.util.List;
import java.util.Map;

import com.product_detail.model.ProductDetailVO;
import com.product_img.model.ProductImgVO;

public interface ProductDAO_interface {
	// p.187 介面裡面宣告的方法，預設由編譯器自動加入以下兩個修飾關鍵字: public abstract
	// return (Success ? prodId : null)
	Integer insert(ProductVO prodVO);
	// return (Success ? 1 : null)
	Integer update(ProductVO prodVO);
	// return (Success ? 1 : null)
	Integer delete(Integer prodId);
	
	// Query
	ProductVO findByPK(Integer prodId);
	List<ProductVO> getAll();
	// CompositeQuery 
	List<ProductVO> CompositeQuery(Map<String, String> map);

	// Get details from product // 不太想做，要設 Fetch, 應用場景不明
//	List<ProductDetailVO> getDetailsByProdId(Integer prodId);
	
	// Get imgs from product
	List<ProductImgVO> getImgsByProdId(Integer prodId);

}
