package com.product_cart.model;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;

public class CartDAORedis implements CartDAO_interface {
	private Jedis jedis;
	
	public CartDAORedis() {
		jedis = new Jedis("localhost", 6379);
		// Switch to DB-10
		jedis.select(10);
	}

	@Override
	public String addToCart(CartVO cartVO) {
		// TODO Auto-generated method stub
		String cartKey = "cart:" + cartVO.getMemberId() + ":" + cartVO.getProdId();
		jedis.hset(cartKey, "prodName", cartVO.getProdName()); 
		jedis.hset(cartKey, "unitPrice", (cartVO.getUnitPrice()).toString());  // ((Integer)1000).toString());
		jedis.hset(cartKey, "prodCount", (cartVO.getProdCount()).toString()); // ((Integer)2).toString());
		Integer prodSum = cartVO.getUnitPrice() * cartVO.getProdCount();
		jedis.hset(cartKey, "prodSum", prodSum.toString());
		return cartKey;
	} // END of addToCart(CartVO cartVO)

	@Override
	public String updateToCart(CartVO cartVO) {
		// TODO Auto-generated method stub
		return addToCart(cartVO); // 先用 addToCart()
	} // END of updateToCart(CartVO cartVO)

	@Override
	public String deleteOneItem(CartVO cartVO) {
		// TODO Auto-generated method stub
		String cartKey = "cart:" + cartVO.getMemberId() + ":" + cartVO.getProdId();
		if (jedis.exists(cartKey)) {
			jedis.del(cartKey);
			return cartKey; // 我也不知道要回傳什麼，先傳 key
		}
		return null;
	} // END of deleteOneItem(CartVO cartVO)

	@Override
	public Integer clearCart(Integer memberId) {
		// TODO Auto-generated method stub
		for (String cartKey : jedis.keys("cart:" + memberId + ":*")) {
			// keys("cart:Id:*") > List all keys start with "cart:Id:..."
			jedis.del(cartKey);
		}
		return 1; // 暫時先 return 1 吧
	}

	@Override
	public List<CartVO> getCart(Integer memberId) {
		// TODO Auto-generated method stub
		List<CartVO> listCart = new ArrayList<>();
		System.out.println("list of cartKey:");
		for (String cartKey : jedis.keys("cart:" + memberId + ":*")) {
			System.out.println(cartKey); // EX: "cart:5:4"
			String[] slot = cartKey.split(":");
			CartVO cartVO = new CartVO();
			cartVO.setMemberId(Integer.parseInt(slot[1]));
			cartVO.setProdId(Integer.parseInt(slot[2]));
			cartVO.setProdName(jedis.hget(cartKey, "prodName"));
			cartVO.setUnitPrice(Integer.parseInt(jedis.hget(cartKey, "unitPrice")));
			cartVO.setProdCount(Integer.parseInt(jedis.hget(cartKey, "prodCount")));
			cartVO.setProdSum(Integer.parseInt(jedis.hget(cartKey, "prodSum")));
			listCart.add(cartVO);
		}
		return listCart;
	} // END of List<CartVO> getCart(Integer memberId)

	@Override
	public CartVO getOneItem(Integer memberId, Integer prodId) {
		// TODO Auto-generated method stub
		CartVO cartVO = null;
		String cartKey = "cart:" + memberId + ":" + prodId;
		if (jedis.exists(cartKey)) { // 如果有才做
			cartVO = new CartVO();
			cartVO.setMemberId(memberId);
			cartVO.setProdId(prodId);
			cartVO.setProdName(jedis.hget(cartKey, "prodName"));
			cartVO.setUnitPrice(Integer.parseInt(jedis.hget(cartKey, "unitPrice")));
			cartVO.setProdCount(Integer.parseInt(jedis.hget(cartKey, "prodCount")));
			cartVO.setProdSum(Integer.parseInt(jedis.hget(cartKey, "prodSum")));
		}
		return cartVO;
	} // END of getOneItem(Integer memberId, Integer prodId)

	@Override
	public void showItem(CartVO cartVO) {
		// TODO Auto-generated method stub
		if (cartVO != null) {
			String cartKey = "cart:" + cartVO.getMemberId() + ":" + cartVO.getProdId();
			// Check cartKey in DB
			System.out.println("Get from cartKey: " + cartKey);
			System.out.println("prodName: " + jedis.hget(cartKey, "prodName"));
			System.out.println("unitPrice: " + jedis.hget(cartKey, "unitPrice"));
			System.out.println("prodCount: " + jedis.hget(cartKey, "prodCount"));
			System.out.println("prodSum: " + jedis.hget(cartKey, "prodSum"));
		} else {
			System.out.println("No cart exist.");
		}
	} // END of showItem(CartVO cartVO) 
}
