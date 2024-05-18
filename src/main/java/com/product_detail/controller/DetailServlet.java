package com.product_detail.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product_detail.model.ProductDetail;
import com.product_detail.model.ProductDetailService;

//src/main/webapp/WEB-INF/web.xml (<servlet-name>OrderServlet) current comment

@WebServlet(name="DetailServlet", urlPatterns="/detail/detail.do")
public class DetailServlet extends HttpServlet {

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
			String str = req.getParameter("prodDetailId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入明細編號");
			}
			// Forward back with errorMsgs
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/detail/selectPage.jsp");
				failureView.forward(req, res);
				return; // 中斷程式
			}
			
			Integer prodDetailId = null;
			try {
				prodDetailId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("明細ID格式不正確");
			}
			// Forward back with errorMsgs
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/detail/selectPage.jsp");
				failureView.forward(req, res);
				return; // 中斷程式
			}
			
			// --- 2. Start query data ----------------------------------------
			// Service 做法 
			ProductDetailService detailSvc = new ProductDetailService();
			ProductDetail prodDetail = detailSvc.getOneDetail(prodDetailId);
			if (prodDetail == null) { errorMsgs.add("查無資料"); }
			// Forward back with errorMsgs
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/detail/selectPage.jsp");
				failureView.forward(req, res);
				return; // 中斷程式
			}
			
			// --- 3. Finish query. Prepare to send the success view ----------
			req.setAttribute("prodDetail", prodDetail); // Query from DB, store to req
			String url = "/detail/listOneDetail.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneDetail.jsp
			successView.forward(req, res);
		} // END of if ("getOneForDisplay".equals(action)
		
		if ("getOneForUpdate".equals(action)) { // 來自 listAllDetail.jsp 的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store in req scope for ErrorPage view
			req.setAttribute("errorMsgs", errorMsgs);
			
			// --- 1. Check input format --------------------------------------
			Integer prodDetailId = Integer.valueOf(req.getParameter("prodDetailId"));
			// --- 2. Query data ----------------------------------------------
			var detailSvc = new ProductDetailService();
			ProductDetail prodDetail = detailSvc.getOneDetail(prodDetailId);
			// --- 3. Finish query. Prepare to send the success view ----------
			req.setAttribute("prodDetail", prodDetail);
			String url = "/detail/updateDetailInput.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 updateDetailInput.jsp
			successView.forward(req, res);
		} // END of if ("getOneForUpdate".equals(action))
		
		if ("update".equals(action)) { // 來自 updateDetailInput.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store in req scope for ErrorPage view, check if necessary?
			req.setAttribute("errorMsgs", errorMsgs);
			
			// Check if I can forward productDetail obj from jsp to Servlet
//			ProductDetail prodDetail = (ProductDetail)req.getAttribute("prodDetail");
//			System.out.println(prodDetail);
			// --- 1. Check input format --------------------------------------
			Integer prodDetailId = Integer.valueOf(req.getParameter("prodDetailId"));
			Integer prodOrdId = Integer.valueOf(req.getParameter("prodOrdId")); // Worked
			Integer prodId = Integer.valueOf(req.getParameter("prodId"));
			Integer unitPrice = Integer.valueOf(req.getParameter("unitPrice"));
			Integer prodCount = Integer.valueOf(req.getParameter("prodCount")); // Should > 0
			Integer prodSum = unitPrice * prodCount;
			// data forward check
//			System.out.println("JSP: prodDetailId = " + prodDetailId);
//			System.out.println("EL : prodOrdId    = " + prodOrdId);
//			System.out.println("EL : unitPrice    = " + unitPrice);
//			System.out.println("EL : prodCount    = " + prodCount);
//			System.out.println("EL : prodSum      = " + prodSum);
			
			ProductDetail prodDetail = new ProductDetail(prodDetailId, prodOrdId, prodId, unitPrice, prodCount, prodSum);	
			if (prodCount < 1) {
				errorMsgs.add("商品數量需 > 0");
				prodDetail.setProdCount(1); // 將商品數量設為 1
				prodDetail.setProdSum(prodDetail.getUnitPrice()); // 將明細小計設為 1*價格 
			} // END of (prodCount <= 0)
			
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("prodDetail", prodDetail);
				RequestDispatcher failureView = req.getRequestDispatcher("/detail/updateDetailInput.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			} // END of if (!errorMsgs.isEmpty())

			// --- 2. Update data ---------------------------------------------
			var detailSvc = new ProductDetailService();
			prodDetail = detailSvc.updateDetail(prodDetail); // 反正都包好了，直接用 obj
			// --- 3. Update success. Prepare send the success view -----------
			req.setAttribute("prodDetail", prodDetail); // After DB update success, save obj to req 
			String url = "/detail/listOneDetail.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // forward to listOneDetail.jsp 
			successView.forward(req, res);
			
		} // END of if ("update".equals(action))
		
		if ("insert".equals(action)) { // 來自 addDetail.jsp 的請求 | 未實作
			// Unimplemented
		} // END of if ("insert".equals(action))
		
		if ("delete".equals(action)) { // 來自 listAllDetail.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store in req scope for ErrorPage view
			req.setAttribute("errorMsgs", errorMsgs);
			
			// --- 1. Receive parameter ---------------------------------------
			// Get from table, no need check format
			Integer prodDetailId = Integer.valueOf(req.getParameter("prodDetailId"));
			// --- 2. Delete data ---------------------------------------------
			var detailSvc = new ProductDetailService();
			detailSvc.deleteDetail(prodDetailId);
			
			// --- 3. Finish delete. Prepare send the success view ------------
			String url = "/detail/listAllDetail.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		} // END of if ("delete".equals(action))
	}
}
