package com.product_cart.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.product_cart.model.CartService;
import com.product_cart.model.CartVO;
import com.product_detail.model.ProductDetailService;
import com.product_detail.model.ProductDetailVO;
import com.product_order.model.ProductOrderService;
import com.product_order.model.ProductOrderVO;

@WebServlet("/product_cart/cart.do")
public class CartServlet extends HttpServlet {
	// --- Service/DAO/還有什麼要放在裡? ---
	private ProductService prodSvc;
	private CartService cartSvc;
	private ProductOrderService prodOrdSvc; // HERE_2
	private ProductDetailService prodDetailSvc; // HERE_2
	// --- Servlet 原生 ---
	
	@Override
	public void init() throws ServletException {
		prodSvc = new ProductService();
		cartSvc = new CartService();
		prodOrdSvc = new ProductOrderService(); // HERE_2
		prodDetailSvc = new ProductDetailService(); // HERE_2
		
	}
	// >=====<
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		// === 後端 ? ===
		
		// === 前端 ===
		// From productDetail.jsp:		addToCart |
		// From cart.jsp: 			deleteOneItem | clearCart | checkOut
		// From checkOut.jsp		goPay
		// From creditCardPay.jsp	payByCard
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
			case "checkOut": 
				forwardPath = checkOut(req, res);
				break;
			case "goPay": 
				forwardPath = goPay(req, res);
				break;
			case "payByCard": 
				forwardPath = payByCard(req, res);
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
	
	private String checkOut(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		// --- 0. 宣告 ---
		Integer memberId;
		List<CartVO> cartList;
		Integer total;
		
		// --- 1. 接收資料/錯誤處理/運算 ---
		memberId = Integer.parseInt(req.getParameter("memberId"));
		total = Integer.parseInt(req.getParameter("total"));
		
		System.out.println("MemberId: " + memberId);
		// --- 2. 存取DB ---
		cartList = cartSvc.getCart(memberId);
		
		// --- 3. 回傳 ---
		req.setAttribute("total", total);
		req.setAttribute("memberId", memberId);
		req.setAttribute("cartList", cartList);
		return "/front-end/product/checkOut.jsp";
	} // END of checkOut()
	
	private String goPay(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		// --- 0. 宣告 ---
		Integer memberId; // 取得會員資訊
//		CartService cartSvc = new CartService();
		List<CartVO> cartList;	// 取得明細
		Integer total;	// 取得總價
		Integer ordStatus;	// 取得狀態
		String recipient; // 取得收件人
		String recAddr; // 取得收件人地址 
		
		Map<String, String> errorMsgs = new TreeMap<>(); // 錯誤處理 
		req.setAttribute("errorMsgs", errorMsgs);
		
		// --- 1. 接收資料/錯誤處理/運算 ---
		recipient = req.getParameter("recipient"); // 取得值
		recipient = checkRecipient(recipient, errorMsgs); // 檢查
		
		recAddr = req.getParameter("recAddr"); // 取得值
		recAddr = checkRecAddr(recAddr, errorMsgs); // 檢查

		memberId = Integer.parseInt(req.getParameter("memberId"));
		total = Integer.parseInt(req.getParameter("total"));
		ordStatus = Integer.parseInt(req.getParameter("ordStatus"));
		cartList = cartSvc.getCart(memberId);
		
		req.setAttribute("total", total);
		req.setAttribute("memberId", memberId);
		req.setAttribute("cartList", cartList);
		req.setAttribute("recipient", recipient);
		req.setAttribute("recAddr", recAddr);
		
		// 回傳報錯
		if (!errorMsgs.isEmpty()) {
			return "/front-end/product/checkOut.jsp"; // 回到 checkOut.jsp
		}
		
		// DEBUG
		System.out.println("memberId: " + memberId);
		System.out.println("total: " + total);
		System.out.println("ordStatus: " + ordStatus);
		// DEBUG		

		// --- 2. 存取DB ---
//		cartList = cartSvc.getCart(memberId);
		// 1. 建立訂單 > 取得 prodOrdId
//		ProductOrderService prodOrdSvc = new ProductOrderService(); // HERE_2
		ProductOrderVO prodOrdVO = new ProductOrderVO();
		// 應用 memberId 取得 memberVO
		prodOrdVO.setMember(getTestMember4()); // 放入假的會員
		prodOrdVO.setEstTime(new Timestamp(System.currentTimeMillis()));
		prodOrdVO.setOrdStatus(ordStatus);
		prodOrdVO.setTotal(total);
		prodOrdVO.setRecipient(recipient);
		prodOrdVO.setRecAddr(recAddr);
		// DEBUG ?
		prodOrdVO = prodOrdSvc.addOrder(prodOrdVO); // Order 放入 DB
		Integer prodOrdId = prodOrdVO.getProdOrdId();
		
		// 2. 建立明細 
//		ProductService prodSvc = new ProductService(); // HERE_2
//		ProductDetailService prodDetailSvc = new ProductDetailService(); // HERE_2
		List<ProductDetailVO> detailList = new ArrayList<>(); // 放明細 list
		for (CartVO cart : cartList) {
			ProductDetailVO detailVO = new ProductDetailVO();
			detailVO.setProdOrd(prodOrdVO); // 這裡是放 Order obj
			detailVO.setProdVO(prodSvc.getOneProd(cart.getProdId()));
			detailVO.setUnitPrice(cart.getUnitPrice());
			detailVO.setProdCount(cart.getProdCount());
			detailVO.setProdSum(cart.getProdSum());
			// DEBUG ?
			prodDetailSvc.addDetail(detailVO); // Detail 放入 DB
			detailList.add(detailVO); // Detail 放入 list
		}
		
		// DEBUG
		System.out.println("CartServlet.java/goPay() - 查看訂單\nProductOrder:");
		System.out.println(prodOrdSvc.getOneOrder(prodOrdId));
		System.out.println("ProductDetails:");
		for (ProductDetailVO d : prodOrdSvc.getDetailsByOrderId(prodOrdId)) {
			System.out.println(d);
		}
		System.out.println();
		// DEBUG
		
		// 3. 訂單已生成，刪除購物車
		cartSvc.clearCart(memberId); // CHECK
		
		// --- 3. 回傳 ---
		req.setAttribute("prodOrdVO", prodOrdVO); // 傳生成的 prodOrdVO
		// 正確: 信用卡結帳頁面
		return "/front-end/product/creditCardPay.jsp";
	} // END of goPay()
	
