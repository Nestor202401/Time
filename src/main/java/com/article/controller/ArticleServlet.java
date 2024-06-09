package com.article.controller;

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
import com.article.model.ArticleVO;
import com.comments.model.*;

@WebServlet("/front-end/article/article.do")
public class ArticleServlet extends HttpServlet {

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
			String str = req.getParameter("articleId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入文章編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/emp/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer articleId = null;
			try {
				articleId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("文章編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/article/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			ArticleService articleSvc = new ArticleService();
			ArticleVO articleVO = articleSvc.getOneArticle(articleId);
			if (articleVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/article/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}
			
			CommentsService commentsSvc = new CommentsService();
			List<CommentsVO> commentsVOs = commentsSvc.getAllByArticleId(articleId);
			
			ArticleService articleService = new ArticleService();
			articleService.updateBrowsePeoples(articleId);
			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("articleVO", articleVO); // 資料庫取出的empVO物件,存入req
			req.setAttribute("commentsVOs", commentsVOs); // 資料庫取出的empVO物件,存入req
			String url = "/front-end/article/articleContent.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer articleId = Integer.valueOf(req.getParameter("articleId"));

			/*************************** 2.開始查詢資料 ****************************************/
			ArticleService articleSvc = new ArticleService();

			ArticleVO articleVO = articleSvc.getOneArticle(articleId);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("articleVO", articleVO); // 資料庫取出的empVO物件,存入req
			String url = "/front-end/article/update.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
			successView.forward(req, res);
		}

		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer articleId = Integer.valueOf(req.getParameter("articleId").trim());

//				Integer type_id = null;			
//				String type_idStr = req.getParameter("type_id");
//				String type_idReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (type_idStr == null || type_idStr.trim().length() == 0) {
//					errorMsgs.add("類型編號: 請勿空白");
//				} else if(!type_idStr.trim().matches(type_idReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("類型編號: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//	            } else {
//	            	type_id = Integer.valueOf(type_idStr);
//	            }

			Integer typeId = null;
			try {
				typeId = Integer.valueOf(req.getParameter("typeId").trim());
			} catch (NumberFormatException e) {
				typeId = 0;
				errorMsgs.add("電影類別不能為空");
			}

			Integer memberId = null;
			try {
				memberId = Integer.valueOf(req.getParameter("memberId").trim());
			} catch (NumberFormatException e) {
				memberId = 0;
				errorMsgs.add("會員編號請勿空白.");
			}

//				String member_idStr = req.getParameter("member_id").trim();
//				Integer member_id = null;
//				if (member_idStr == null || member_idStr.trim().length() == 0) {
//					errorMsgs.add("會員編號請勿空白");
//				} else {
//					member_id = Integer.valueOf(member_idStr);
//				}

			String theme = req.getParameter("theme").trim();
			if (theme == null || theme.trim().length() == 0) {
				errorMsgs.add("請輸入文章!");
			}

			String articleContent = null;
			articleContent = req.getParameter("articleContent").trim();
			if (theme == null || theme.trim().length() == 0) {
				errorMsgs.add("文章內容請勿空白");
			}

			Integer browsePeoples = null;
			try {
				browsePeoples = Integer.valueOf(req.getParameter("browsePeoples").trim());
			} catch (NumberFormatException e) {
				browsePeoples = 0;
			}

			Integer articleStatus = null;
			try {
				articleStatus = Integer.valueOf(req.getParameter("articleStatus").trim());
			} catch (NumberFormatException e) {
				articleStatus = 0;
			}

			Timestamp releaseTime = null;
			try {
				releaseTime = Timestamp.valueOf(req.getParameter("releaseTime").trim());
			} catch (IllegalArgumentException e) {
				releaseTime = new Timestamp(System.currentTimeMillis());
				errorMsgs.add("請輸入日期!");
			}

			ArticleVO articleVO = new ArticleVO();
			articleVO.setArticleId(articleId);
			articleVO.setTypeId(typeId);
			articleVO.setMemberId(memberId);
			articleVO.setTheme(theme);
			articleVO.setArticleContent(articleContent);
			articleVO.setBrowsePeoples(browsePeoples);
			articleVO.setArticleStatus(articleStatus);
			articleVO.setReleaseTime(releaseTime);

			// Send the use back to the form, if there were errors

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("articleVO", articleVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/article/update_article_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			ArticleService articleSvc = new ArticleService();
			articleVO = articleSvc.updateArticle(articleId, typeId, memberId, theme, articleContent, browsePeoples,
					articleStatus, releaseTime);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("articleVO", articleVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/front-end/article/home.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
//				String typeId = req.getParameter("type_id");
//				String typeIdReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (type_id == null || type_id.trim().length() == 0) {
//					errorMsgs.add("員工姓名: 請勿空白");
//				} else if(!type_id.trim().matches(type_idReg)) { //以下練習正則(規)表示式(regular-expression }
//					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//	            }

			Integer typeId = null;
			try {
				typeId = Integer.valueOf(req.getParameter("typeId").trim());
			} catch (NumberFormatException e) {
				typeId = 0;
				errorMsgs.add("電影類別不能為空");
			}

			Integer memberId = null;
			try {
				memberId = Integer.valueOf(req.getParameter("memberId").trim());
			} catch (NumberFormatException e) {
				memberId = 0;
				errorMsgs.add("會員編號請勿空白");
			}

			String theme = req.getParameter("theme").trim();
			if (theme == null || theme.trim().length() == 0) {
				errorMsgs.add("請輸入文章!");
			}

			String articleContent = null;
			articleContent = req.getParameter("articleContent").trim();
			if (articleContent == null || articleContent.trim().length() == 0) {
				errorMsgs.add("文章內容請勿空白");
			}

			Integer browsePeoples = 0;
//			try {
//				browsePeoples = Integer.valueOf(req.getParameter("browsePeoples").trim());
//			} catch (NumberFormatException e) {
//				browsePeoples = 0;
//			}
			Integer articleStatus = 0;
//			try {
//				articleStatus = Integer.valueOf(req.getParameter("articleStatus").trim());
//			} catch (NumberFormatException e) {
//				articleStatus = 0;
//			}

			java.sql.Timestamp releaseTime = Timestamp.valueOf(LocalDateTime.now());

			ArticleVO articleVO = new ArticleVO();

			articleVO.setTypeId(typeId);
			articleVO.setMemberId(memberId);
			articleVO.setTheme(theme);
			articleVO.setArticleContent(articleContent);
			articleVO.setBrowsePeoples(browsePeoples);
			articleVO.setArticleStatus(articleStatus);
			articleVO.setReleaseTime(releaseTime);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("articleVO", articleVO); // 含有輸入格式錯誤的empVO物件,也存入req //<---注意
															// 重要!!!!!!!!!!!!!!!!!!!!!!!!!!
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/article/addArticle.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			ArticleService articleSvc = new ArticleService();
			articleVO = articleSvc.addArticle(articleVO);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/front-end/article/home.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer articleId = Integer.valueOf(req.getParameter("articleId"));

			/*************************** 2.開始刪除資料 ***************************************/
			ArticleService articleSvc = new ArticleService();
//			articleSvc.deleteEmp(articleId);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/article/listAllArticle.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}
	}
}
