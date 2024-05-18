package com.product_order.model;

import java.util.List;
//import java.lang.Optional; /* About NULL, How to ? */

/* DAO: Data Access Object */

public interface ProductOrderDAO {
	void add(ProductOrder prodOrd);
	void update(ProductOrder prodOrd);
	void delete(int prodOrdId);
	ProductOrder findByPK(int prodOrdId);
	List<ProductOrder> getAll();
}
