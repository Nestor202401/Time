package com.product_order.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product_order.model.ProductOrder;
import com.product_order.model.ProductOrderService;

// src/main/webapp/WEB-INF/web.xml (<servlet-name>OrderServlet) current comment
//	<servlet>
//	<servlet-name>OrderServlet</servlet-name>
//	<servlet-class>com.product_order.controller.OrderServlet</servlet-class>
//	</servlet>
//	<servlet-mapping>
//	<servlet-name>OrderServlet</servlet-name>
//	<url-pattern>/order/order.do</url-pattern>
//	</servlet-mapping>

@WebServlet(name="OrderServlet", urlPatterns="/order/order.do")
public class OrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		// Query with getAll
//		if ("getAll".equals(action)) {// In 0201, getAll(link) doesn't need to check, link to JSP directly.}
		
		// Query with FindByPk
		if ("getOneForDisplay".equals(action)) { // 來自 selectPage.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store in req scope for ErrorPage view
			req.setAttribute("errorMsgs", errorMsgs);
			
			// --- 1. Receive request parameters, format check ----------------
			String str = req.getParameter("prodOrdId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入明細編號");
			}
			// Forward back with errorMsgs
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/order/selectPage.jsp");
				failureView.forward(req, res);
				return; // 中斷程式
			}
			
			Integer prodOrdId = null;
			try {
				prodOrdId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("明細ID格式不正確");
			}
			// Forward back with errorMsgs
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/order/selectPage.jsp");
				failureView.forward(req, res);
				return; // 中斷程式
			}
			
			// --- 2. Start query data ----------------------------------------
			// Service 做法 
			ProductOrderService orderSvc = new ProductOrderService();
			ProductOrder prodOrd = orderSvc.getOneOrder(prodOrdId);
			if (prodOrd == null) { errorMsgs.add("查無資料"); }
			// Forward back with errorMsgs
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/order/selectPage.jsp");
				failureView.forward(req, res);
				return; // 中斷程式
			}
			
			// --- 3. Finish query. Prepare to send the success view ----------
			req.setAttribute("prodOrd", prodOrd); // Query from DB, store to req
			String url = "/order/listOneOrder.jsp"; 
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneOrder.jsp
			successView.forward(req, res);
		} // END of if ("getOneForDisplay".equals(action)
		
		if ("getOneForUpdate".equals(action)) { // 來自 listAllOrder.jsp 的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store in req scope for ErrorPage view
			req.setAttribute("errorMsgs", errorMsgs);
			
			// --- 1. Check input format --------------------------------------
			Integer prodOrdId = Integer.valueOf(req.getParameter("prodOrdId"));
			// --- 2. Query data ----------------------------------------------
			var orderSvc = new ProductOrderService();
			ProductOrder prodOrd = orderSvc.getOneOrder(prodOrdId);
			// --- 3. Finish query. Prepare to send the success view ----------
			req.setAttribute("prodOrd", prodOrd);
			String url = "/order/updateOrderInput.jsp"; 
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 updateOrderInput.jsp
			successView.forward(req, res);
		} // END of if ("getOneForUpdate".equals(action))
		
		if ("update".equals(action)) { // 來自 updateOrderInput.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store in req scope for ErrorPage view, check if necessary?
			req.setAttribute("errorMsgs", errorMsgs);
			
			// --- 1. Check input format --------------------------------------
			Integer prodOrdId = Integer.valueOf(req.getParameter("prodOrdId"));
			Integer memberId = 	Integer.valueOf(req.getParameter("memberId")); 
			Timestamp estTime = Timestamp.valueOf(req.getParameter("estTime")); 
			Integer ordStatus = Integer.valueOf(req.getParameter("ordStatus"));
			Integer total = 	Integer.valueOf(req.getParameter("total")); 
			String recipient = req.getParameter("recipient");
			String recipientReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9‧ )]{2,50}$";
			if (recipient == null || recipient.trim().isEmpty()) { // .isEmpty() [1.6] | .length() == 0 / .equals("") [1.0]
				errorMsgs.add("收件人姓名 請勿空白");
			} else if (!recipient.trim().matches(recipientReg)) { // 以下練習正規表示法
				errorMsgs.add("收件人姓名 只能是中、英、數字和空隔，且長度在2-50之間");
			}
			
			String recAddr = req.getParameter("recAddr");
			String recAddrReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9 )]{2,200}$";
			if (recAddr == null || recAddr.trim().equals("")) { // .isEmpty() [1.6] | .length() == 0 / .equals("") [1.0]
				errorMsgs.add("收件人地址 請勿空白");
			} else if (!recAddr.trim().matches(recAddrReg)) { // 以下練習正規表示法
				errorMsgs.add("收件人地址請勿使用標點符號、底線、特殊符號，且長度在2-200之間");
			}
			
			// data forward check
//			System.out.println("JSP: prodOrdId = " + prodOrdId);
//			System.out.println("EL : prodOrdId    = " + prodOrdId);
//			System.out.println("EL : unitPrice    = " + unitPrice);
//			System.out.println("EL : prodCount    = " + prodCount);
//			System.out.println("EL : prodSum      = " + prodSum);
			
			var prodOrd = new ProductOrder(prodOrdId, memberId, estTime, ordStatus, total, recipient, recAddr);	
			// 做錯誤檢查
