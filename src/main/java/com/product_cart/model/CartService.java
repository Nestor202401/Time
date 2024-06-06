package com.product_cart.model;

import java.util.List;

public class CartService {
	private CartDAO_interface cartDao;
	
	public CartService() {
		cartDao = new CartDAORedis();
	}
	
	// Add an item to cart | Return key
	public String addToCart(CartVO cartVO) {
		return cartDao.addToCart(cartVO);
	} // END of Add an item to cart | Return key
	
	// Get a member's cart | Return Cart with items
	public List<CartVO> getCart(Integer memberId) {
		return cartDao.getCart(memberId);
	} // END of Get a member's cart | Return Cart with items
	
	// Delete an item from cart | Return Key
	public String deleteOneItem(CartVO cartVO) {
		return cartDao.deleteOneItem(cartVO);
	}
	
	// Delete cart | return Integer 1(success) / -1(failure)
	public Integer clearCart(Integer memberId) {
		return cartDao.clearCart(memberId);
	}
}
