package com.product_cart.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.product_cart.model.CartService;
import com.product_cart.model.CartVO;

@WebServlet("/product_cart/cart.do")
public class CartServlet extends HttpServlet {
	// --- Service/DAO/還有什麼要放在裡? ---
	private ProductService prodSvc;
	private CartService cartSvc;
	// --- Servlet 原生 ---
	
	@Override
	public void init() throws ServletException {
		prodSvc = new ProductService();
		cartSvc = new CartService();
		
	}
	// >=====<
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		// === 後端 ? ===
		
		// === 前端 ===
		// From productDetail.jsp:		addToCart |
		// From cart.jsp: 			deleteOneItem | clearCart
		String action = req.getParameter("action");  
		String forwardPath = "";
		
		switch (action) {
			case "addToCart": 
				forwardPath = addToCart(req, res);
				break;
//				return; // 測試: 停在原頁 > 不行
			case "deleteOneItem": 
				forwardPath = deleteOneItem(req, res);
				break;
			case "clearCart": 
				forwardPath = clearCart(req, res);
				break;
			default:
				forwardPath = "index.jsp";
		}
		
		res.setContentType("Text/html; charactset=UTF-8");
		RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPath);
		dispatcher.forward(req, res);
	}
	
	// --- END of Servlet 原生 ---
	
	
	// --- action call ---
	
	// === 後台 ? 有嗎 ===
	// === 前台 ? ===
	private String addToCart(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		// --- 0. 宣告 ---
		CartVO cartVO = new CartVO(); // for add obj
		
		// --- 1. 接收資料/錯誤處理/運算 ---
		Integer memberId = Integer.parseInt(req.getParameter("memberId"));
		Integer prodId = Integer.parseInt(req.getParameter("prodId"));
		String prodName = req.getParameter("prodName");
		Integer unitPrice = Integer.parseInt(req.getParameter("unitPrice"));
		Integer prodCount = Integer.parseInt(req.getParameter("prodCount"));
		Integer prodSum = unitPrice * prodCount;

//		System.out.printf("memberId:%d, prodId: %d",memberId, prodId);
		cartVO.setMemberId(memberId);
		cartVO.setProdId(prodId);
		cartVO.setProdName(prodName);
		cartVO.setUnitPrice(unitPrice);
		cartVO.setProdCount(prodCount);
		cartVO.setProdSum(prodSum);
		
		// --- 2. 存取DB ---
		ProductVO prodVO = prodSvc.getOneProd(prodId); // 回到 productDetail.jsp 時，需要拿到這個商品回填資料
		cartSvc.addToCart(cartVO); // Cart 加到 Redis DB
		
		// --- 3. 回傳 ---
		req.setAttribute("prodVO", prodVO);
		
//		http://localhost:8081/Time/front-end/product/cart.jsp
		return "/front-end/product/productDetail.jsp"; // 商品資料回傳 | 應該導向哪?
//		return "/front-end/product/cart.jsp"; // 先導回原網頁, 之後導向購物車
	}
	
	private String deleteOneItem(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		// --- 0. 宣告 ---
		CartVO cartVO = new CartVO(); // for add obj
		
		// --- 1. 接收資料/錯誤處理/運算 ---
		Integer memberId = Integer.parseInt(req.getParameter("memberId"));
		Integer prodId = Integer.parseInt(req.getParameter("prodId"));
		cartVO.setMemberId(memberId);
		cartVO.setProdId(prodId);		
		
		// --- 2. 存取DB ---
		cartSvc.deleteOneItem(cartVO);
		
		// --- 3. 回傳 ---
		return "/front-end/product/cart.jsp";
	}
	// >=====<
	private String clearCart(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		// --- 0. 宣告 ---
		
		// --- 1. 接收資料/錯誤處理/運算 ---
		Integer memberId = Integer.parseInt(req.getParameter("memberId"));
		
		// --- 2. 存取DB ---
		cartSvc.clearCart(memberId);
		
		// --- 3. 回傳 ---
		return "/front-end/product/cart.jsp";
	}
	// --- END of action call ---
	
	// --- check() ---
	// >=====<
	// --- END of check() ---	
	
	// --- Other() ---
	// >=====<
	
	// --- END of Other() ---

}
