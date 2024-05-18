package com.product_order.model;

import java.sql.Timestamp;
import java.util.List;
import com.product_detail.model.*;

public class ProductOrderService {
	
	private ProductOrderDAO ordDao;
	
	// <Constructor>: Call an implement obj when new
	public ProductOrderService() {
		ordDao = new ProductOrderDAOImpl();
	}
	
	// 增
	// addOrder overload(多載)
	public ProductOrder addOrder(ProductOrder prodOrd) {
		ordDao.add(prodOrd);
		return prodOrd;
	}
	public ProductOrder addOrder(Integer memId, Timestamp estTime,
			Integer ordStatus, Integer total, String recipient, String recAdd) {
		var prodOrd = new ProductOrder(
			null, memId, estTime, ordStatus, total, recipient, recAdd);
		
		ordDao.add(prodOrd);
		return prodOrd;
	}
	
	// 改
	// updateOrder overload(多載)
	public ProductOrder updateOrder(ProductOrder prodOrd) {
		ordDao.update(prodOrd);
		return prodOrd;
	}
	public ProductOrder updateOrder(Integer prodOrdId, Integer memId, Timestamp estTime,
			Integer ordStatus, Integer total, String recipient, String recAdd) {
		var prodOrd = new ProductOrder(prodOrdId, memId, estTime,
			ordStatus, total, recipient, recAdd); 
		
		ordDao.update(prodOrd);
		return prodOrd;
	}
	
	// 刪
	public void deleteOrder(Integer prodOrdId) {
		ordDao.delete(prodOrdId);
	}
	
	// 查
	public ProductOrder getOneOrder(Integer prodOrdId) {
		return ordDao.findByPK(prodOrdId);
	}
	// 查(All)
	public List<ProductOrder> getAll() {
		return ordDao.getAll();
	}
}
