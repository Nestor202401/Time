package com.product_img.model;

import java.util.List;

import com.product.model.ProductDAO;
import com.product.model.ProductDAO_interface;
import com.product.model.ProductVO;


public class TestProdImgHibernate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 0. Declare DAO obj
		ProductImgDAO_interface imgDao = new ProductImgDAO();
		ProductDAO_interface prodDao = new ProductDAO();
		
		// 1. Insert 2 products
		ProductImgVO img1 = new ProductImgVO(
			null,	// Integer prodImgId
			prodDao.findByPK(13000001),	// ProductVO prod
			"./prod_img/功夫熊猫資料夾_11.jpg",	// String imgSrc
			"功夫熊猫資料夾_11"	// String imgName
		);
		Integer imgId1 = imgDao.insert(img1);
		System.out.println("imgId1: " + imgId1);
		
		img1.setImgSrc("./prod_img/功夫熊猫資料夾_12.jpg");
		img1.setImgName("功夫熊猫資料夾_12");
		Integer imgId2 = imgDao.insert(img1);
		System.out.println("imgId2: " + imgId2);
		
		// 2. Delete 1st insert product
		System.out.println(imgDao.delete(imgId1) == 1 ? "Delete success" : "Fail delete");
		
		// 3. Update 2nd insert product
		img1.setProdImgId(imgId2); // Add PK for update()
		img1.setImgSrc("./prod_img/FoodPanda資料夾_12.jpg");
		img1.setImgName("FoodPanda資料夾_12");
		System.out.println(imgDao.update(img1) == 1 ? "Updeta success" : "Fail update");
		
		// Query
		// 4. Find by PK 14000001
		Integer pk = 14000001;
		System.out.println("Find by PK: " + pk);
		printHead();
		System.out.println(imgDao.findByPK(pk));
		
		// 5. GetAll
		listAllProdImg(imgDao);

	}
	private static void printHead() {
		System.out.println(
			"ProductImg:\n"
			+ " prodImgId | prodId | prodName | imgName | imgSrc "
		);
		for (int i = 0; i < 75; i++)
			System.out.print("-");
		System.out.println();
	}
	
	private static void listAllProdImg(ProductImgDAO_interface dao) {
		printHead();
		List<ProductImgVO> prodImgList = dao.getAll();
		for (ProductImgVO prodImgVO : prodImgList) {
			System.out.println(prodImgVO);
		}
		System.out.println();
	}

}
