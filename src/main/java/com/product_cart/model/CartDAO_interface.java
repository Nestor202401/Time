package com.product_cart.model;

import java.util.List;

public interface CartDAO_interface {
	// Add an item to cart | Return key
	String addToCart(CartVO cartVO); 
	// Update an item from cart | Return Key
	String updateToCart(CartVO cartVO);
	// Delete an item from cart | Return Key
	String deleteOneItem(CartVO cartVO);
	// Delete cart | return Integer 1(success) / -1(failure)
	Integer clearCart(Integer memberId);

	List<CartVO> getCart(Integer memberId); // Get a member's cart | Return Cart with items
	CartVO getOneItem(Integer memberId, Integer prodId);
	
	// print one item
	void showItem(CartVO cartVO);
}
