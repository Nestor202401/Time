package com.product_img.model;

import java.util.List;

import com.product.model.ProductVO;

public interface ProductImgDAO_interface {
	// p.187 介面裡面宣告的方法，預設由編譯器自動加入以下兩個修飾關鍵字: public abstract
	// return (Success ? prodImgId : null)
	Integer insert(ProductImgVO prodImgVO);
	// return (Success ? 1 : null)
	Integer update(ProductImgVO prodImgVO);
	// return (Success ? 1 : null)
	Integer delete(Integer prodImgId);
	
	// Query
	ProductImgVO findByPK(Integer prodImgId);
	List<ProductImgVO> getAll();
}
