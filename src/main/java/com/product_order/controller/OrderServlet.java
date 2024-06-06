package com.product_order.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product_detail.model.ProductDetailVO;
import com.product_order.model.ProductOrderService;
import com.product_order.model.ProductOrderVO;
import com.member.model.*;

@WebServlet("/product_order/product_order.do")
public class OrderServlet extends HttpServlet {
	private ProductOrderService prodOrdSvc;
	
	// --- Servlet 原生 ---
	@Override
	public void init() throws ServletException {
		prodOrdSvc = new ProductOrderService();
	} // END of init()

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	} // END of doGet()
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		// From jsp | action: update...
		
		String action = req.getParameter("action");  
		String forwardPath = "";
		
		/**************************** getOne_For_Display ****************************/
		if("getOne_For_Display".equals(action)){
			List<String> errorMsgs = new LinkedList<>();
			
			req.setAttribute("errorMsgs", errorMsgs);
			
			//接收請求參數 - 輸入格式錯誤處理
			String prodOrdIdStr = req.getParameter("prodOrdId");
			if (prodOrdIdStr == null || (prodOrdIdStr.trim()).length() == 0) {
				errorMsgs.add("請輸入訂單編號");
			}
			
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/productorder/order.jsp");
				failureView.forward(req, res);
				return;
			}
			
			Integer prodOrdId = null;
			try {
				prodOrdId = Integer.valueOf(prodOrdIdStr);
			} catch (Exception e) {
				errorMsgs.add("訂單編號格式不正確");
			}
			
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/productorder/order.jsp");
				failureView.forward(req, res);
				return;
			}
		
			//查詢資料
			ProductOrderVO prodOrdVO = prodOrdSvc.getOneOrder(prodOrdId);
			if(prodOrdVO == null) {
				errorMsgs.add("查無資料");
			}
			
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/productorder/order.jsp");
				failureView.forward(req, res);
				return;
			}
			
			//查詢完成，準備轉交
			req.setAttribute("prodOrdVO", prodOrdVO);
			String url = "/back-end/productorder/listOneOrder.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneOrder.jsp
			successView.forward(req, res);
		} // END of if("getOne_For_Display".equals(action))

		/****************************getOne_For_Update ****************************/
		
		if("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);
				
			Integer prodOrdId = Integer.valueOf(req.getParameter("prodOrdId"));
			
//			OrderService orderSvc = new OrderService();
			ProductOrderVO prodOrdVO = prodOrdSvc.getOneOrder(prodOrdId);
							
			req.setAttribute("prodOrdVO", prodOrdVO);         // 資料庫取出的 prodOrdVO 物件，存入req
			String url = "/back-end/productorder/updateOrderInput.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 updateOrderInput.jsp
			successView.forward(req, res);
		}
		
		/**************************** update ****************************/
		
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Integer prodOrdId = Integer.valueOf(req.getParameter("prodOrdId"));
			Integer ordStatus = Integer.valueOf(req.getParameter("ordStatus"));
			Timestamp estTime = Timestamp.valueOf(req.getParameter("estTime"));
			
			String recipient = req.getParameter("recipient");
			if (recipient == null || recipient.trim().length() == 0) {
				errorMsgs.add("收件人請勿空白");
			}
			
			String recAddr = req.getParameter("recAddr");
			if (recAddr == null || recAddr.trim().length() == 0) {
				errorMsgs.add("收件地址請勿空白 ");
			}
			
			Integer total = null;
			try {
				total = Integer.parseInt(req.getParameter("total"));
			}catch(Exception e) {
				errorMsgs.add("總金額請填數字.");
			}
			
			// check - 02: 包一個 MemberVO > 改func()
			// 先用 Svc 抓出這個 prodOrdVO, 因為 prodOrdId, MemberVO 不變，其它的就用 setter
			ProductOrderVO prodOrdVO = prodOrdSvc.getOneOrder(prodOrdId);
			prodOrdVO.setOrdStatus(ordStatus);
			prodOrdVO.setEstTime(estTime);
			prodOrdVO.setRecipient(recipient);
			prodOrdVO.setRecAddr(recAddr);
			prodOrdVO.setTotal(total);
			
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("prodOrdVO", prodOrdVO); // 含有輸入格式錯誤的orderVO物件，也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/productorder/updateOrderInput.jsp");
				failureView.forward(req, res);
				return; //程式中斷
			}
			
			// DB operation
			prodOrdVO = prodOrdSvc.updateOrder(prodOrdVO);
			
			req.setAttribute("prodOrdVO", prodOrdVO);	// 資料庫update成功後，正確的prodOrdVO物件，存入req
			String url = "/back-end/productorder/listOneOrder.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後，轉交listOneOrder.jsp
			successView.forward(req, res);
		} // END of if("update".equals(action))
		
		/**************************** insert ****************************/
		
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<>();			
			req.setAttribute("errorMsgs", errorMsgs);
			
			String recipient = req.getParameter("recipient").trim();
			if(recipient == null || recipient.length() == 0) {
				errorMsgs.add("收件人請勿空白");		
			}
			
			String recAddr = req.getParameter("recAddr").trim();
			if(recAddr == null || recAddr.length() == 0) {
				errorMsgs.add("收件地址請勿空白");		
			}
			
			Integer total = null;
			try {
				total = Integer.parseInt(req.getParameter("total"));
			}catch(Exception e) {
				errorMsgs.add("總金額請填數字.");
			}
			
			Integer ordStatus = Integer.valueOf(req.getParameter("ordStatus"));
			
