package com.product_cart.controller;

import java.util.List;

import com.product_cart.model.CartDAORedis;
import com.product_cart.model.CartDAO_interface;
import com.product_cart.model.CartVO;

import redis.clients.jedis.Jedis;

public class TestJedis {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CartDAO_interface cartDao = new CartDAORedis(); 
		
		Jedis jedis = new Jedis("localhost", 6379);
//		System.out.println(jedis.ping());
		// Switch to DB-10
		jedis.select(10);
		
		Integer memberId = 5, prodId = 3; // Fake Data
		
		// Add Test
		CartVO cartVO = new CartVO();
		cartVO.setMemberId(memberId);
		cartVO.setProdId(prodId);
		cartVO.setProdName("CartTestProd3");
		cartVO.setUnitPrice(1000);
		cartVO.setProdCount(3);
		cartVO.setProdSum(1000*3);
		String cartKey = cartDao.addToCart(cartVO);
		
		// Cart 2: Same member, different product
		memberId = 5; // 
		prodId = 4; // Fake Data
		
		// Add Test
		cartVO = new CartVO();
		cartVO.setMemberId(memberId);
		cartVO.setProdId(prodId);
		cartVO.setProdName("CartTestProd4");
		cartVO.setUnitPrice(1000);
		cartVO.setProdCount(4);
		cartVO.setProdSum(1000*4);
		cartDao.addToCart(cartVO);
		
		// Cart 3: Different member, different product
		memberId = 1; // 
		prodId = 1; // Fake Data
		
		// Add Test
		cartVO = new CartVO();
		cartVO.setMemberId(memberId);
		cartVO.setProdId(prodId);
		cartVO.setProdName("CartTestProd1");
		cartVO.setUnitPrice(1000);
		cartVO.setProdCount(1);
		cartVO.setProdSum(1000*1);
		cartDao.addToCart(cartVO);
//
//		// Check cartKey in DB
//		System.out.println("Add to DB");
//		cartDao.showItem(cartVO);
//		
//		// Update Test
//		cartVO.setProdCount(3);
//		cartVO.setProdSum(3*1000);
//		cartDao.updateToCart(cartVO);
//		
//		// Check cartKey in DB
//		System.out.println("Update to DB");
//		cartDao.showItem(cartVO);
//		
//		// GetOneItem in DB
//		CartVO testGetOneCart = cartDao.getOneItem(memberId, prodId);
//
//		System.out.println("Get item from DB");
//		cartDao.showItem(testGetOneCart);
//		
//		System.out.println("Get item from DB(not exists)");
//		CartVO testGetNon = cartDao.getOneItem(1, 1);
//		cartDao.showItem(testGetNon);

		// Clear carts by member
//		cartDao.clearCart(5);
		
		// show cart
//		CartVO checkCart = cartDao.getOneItem(5, 3);
//		cartDao.showItem(checkCart);
		
		List<CartVO> cartList = cartDao.getCart(5);
		for (CartVO c : cartList) {
			cartDao.showItem(c);
		}
		
		cartList = cartDao.getCart(1);
		for (CartVO c : cartList) {
			cartDao.showItem(c);
		}
		
		cartList = cartDao.getCart(3);
		for (CartVO c : cartList) {
			cartDao.showItem(c);
		}
		
		
		jedis.close();

	}

}
