package com.movie.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.movie.model.MovieService;
import com.movie.model.MovieVO;

@WebServlet("/back-end/movie/movie.do")
public class MovieServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
			String str = req.getParameter("movieId");
			if (str == null) {
			    // 如果 movieId 為 null，則需要進行相應的處理，例如設置一個默認值或者返回錯誤訊息
			    errorMsgs.add("請輸入電影編號");
			} else if (str.trim().length() == 0) {
			    // 如果 movieId 是空字串，也需要進行相應的處理
			    errorMsgs.add("請輸入電影編號");
			}

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
			    RequestDispatcher failureView = req.getRequestDispatcher("/back-end/movie/select_page.jsp");
			    failureView.forward(req, res);
			    return; // 程式中斷
			}

			
			Integer movieId = null;
			try {
				movieId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("電影編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/movie/select_page.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}

				
				/***************************2.開始查詢資料*****************************************/
				MovieService movieSvc = new MovieService();
				MovieVO movieVO = movieSvc.getOneMovie(movieId);
				if (movieVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/movie/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("movieVO", movieVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/movie/listOneMovie.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
				/***************************1.接收請求參數****************************************/
				Integer movieId = Integer.valueOf(req.getParameter("movieId"));
				
				/***************************2.開始查詢資料****************************************/
				MovieService movieSvc = new MovieService();
				MovieVO movieVO = movieSvc.getOneMovie(movieId);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("movieVO", movieVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/movie/update_movie_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
//			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
		
			Map<String, String> error = new HashMap<>();
			req.setAttribute("error", error);
			
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer movieId = Integer.valueOf(req.getParameter("movieId").trim());
				
				String movieName = req.getParameter("movieName");
				
				if (movieName == null || movieName.trim().length() == 0) {
					error.put("movieName","請勿空白");
				} 
				
				Integer movieRating = null;
				try {
					movieRating = Integer.valueOf(req.getParameter("movieRating").trim());
				} catch (NumberFormatException e) {
					movieRating = 0; // 或其他適合的預設值
				    error.put("movieRating","請填數字:0.普遍級1.保護級2.輔導級3.限制級");
				}

				String director = req.getParameter("director").trim();
				if (director == null || director.trim().length() == 0) {
//					errorMsgs.add("導演名稱請勿空白");
				}	
				
				String actor = req.getParameter("actor").trim();
				if (actor == null || actor.trim().length() == 0) {
//					errorMsgs.add("演員名稱請勿空白");
				}	
				
				java.sql.Date releaseDate = null;
				String releaseDateParam = req.getParameter("releaseDate");
				if (releaseDateParam != null) {
				    try {
				        releaseDate = java.sql.Date.valueOf(releaseDateParam.trim());
				    } catch (IllegalArgumentException e) {
				        releaseDate = new java.sql.Date(System.currentTimeMillis());
//				        errorMsgs.add("請輸入正確的上映日期!");
				    }
				} else {
				    // 處理 releaseDate 為 null 的情況，例如設置一個默認值或返回錯誤訊息
				    releaseDate = new java.sql.Date(System.currentTimeMillis());
//				    errorMsgs.add("請輸入上映日期!");
				}

				
				java.sql.Date endDate = null;
				String endDateParam = req.getParameter("endDate");
				if (endDateParam != null) {
				    try {
				        endDate = java.sql.Date.valueOf(endDateParam.trim());
				    } catch (IllegalArgumentException e) {
				        endDate = new java.sql.Date(System.currentTimeMillis());
//				        errorMsgs.add("請輸入正確的下映日期!");
				    }
				} else {
				    // 處理 endDate 為 null 的情況，例如設置一個默認值或返回錯誤訊息
				    endDate = new java.sql.Date(System.currentTimeMillis());
//				    errorMsgs.add("請輸入下映日期!");
				}

				
				Integer runtime = null;
				try {
				    runtime = Integer.valueOf(req.getParameter("runtime").trim());
				} catch (NumberFormatException e) {
				    runtime = 0; // 或其他適合的預設值
//				    errorMsgs.add("播放時間請填數字.");
				}

				String introduction = req.getParameter("introduction");
				if (introduction == null || introduction.trim().isEmpty()) {
				    // 如果介紹為空或者只包含空白字符，可以處理相應的邏輯，比如給予預設值或者顯示錯誤訊息。
				    introduction = "暫無介紹"; // 或者給予一個預設值
//				    errorMsgs.add("介紹為空或者只包含空白字符.");
				}

				MovieVO movieVO = new MovieVO();
				movieVO.setMovieId(movieId);
				movieVO.setMovieName(movieName);
				movieVO.setMovieRating(movieRating);
				movieVO.setDirector(director);
				movieVO.setActor(actor);
				movieVO.setReleaseDate(releaseDate);
				movieVO.setEndDate(endDate);
				movieVO.setRuntime(runtime);
				movieVO.setIntroduction(introduction);

				// Send the use back to the form, if there were errors
				if (!error.isEmpty()) {
					req.setAttribute("movieVO", movieVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/movie/update_movie_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				MovieService movieSvc = new MovieService();
				movieVO = movieSvc.updateMovie(movieId, movieName, movieRating, director, actor, releaseDate, endDate,runtime,introduction);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("movieVO",movieVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/movie/Success.html";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
//			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
			
			Map<String, String> errorMsgs = new HashMap<>();
			req.setAttribute("errorMsgs", errorMsgs);


				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String movieName = req.getParameter("movieName");
				String movieNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (movieName == null || movieName.trim().length() == 0) {
					errorMsgs.put("movieName","請勿空白");
				} else if(!movieName.trim().matches(movieNameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("movieName","只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				Integer movieRating = null;
				String movieRatingParam = req.getParameter("movieRating");
				if (movieRatingParam != null) {
				    try {
				        movieRating = Integer.valueOf(movieRatingParam.trim());
				    } catch (NumberFormatException e) {
				        movieRating = 0; // 或其他適合的預設值
				        errorMsgs.put("movieRating","未選擇");
				    }
				} else {
				    // 處理 movieRating 為 null 的情況，例如設置一個默認值或返回錯誤訊息
				    movieRating = 1; // 或其他適合的預設值
				    errorMsgs.put("movieRating","請填寫電影評分!");
				}


				String director = req.getParameter("director").trim();
				if (director == null || director.trim().length() == 0) {
					errorMsgs.put("director","請勿空白");
				}	
				
				String actor = req.getParameter("actor").trim();
				if (actor == null || actor.trim().length() == 0) {
					errorMsgs.put("actor","請勿空白");
				}	
				
				java.sql.Date releaseDate = null;
				try {
					releaseDate = java.sql.Date.valueOf(req.getParameter("releaseDate").trim());
				} catch (IllegalArgumentException e) {
					releaseDate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.put("releaseDate","請選擇日期!");
				}
				
				java.sql.Date endDate = null;
				try {
					endDate = java.sql.Date.valueOf(req.getParameter("endDate").trim());
				} catch (IllegalArgumentException e) {
					endDate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.put("endDate","請選擇日期!");
				}
				
				Integer runtime = null;
				try {
				    runtime = Integer.valueOf(req.getParameter("runtime").trim());
				} catch (NumberFormatException e) {
				    runtime = 0; // 或其他適合的預設值
				    errorMsgs.put("runtime","播放時間請填數字.");
				}

				String introduction = req.getParameter("introduction");
				if (introduction == null || introduction.trim().isEmpty()) {
				    // 如果介紹為空或者只包含空白字符，可以處理相應的邏輯，比如給予預設值或者顯示錯誤訊息。
				    introduction = "暫無介紹"; // 或者給予一個預設值
				    errorMsgs.put("introduction","介紹為空或者只包含空白字符.");
				}

				MovieVO movieVO = new MovieVO();
				movieVO.setMovieName(movieName);
				movieVO.setMovieRating(movieRating);
				movieVO.setDirector(director);
				movieVO.setActor(actor);
				movieVO.setReleaseDate(releaseDate);
				movieVO.setEndDate(endDate);
				movieVO.setRuntime(runtime);
				movieVO.setIntroduction(introduction);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("movieVO", movieVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/movie/addMovie.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				MovieService movieSvc = new MovieService();
				movieVO = movieSvc.addMovie(movieName, movieRating, director, actor, releaseDate, endDate,runtime,introduction);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/movie/Success.html";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);	
				
				


				
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
				/***************************1.接收請求參數***************************************/
				Integer movieId = Integer.valueOf(req.getParameter("movieId"));
				
				/***************************2.開始刪除資料***************************************/
				MovieService movieSvc = new MovieService();
				movieSvc.deleteMovie(movieId);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/movie/listOneMovie.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
		}
		
		if ("listMovie_ByCompositeQuery".equals(action)) { // 來自select_page.jsp的複合查詢請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

				
				/***************************1.將輸入資料轉為Map**********************************/ 
				//採用Map<String,String[]> getParameterMap()的方法 
				//注意:an immutable java.util.Map 
				Map<String, String[]> map = req.getParameterMap();
				
				/***************************2.開始複合查詢***************************************/
				MovieService movieSvc = new MovieService();
				List<MovieVO> list  = movieSvc.getAll(map);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("movieListData", list); // 資料庫取出的list物件,存入request
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/movie/listAllMovie2.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
		}
	}
}