//			Integer userNumber = 1;	//TODO 正常來說這裡是要抓登入者的會員編號，但目前還未寫到，先用寫死的方式
			MemberVO memberVO = getTestMember();
			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

			ProductOrderVO prodOrdVO = new ProductOrderVO();
			prodOrdVO.setMember(memberVO);
			prodOrdVO.setRecipient(recipient);
			prodOrdVO.setRecAddr(recAddr);
			prodOrdVO.setTotal(total);
			prodOrdVO.setEstTime(currentTimestamp);
			prodOrdVO.setOrdStatus(ordStatus);
			
			
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("prodOrdVO", prodOrdVO); // 含有輸入格式錯誤的orderVO物件，也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/productorder/addOrder.jsp"); 
				failureView.forward(req, res);
				return; // 程式中斷
			}
			
			prodOrdVO = prodOrdSvc.addOrder(prodOrdVO);
			
			String url = "/back-end/productorder/order.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交order.jsp
			successView.forward(req, res);	
			
		} // END of if("insert".equals(action)) 

		// Skip
		/**************************** getOneUserNumber_For_Display ****************************/
		/**************************** getOneOrderStatus_For_Display ****************************/
		
		/**************************** checkout ****************************/
		if("checkout".equals(action)) {
			List<String> errorMsgs = new LinkedList<>();			
			req.setAttribute("errorMsgs", errorMsgs);
			
			String contextPath = req.getContextPath();	//取得專案名稱，給結帳時回傳網頁用

			
			String recipient = req.getParameter("recipient").trim();
			if(recipient == null || recipient.length() == 0) {
				errorMsgs.add("收件人請勿空白");		
			}
			
			String recAddr = req.getParameter("recAddr").trim();
			if(recAddr == null || recAddr.length() == 0) {
				errorMsgs.add("收件地址請勿空白");		
			}
			
//			String userNumber = req.getParameter("userNumber"); // Check - 03: checkout 的頁面怎麼拿到 userNumber/memberId?
			String memberIdStr = req.getParameter("memberId");
			
			ProductOrderVO prodOrdVO = new ProductOrderVO();

			prodOrdVO.setMember(getTestMember()); // check - 02: 包一個 MemberVO ?: 如何取得 member?
			prodOrdVO.setRecAddr(recipient);
			prodOrdVO.setRecAddr(recAddr);
			System.out.println(prodOrdVO);
			
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("prodOrdVO", prodOrdVO); // 含有輸入格式錯誤的orderVO物件，也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/product/checkout.jsp");
				failureView.forward(req, res);
				return; //程式中斷
			}
			// check - 04: 這行是什麼意思? 沒寫 購物車
//			String paymentForm = orderService.checkout(orderVO.getUserNumber(), orderVO, contextPath);
			
//			if (paymentForm != null) {
//                res.setContentType("text/html;charset=UTF-8");
//                res.getWriter().write(paymentForm);
//            } else {
//                String url = "/front-end/checkout.jsp";
//                RequestDispatcher failureView = req.getRequestDispatcher(url); // 如果沒有成功，再回到結帳畫面
//                failureView.forward(req, res);
//            }
			// check - 05: 怎麼寫個結束結帳的頁面?
			String url = "/front-end/product/checkoutTerminal.jsp";
            RequestDispatcher failureView = req.getRequestDispatcher(url); // 如果沒有成功，再回到結帳畫面
            failureView.forward(req, res);

		} // END of if("checkout".equals(action))
		
		
	} // END of doPost()
	// >=====<
	
	// ---END of Servlet 原生 ---
	
	// Other()
	private static MemberVO getTestMember() {
		// check - 02: 包一個 MemberVO
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId(1);
		memberVO.setMemberAccount("tony2892");
		memberVO.setMemberPassword("tia222334");
		memberVO.setMemberName("員工1");
		memberVO.setMemberPhone("0926523123");
		memberVO.setMemberEmail("bbac@yahoo.com.tw");
		return memberVO;
	}
}
