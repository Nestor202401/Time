package com.product_img.model;

import java.util.List;

import com.product.model.ProductVO;

public class ProductImgService {
	
	// Declare DAO variable, should use interface for switch different DAO implements
	private ProductImgDAO_interface imgDao;
	// <Constructor>: Call an implement obj when new
	public ProductImgService() {
		// Should able switch to different DAO implements: JDBC/JNDI/Hibernate etc
		imgDao = new ProductImgDAO(); // Hibernate version
	}

	// addImg overload(VO/(...))
	public ProductImgVO addImg(ProductImgVO prodImgVO) {
		imgDao.insert(prodImgVO);
		return prodImgVO;
	}
	public ProductImgVO addImg(ProductVO prodVO, String imgSrc, String imgName) {
		var prodImgVO = new ProductImgVO(null, prodVO, imgSrc, imgName);
		imgDao.insert(prodImgVO);
		return prodImgVO;
	}
	
	// updateImg overload(VO/(...))
	public ProductImgVO updateImg(ProductImgVO prodImgVO) {
		imgDao.update(prodImgVO);
		return prodImgVO;
	}
	public ProductImgVO updateImg(Integer prodImgId, ProductVO prodVO, String imgSrc, String imgName) {
		var prodImgVO = new ProductImgVO(prodImgId, prodVO, imgSrc, imgName);
		imgDao.update(prodImgVO);
		return prodImgVO;
	}
	
	// deleteImg
	public void deleteImg(Integer prodImgId) {
		imgDao.delete(prodImgId);
	}
	
	// Query
	// findByPK
	public ProductImgVO getOneImg(Integer prodImgId) {
		return imgDao.findByPK(prodImgId);
	}
	// GetAll
	public List<ProductImgVO> getAll() {
		return imgDao.getAll();
	}
}
