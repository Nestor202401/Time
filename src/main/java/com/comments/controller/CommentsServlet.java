package com.comments.controller;

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

import com.comments.model.CommentsService;
import com.comments.model.CommentsVO;

@WebServlet("/front-end/comments/comments.do")
public class CommentsServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("commentId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請勿空白");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/commentId/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer commentId = null;
			try {
				commentId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("留言編號不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/comments/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			CommentsService commentsSvc = new CommentsService();
			CommentsVO commentsVO = commentsSvc.getOneComments(commentId);
			if (commentsVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/comments/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("commentsVO", commentsVO); // 資料庫取出的empVO物件,存入req
			String url = "/front-end/comments/commentsContent.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer commentId = Integer.valueOf(req.getParameter("commentId"));

			/*************************** 2.開始查詢資料 ****************************************/
			CommentsService commentsSvc = new CommentsService();
			CommentsVO commentsVO = commentsSvc.getOneComments(commentId);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("commentsVO", commentsVO); // 資料庫取出的empVO物件,存入req
			String url = "/front-end/comments/commentupdate.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
			successView.forward(req, res);
		}

		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

			Integer commentId = null;
			try {
				commentId = Integer.valueOf(req.getParameter("commentId").trim());
			} catch (NumberFormatException e) {
				commentId = 0;
				errorMsgs.add("留言編號請勿空白");
			}

//				String ename = req.getParameter("ename");
//				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (ename == null || ename.trim().length() == 0) {
//					errorMsgs.add("員工姓名: 請勿空白");
//				} else if(!ename.trim().matches(enameReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//	            }

			Integer articleId = null;
			String articleIdStr = req.getParameter("articleId");
			if (articleIdStr != null && !articleIdStr.isEmpty()) {
				articleId = Integer.valueOf(articleIdStr.trim());
			} else {
				// 處理參數為空的情況，例如設置一個默認值或者拋出異常
				// 這裡僅為示例，你可以根據實際情況進行修改
				articleId = 0; // 設置默認值
				errorMsgs.add("文章編號請勿空白");
			}

			Integer memberId = null;
			try {
				memberId = Integer.valueOf(req.getParameter("memberId").trim());
			} catch (NumberFormatException e) {
				memberId = 0;
				errorMsgs.add("會員編號請勿空白");
			}

//				String commentContent = req.getParameter("commentContent").trim();
//				if (commentContent == null || commentContent.trim().length() == 0) {
//					errorMsgs.add("留言內容請勿空白");
//				}	

			String commentContent = null;
			try {
				commentContent = String.valueOf(req.getParameter("commentContent").trim());
			} catch (NumberFormatException e) {
				commentContent = null;
				errorMsgs.add("請勿空白");
			}

			Integer commentStatus = null;
			String commentStatusParam = req.getParameter("commentStatus");
			if (commentStatusParam != null && !commentStatusParam.isEmpty()) {
				try {
					commentStatus = Integer.valueOf(commentStatusParam.trim());
				} catch (NumberFormatException e) {
					commentStatus = 0;
				}
			}

			Timestamp releaseTime = null;
			try {
				releaseTime = Timestamp.valueOf(req.getParameter("releaseTime").trim());
			} catch (IllegalArgumentException e) {
				releaseTime = new Timestamp(System.currentTimeMillis());
				errorMsgs.add("請輸入日期!");
			}

			CommentsVO commentsVO = new CommentsVO();
			commentsVO.setCommentId(commentId);
			commentsVO.setArticleId(articleId);
			commentsVO.setMemberId(memberId);
			commentsVO.setCommentContent(commentContent);
			commentsVO.setCommentStatus(commentStatus);
			commentsVO.setReleaseTime(releaseTime);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("commentsVO", commentsVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/comments/commentupdate.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			CommentsService commentsSvc = new CommentsService();
			commentsVO = commentsSvc.updateComments(commentId, articleId, memberId, commentContent, commentStatus,
					releaseTime);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("commentsVO", commentsVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/front-end/comments/commenthome.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
//				String ename = req.getParameter("ename");
//				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (ename == null || ename.trim().length() == 0) {
//					errorMsgs.add("員工姓名: 請勿空白");
//				} else if(!ename.trim().matches(enameReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//	            }

			Integer articleId = null;
			try {
				articleId = Integer.valueOf(req.getParameter("articleId").trim());
			} catch (NumberFormatException e) {
				articleId = 0;
				errorMsgs.add("留言編號請勿空白");
			}

			Integer memberId = null;
			try {
				memberId = Integer.valueOf(req.getParameter("memberId").trim());
			} catch (NumberFormatException e) {
				memberId = 0;
				errorMsgs.add("會員編號請勿空白");
			}

			String commentContent = req.getParameter("commentContent").trim();
			if (commentContent == null || commentContent.trim().length() == 0) {
				errorMsgs.add("留言內容請勿空白");
			}

			Integer commentStatus = 0;

//				Integer commentStatus = null;
//				try {
//					commentStatus = Integer.valueOf(req.getParameter("commentStatus").trim());
//				} catch (NumberFormatException e) {
//					commentStatus = 0;
//					errorMsgs.add("請勿空白");
//				}

			java.sql.Timestamp releaseTime = Timestamp.valueOf(LocalDateTime.now());

			CommentsVO commentsVO = new CommentsVO();

			commentsVO.setArticleId(articleId);
			commentsVO.setMemberId(memberId);
			commentsVO.setCommentContent(commentContent);
			commentsVO.setCommentStatus(commentStatus);
			commentsVO.setReleaseTime(releaseTime);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("commentsVO", commentsVO); // 含有輸入格式錯誤的empVO物件,也存入req //<---注意
															// 重要!!!!!!!!!!!!!!!!!!!!!!!!!!
				RequestDispatcher failureView = req.getRequestDispatcher("/comments/addEmp.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			CommentsService commentsSvc = new CommentsService();
			commentsVO = commentsSvc.addComments(commentsVO);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
//			String url = "/front-end/comments/commenthome.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
//			successView.forward(req, res);
			
			res.sendRedirect(req.getContextPath() + "/front-end/article/article.do?action=getOne_For_Display&articleId=" + articleId);
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

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
	}
}