	// >=====<
	private String payByCard(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		// --- 0. 宣告 ---
		Integer prodOrdId;
		
		Map<String, String> errorMsgs = new TreeMap<>(); // 錯誤處理 
		req.setAttribute("errorMsgs", errorMsgs);
		// --- 1. 接收資料/錯誤處理/運算 ---
//		Integer memberId = Integer.parseInt(req.getParameter("memberId"));
		prodOrdId = Integer.parseInt(req.getParameter("prodOrdId"));
		// HERE
		// --- 2. 存取DB ---
//		cartSvc.clearCart(memberId);
		
		// --- 3. 回傳 ---
		return "/front-end/product/shop.jsp"; // 回商城首頁
	} // END of payByCard()
	// --- END of action call ---
	
	
	// --- check() ---
	private String checkRecipient(String recipient, Map<String, String> errorMsgs) {
		if (recipient == null || recipient.trim().isEmpty()) {
			errorMsgs.put("recipient", "請勿空白");
			return null; // 後面就別驗了
		}
		// 用 reg 驗証
		String regRecipient = "^[\\s\\S]{1,120}$";
		if (!recipient.matches(regRecipient)) {
			errorMsgs.put("recipient", "請勿超出 120 字");
			// DEBUG
			System.out.println("CartServlet.java/checkRecipient(): recipient error: 驗証超出120字-REG");
			// DEBUG
			return null;
		}
		return recipient;
	} // END of checkRecipient()
	
	private String checkRecAddr(String recAddr, Map<String, String> errorMsgs) {
		// TODO Auto-generated method stub
		if (recAddr == null || recAddr.trim().isEmpty()) {
		errorMsgs.put("recAddr", "請勿空白");
		return null; // 後面就別驗了
	}
	// 用 reg 驗証
	String regRecAddr = "^[\\s\\S]{1,120}$";
	if (!recAddr.matches(regRecAddr)) {
		errorMsgs.put("recAddr", "請勿超出 120 字");
		// DEBUG
		System.out.println("CartServlet.java/checkRecAddr(): recAddr error: 驗証超出120字-REG");
		// DEBUG
		return null;
	}
	return recAddr;
	} // END of checkRecAddr()
	// >=====<
	// --- END of check() ---	
	
	// --- Other() ---
	private static MemberVO getTestMember4() {
		// check - 02: 包一個 MemberVO
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId(4);
		memberVO.setMemberAccount("antya123");
		memberVO.setMemberPassword("gua2718");
		memberVO.setMemberName("員工4");
		memberVO.setMemberPhone("0918572095");
		memberVO.setMemberEmail("gary@yahoo.com.tw");
		return memberVO;
	}
	// >=====<
	// --- END of Other() ---
	
}
