package com.product_detail.model;

import java.util.List;

import com.product.model.ProductDAO;
import com.product.model.ProductDAO_interface;
import com.product_order.model.ProductOrderDAO;
import com.product_order.model.ProductOrderDAO_interface;

public class TestProdDetailHibernate {

	public static void main(String[] args) {
		// Since detail use order and product, also create their DAO to get objs
		ProductDetailDAO_interface detailDao = new ProductDetailDAO();
		ProductOrderDAO_interface orderDao = new ProductOrderDAO();
		ProductDAO_interface prodDao = new ProductDAO(); 
		
		// 1. insert 2 new orders
		// Prepare VO for details
		Integer orderId1 = 11000001, orderId2 = 11000002, 
				prodId1  = 13000001,  prodId2 = 13000002;
		var orderVO1 = orderDao.findByPK(orderId1); // ProductOrderVO for detail1
		var orderVO2 = orderDao.findByPK(orderId2); // ProductOrderVO for detail2
		var prodVO1 = prodDao.findByPK(prodId1); // ProductVO for detail1
		var prodVO2 = prodDao.findByPK(prodId2); // ProductVO for detail2
		// Create detail VO
		var detail1 = new ProductDetailVO(null, orderVO1, prodVO1, 1500, 1, 1500);
		var detail2 = new ProductDetailVO(null, orderVO2, prodVO2,  900, 2, 1800);
		
		// Add test
		Integer detailId1 = detailDao.insert(detail1); 
		Integer detailId2 = detailDao.insert(detail2);
		
		// Update test
		detail1.setProdDetailId(detailId1);
		detail1.setProdCount(2);
		detail1.setProdSum(3000);
		System.out.println(detailDao.update(detail1) == 1 ? "Update success" : "Fail of update");
		
		// DELETE
		System.out.println(detailDao.delete(detailId2) == 1 ? "Delete success" : "Fail of delete");
		
		// findByPK test
		Integer key = 12000001; // 12000001 | 12000013
		System.out.println("findByPK(" + key + ") :");
		printHead();
		System.out.println(detailDao.findByPK(key));
				
		// 4-2. findAll
		listAllDetail(detailDao);
		
		System.out.println("Hibernate detail test finish.");

	}
	
	private static void printHead() {
		System.out.println(
			"Product_detail:\nprod_detail_id | prod_ord_id |  prod_id | "
			+ "unit_price | prod_count | prod_sum"
		);
		for(int i = 0; i < 76; i++)
			System.out.print("-");
		System.out.println();
	}
	
	private static void listAllDetail(ProductDetailDAO_interface dao) {
		printHead();
		List<ProductDetailVO> prodDetailList = dao.getAll();
		for (ProductDetailVO prodDetailVO : prodDetailList) {
			System.out.println(prodDetailVO);
		}
		System.out.println();
	}

}