//			if (prodCount < 1) { // HERE
//				errorMsgs.add("商品數量需 > 0");
//				prodOrd.setProdCount(1); // 將商品數量設為 1
//				prodOrd.setProdSum(prodOrd.getUnitPrice()); // 將明細小計設為 1*價格 
//			} // END of (prodCount <= 0)
			
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("prodOrd", prodOrd);
				RequestDispatcher failureView = req.getRequestDispatcher("/order/updateOrderInput.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			} // END of if (!errorMsgs.isEmpty())

			// --- 2. Update data ---------------------------------------------
			var orderSvc = new ProductOrderService();
			prodOrd = orderSvc.updateOrder(prodOrd); // 反正都包好了，直接用 obj 
			// --- 3. Update success. Prepare send the success view -----------
			req.setAttribute("prodOrd", prodOrd); // After DB update success, save obj to req 
			String url = "/order/listOneOrder.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // forward to listOneOrder.jsp 
			successView.forward(req, res);
			
		} // END of if ("update".equals(action))
		
		if ("insert".equals(action)) { // 來自 addOrder.jsp 的請求 | 未實作
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			// --- 1. Check input format --------------------------------------
//			Integer prodOrdId = Integer.valueOf(req.getParameter("prodOrdId").trim());
			Integer prodOrdId = null; // PK auto increment, not receive from add obj
			
			Integer memberId = null;
			try {
				memberId = Integer.valueOf(req.getParameter("memberId").trim());
				if (memberId <= 0) { // 未連動會員表格，無法判斷會員是否存在
					errorMsgs.add("會員 ID 需 > 0 ");
				}
			} catch (NumberFormatException e) { // 無輸入的情況
				memberId = -1;
				errorMsgs.add("請輸入會員 ID!");
			}
			
//			Timestamp estTime = Timestamp.valueOf(req.getParameter("estTime"));
			Timestamp estTime = new Timestamp(System.currentTimeMillis()); // Assign current time when order establish
			
			Integer ordStatus = null;
			try {
				ordStatus = Integer.valueOf(req.getParameter("ordStatus")); // should return 0 or 1
			} catch (NumberFormatException e) { // 無輸入的情況
				ordStatus = -1; // should return 0 or 1, can I assign -1?
				errorMsgs.add("請輸入訂單狀態!");
			}
			
			Integer total = null;
			try {
			total = Integer.valueOf(req.getParameter("total")); // Should calculate automatically, but only check < 0 now
			} catch (NumberFormatException e) { // 無輸入的情況
				total = 0;
				errorMsgs.add("請輸總額!");
			} 
			if (total < 0) {
				errorMsgs.add("總額不得小於 0");
			}
			
			String recipient = req.getParameter("recipient");
			String recipientReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9‧ )]{2,50}$";
			if (recipient == null || recipient.trim().isEmpty()) { // .isEmpty() [1.6] | .length() == 0 / .equals("") [1.0]
				errorMsgs.add("收件人姓名 請勿空白");
			} else if (!recipient.trim().matches(recipientReg)) { // 以下練習正規表示法
				errorMsgs.add("收件人姓名 只能是中、英、數字和空隔，且長度在2-50之間");
			}
			
			String recAddr = req.getParameter("recAddr");
			String recAddrReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9 )]{2,200}$";
			if (recAddr == null || recAddr.trim().equals("")) { // .isEmpty() [1.6] | .length() == 0 / .equals("") [1.0]
				errorMsgs.add("收件人地址 請勿空白");
			} else if (!recAddr.trim().matches(recAddrReg)) { // 以下練習正規表示法
				errorMsgs.add("收件人地址請勿使用標點符號、底線、特殊符號，且長度在2-200之間");
			}
			
			// Package to a ProductOrder obj
			var prodOrd = new ProductOrder(prodOrdId, memberId, estTime, ordStatus, total, recipient, recAddr);
			
			// Send the data back to the form, if there were errors
			if(!errorMsgs.isEmpty()) {
				req.setAttribute("prodOrder", prodOrd); // 把剛才打的資料送回 addOrder.jsp
				RequestDispatcher failureView = req.getRequestDispatcher("addOrder.jsp");
				failureView.forward(req, res);
				return;
			}
			
			// --- 2. Insert data ---------------------------------------------
			var ordSvc = new ProductOrderService();
			prodOrd = ordSvc.addOrder(prodOrd);
			// 未連動會員表格，無法判斷會員是否存在
			
			// --- 3. Insert success. Prepare send the success view -----------
			String url = "listAllOrder.jsp"; // Check url
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);	
		} // END of if ("insert".equals(action))
		
		if ("delete".equals(action)) { // 來自 listAllOrder.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store in req scope for ErrorPage view
			req.setAttribute("errorMsgs", errorMsgs);
			
			// --- 1. Receive parameter ---------------------------------------
			// Get from table, no need check format
			Integer prodOrdId = Integer.valueOf(req.getParameter("prodOrdId"));
			// --- 2. Delete data ---------------------------------------------
			var orderSvc = new ProductOrderService();
			orderSvc.deleteOrder(prodOrdId);
			
			// --- 3. Finish delete. Prepare send the success view ------------
			String url = "/order/listAllOrder.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		} // END of if ("delete".equals(action))
	}
}
