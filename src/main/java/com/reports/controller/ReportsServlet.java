package com.reports.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.article.model.ArticleService;
import com.reports.model.ReportsService;
import com.reports.model.ReportsVO;


@WebServlet("/front-end/reports/reports.do")
public class ReportsServlet extends HttpServlet {

	private Integer reportStatus;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("reportId");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入檢舉編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/reports/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer reportId = null;
				try {
					reportId = Integer.valueOf(str);
				} catch (Exception e) {
					errorMsgs.add("按讚編號不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/reports/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ReportsService reportsSvc = new ReportsService();
				ReportsVO reportsVO = reportsSvc.getOneReports(reportId);
				if (reportsVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/reports/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("reportsVO", reportsVO); // 資料庫取出的empVO物件,存入req
				String url = "/reports/listOneReports.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
				/***************************1.接收請求參數****************************************/
				Integer reportId = Integer.valueOf(req.getParameter("reportId"));
				
				/***************************2.開始查詢資料****************************************/
				ReportsService reportsSvc = new ReportsService();
				ReportsVO reportsVO = reportsSvc.getOneReports(reportId);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("reportsVO", reportsVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/reports/report.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			
				
				
//				String ename = req.getParameter("ename");
//				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (ename == null || ename.trim().length() == 0) {
//					errorMsgs.add("員工姓名: 請勿空白");
//				} else if(!ename.trim().matches(enameReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//	            }
//				
			
				
				Integer reportId = null;
				try {
					reportId = Integer.valueOf(req.getParameter("reportId").trim());
				} catch (NumberFormatException e) {
					reportId = 0;
				errorMsgs.add("檢舉編號請勿空白");
				}
				
				
				Integer memberId = null;
				try {
					memberId = Integer.valueOf(req.getParameter("memberId").trim());
				} catch (NumberFormatException e) {
					memberId = 0;
				errorMsgs.add("會員編號請勿空白");
				}
				
				Integer articleId = null;
				try {
					articleId = Integer.valueOf(req.getParameter("articleId").trim());
				} catch (NumberFormatException e) {
//					articleId = 0;
//				errorMsgs.add("文章編號請勿空白");
				}
				
				Integer commentId = null;
				try {
					commentId = Integer.valueOf(req.getParameter("commentId").trim());
				} catch (NumberFormatException e) {
//					commentId = 0;
//				errorMsgs.add("留言編號請勿空白");
				}
			
				Integer reportType = null;
				try {
					reportType = Integer.valueOf(req.getParameter("reportType").trim());
				} catch (NumberFormatException e) {
					reportType = 0;
					errorMsgs.add("檢舉原因請勿空白");
				}
				
				String reportReason = null;
				try {
					reportReason = String.valueOf(req.getParameter("reportReason").trim());
				} catch (NumberFormatException e) {
					reportReason = null;
					errorMsgs.add("檢舉狀態請勿空白");
				}
				
				
				Integer reportStatus = null;
				try {
					reportStatus = Integer.valueOf(req.getParameter("reportStatus").trim());
				} catch (NumberFormatException e) {
					reportStatus = 0;
					errorMsgs.add("檢舉狀態請勿空白");
				}	
				
				Timestamp reportDateTime = null;
				try {
					reportDateTime = Timestamp.valueOf(req.getParameter("reportDateTime").trim());
				} catch (IllegalArgumentException e) {
					reportDateTime = new Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入檢舉時間");
				}

				ReportsVO reportsVO = new ReportsVO();
				reportsVO.setReportId(reportId);
				reportsVO.setMemberId(memberId);
				reportsVO.setArticleId(articleId);
				reportsVO.setCommentId(commentId);
				reportsVO.setReportType(reportType);
				reportsVO.setReportReason(reportReason);
				reportsVO.setReportStatus(reportStatus);
				reportsVO.setReportDateTime(reportDateTime);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("reportsVO", reportsVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/reports/reportshome.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				System.out.println(reportStatus);
				/***************************2.開始修改資料*****************************************/
				ReportsService reportsSvc = new ReportsService();
				reportsVO = reportsSvc.updateReports(reportId,memberId,articleId,commentId,reportType,reportReason,reportStatus,reportDateTime);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("reportsVO", reportsVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/reports/reportshome.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
//				String ename = req.getParameter("ename");
//				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (ename == null || ename.trim().length() == 0) {
//					errorMsgs.add("員工姓名: 請勿空白");
//				} else if(!ename.trim().matches(enameReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//	            }
				
			
				
				
				Integer memberId = null;
				try {
					memberId = Integer.valueOf(req.getParameter("memberId").trim());
				} catch (NumberFormatException e) {
					memberId = 0;
					errorMsgs.add("會員編號請勿空白");
				}
				
				Integer commentId = null;
				try {
					commentId = Integer.valueOf(req.getParameter("commentId").trim());
				} catch (NumberFormatException e) {
					// 留言編號可以空白
//					commentId = 0;
//					errorMsgs.add("留言編號請勿空白");
				}
				
				Integer articleId = null;
				try {
					articleId = Integer.valueOf(req.getParameter("articleId").trim());
				} catch (NumberFormatException e) {
					articleId = 0;
					errorMsgs.add("文章編號請勿空白");
				}
			
				Integer reportType = null;
				try {
					reportType = Integer.valueOf(req.getParameter("reportType").trim());
				} catch (NumberFormatException e) {
					reportType = 0;
					errorMsgs.add("檢舉原因請勿空白");
				}
				
				
				String reportReason = req.getParameter("reportReason").trim();
				if (reportReason == null || reportReason.trim().length() == 0) {
					errorMsgs.add("檢舉原因請勿空白");
				}
				
				Integer reportStatus = null;
				try {
					reportStatus = Integer.valueOf(req.getParameter("reportStatus").trim());
				} catch (NumberFormatException e) {
					reportStatus = 0;
					errorMsgs.add("檢舉狀態請勿空白");
				}
				
				java.sql.Timestamp reportDateTime = Timestamp.valueOf(LocalDateTime.now());

				ReportsVO reportsVO = new ReportsVO();
				
			
				reportsVO.setMemberId(memberId);
				reportsVO.setArticleId(articleId);
				reportsVO.setCommentId(commentId);
				reportsVO.setReportType(reportType);
				reportsVO.setReportReason(reportReason);
				reportsVO.setReportStatus(reportStatus);
				reportsVO.setReportDateTime(reportDateTime);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println(errorMsgs);
					req.setAttribute("reportsVO", reportsVO); // 含有輸入格式錯誤的empVO物件,也存入req  //<---注意 重要!!!!!!!!!!!!!!!!!!!!!!!!!! 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/article/articleContent.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				ReportsService reportsSvc = new ReportsService();
				reportsVO = reportsSvc.addReports(memberId, articleId, commentId, reportType, reportReason, reportStatus, reportDateTime);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/article/article.do?action=getOne_For_Display&articleId=" + articleId;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
//	
//				/***************************1.接收請求參數***************************************/
//				Integer empno = Integer.valueOf(req.getParameter("empno"));
//				
//				/***************************2.開始刪除資料***************************************/
//				EmpService empSvc = new EmpService();
//				empSvc.deleteEmp(empno);
//				
//				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
//				String url = "/emp/listAllEmp.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
		}
		
		if ("updateReportStatus".equals(action)) { // 來自listAllEmp.jsp

			ReportsService reportsSvc = new ReportsService();
			reportsSvc.updateReport_status(Integer.valueOf(req.getParameter("reportId")));
			
			ArticleService articleSvc = new ArticleService();
			articleSvc.updateArticleStatus(Integer.valueOf(req.getParameter("articleId")));
			
			String url = "/back-end/reports/report.jsp";  
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}
		
	}
}
