package com.movietime.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
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

import com.cinema.model.CinemaService;
import com.google.gson.Gson;
import com.movie.model.MovieVO;
import com.movietime.model.MovieTimeService;
import com.movietime.model.MovieTimeVO;

@WebServlet("/back-end/movietime/movietime.do")
public class MovieTimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
            Map<String, String> errorMsgs = new HashMap<>();
            req.setAttribute("errorMsgs", errorMsgs);

            /***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
            Integer movieId = null;
            try {
                movieId = Integer.valueOf(req.getParameter("movieId").trim());
            } catch (NumberFormatException e) {
                movieId = 0; // 或其他適合的預設值
                errorMsgs.put("movieId", "請勿空白");
            }
                
            Integer cinemaId = null;
            try {
                cinemaId = Integer.valueOf(req.getParameter("cinemaId").trim());
            } catch (NumberFormatException e) {
                cinemaId = 0; // 或其他適合的預設值
                errorMsgs.put("cinemaId", "請勿空白");
            }

            Integer moviePlaybackType = null;
            try {
                moviePlaybackType = Integer.valueOf(req.getParameter("moviePlaybackType").trim());
            } catch (NumberFormatException e) {
                moviePlaybackType = 0; // 或其他適合的預設值
                errorMsgs.put("moviePlaybackType", "請選擇類型");
            }   
                
            Integer price = null;
            try {
                price = Integer.valueOf(req.getParameter("price").trim());
            } catch (NumberFormatException e) {
                price = 0; // 或其他適合的預設值
                errorMsgs.put("price", "請勿空白");
            }   
                
            Integer showTime = null;
            try {
                showTime = Integer.valueOf(req.getParameter("showTime").trim());
            } catch (NumberFormatException e) {
                showTime = 0; // 或其他適合的預設值
                errorMsgs.put("showTime", "請勿空白");
            }
                
            java.sql.Date showTimeDate = null;
            try {
                showTimeDate = java.sql.Date.valueOf(req.getParameter("showTimeDate").trim());
            } catch (IllegalArgumentException e) {
                showTimeDate = new java.sql.Date(System.currentTimeMillis());
                errorMsgs.put("showTimeDate", "請選擇日期!");
            }

            MovieTimeVO movieTimeVO = new MovieTimeVO();
            movieTimeVO.setMovieId(movieId);
            movieTimeVO.setCinemaId(cinemaId);
            movieTimeVO.setMoviePlaybackType(moviePlaybackType);
            movieTimeVO.setPrice(price);
            movieTimeVO.setShowTime(showTime);
            movieTimeVO.setShowTimeDate(showTimeDate);

            // Send the use back to the form, if there were errors
            if (!errorMsgs.isEmpty()) {
                req.setAttribute("movieTimeVO", movieTimeVO); // 含有輸入格式錯誤的movieTimeVO物件,也存入req
                RequestDispatcher failureView = req.getRequestDispatcher("/addMovieTime.jsp");
                failureView.forward(req, res);
                return;
            }

            /***************************2.開始新增資料***************************************/
            MovieTimeService movieTimeSvc = new MovieTimeService();
            movieTimeVO = movieTimeSvc.addMovieTime(movieId, cinemaId, moviePlaybackType, price, showTime, showTimeDate);
            CinemaService cinemaService = new CinemaService();
            
            
            // 使用新增的场次ID创建专属座位表
            String sessionId = movieTimeVO.getShowTimesId().toString();
            String cinemaName = cinemaService.getCinemaNameById(cinemaId); // 假设有这个方法获取影厅名称
            cinemaService.createSessionSeats(sessionId, cinemaName);

            /***************************3.新增完成,準備轉交(Send the Success view)***********/
            String url = "/back-end/movietime/Success.html";
            RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交Success.html
            successView.forward(req, res);   
        }
		
if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			Map<String, String> error = new HashMap<>();
			req.setAttribute("error", error);
			
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			String showTimesIdStr = req.getParameter("showTimesId");
		    Integer showTimesId = null;
		    if (showTimesIdStr != null) {
		        try {
		            showTimesId = Integer.valueOf(showTimesIdStr.trim());
		        } catch (NumberFormatException e) {
		            showTimesId = 0; // 或其他適合的預設值
		            error.put("showTimesId", "請勿空白");
		        }
		    } else {
		        showTimesId = 0; // 或其他適合的預設值
		        error.put("showTimesId", "請勿空白");
		    }	
			    
				Integer movieId = null;
				try {
					movieId = Integer.valueOf(req.getParameter("movieId").trim());
				} catch (NumberFormatException e) {
					movieId = 0; // 或其他適合的預設值
				    error.put("movieId","請勿空白");
				}
				
				Integer cinemaId = null;
				try {
					cinemaId = Integer.valueOf(req.getParameter("cinemaId").trim());
				} catch (NumberFormatException e) {
					cinemaId = 0; // 或其他適合的預設值
				    error.put("cinemaId","請勿空白");
				}

				Integer moviePlaybackType = null;
				try {
					moviePlaybackType = Integer.valueOf(req.getParameter("moviePlaybackType").trim());
				} catch (NumberFormatException e) {
					moviePlaybackType = 0; // 或其他適合的預設值
				    error.put("moviePlaybackType","請勿空白");
				}	

				Integer price = null;
				try {
					price = Integer.valueOf(req.getParameter("price").trim());
				} catch (NumberFormatException e) {
					price = 0; // 或其他適合的預設值
				    error.put("price","請勿空白");
				}
				
				Integer showTime = null;
				try {
					showTime = Integer.valueOf(req.getParameter("showTime").trim());
				} catch (NumberFormatException e) {
					showTime = 0; // 或其他適合的預設值
				    error.put("showTime","請勿空白");
				}
				
				java.sql.Date showTimeDate = null;
				String showTimeDateStr = req.getParameter("showTimeDate");
				if (showTimeDateStr != null) {
				    try {
				    	showTimeDate = java.sql.Date.valueOf(showTimeDateStr.trim());
				    } catch (IllegalArgumentException e) {
				    	showTimeDate = new java.sql.Date(System.currentTimeMillis());
				        error.put("showTimeDate","請勿空白");
				    }
				} else {
				    // 處理 releaseDate 為 null 的情況，例如設置一個默認值或返回錯誤訊息
					showTimeDate = new java.sql.Date(System.currentTimeMillis());
				     error.put("showTimeDate","請勿空白");
				}

				
				MovieTimeVO movieTimeVO = new MovieTimeVO();
				movieTimeVO.setShowTimesId(showTimesId);
				movieTimeVO.setMovieId(movieId);
				movieTimeVO.setCinemaId(cinemaId);
				movieTimeVO.setMoviePlaybackType(moviePlaybackType);
				movieTimeVO.setPrice(price);
				movieTimeVO.setShowTime(showTime);
				movieTimeVO.setShowTimeDate(showTimeDate);

				// Send the use back to the form, if there were errors
				if (!error.isEmpty()) {
					req.setAttribute("movieTimeVO", movieTimeVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/movietime/updatetime.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				MovieTimeService movieTimeSvc = new MovieTimeService();
				movieTimeVO = movieTimeSvc.updateMovieTime(showTimesId,movieId,cinemaId,
						moviePlaybackType,price,showTime,showTimeDate);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("movieTimeVO",movieTimeVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/movietime/Success.html";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
		}
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
				/***************************1.接收請求參數****************************************/
				Integer showTimesId = Integer.valueOf(req.getParameter("showTimesId"));
				
				/***************************2.開始查詢資料****************************************/
				MovieTimeService movieTimeSvc = new MovieTimeService();
				MovieTimeVO movieTimeVO = movieTimeSvc.getOneMovieTime(showTimesId);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("movieTimeVO", movieTimeVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/movietime/updatetime.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);
		}
		
		if ("getKey_Word".equals(action)) { // 來自select_page.jsp的請求
		    /***************************1.接收請求參數****************************************/
		    String keyword = req.getParameter("keyword");

		    /***************************2.開始查詢資料*****************************************/
		    MovieTimeService movieTimeSvc = new MovieTimeService();
		    Map<String, List<?>> result = new HashMap<>();
		    if (keyword == null || keyword.trim().isEmpty()) {
		        // 如果關鍵字為空，則查詢全部資料
		        result.put("movieTimes", movieTimeSvc.getAllMovieTime());
		        result.put("movies", new ArrayList<MovieVO>()); // 空列表
		    } else {
		        // 否則根據關鍵字查詢
		        result = movieTimeSvc.getMovieKeyword(keyword);
		    }

		    /***************************3.查詢完成,準備轉交(Send the Success view)*************/
		    req.setAttribute("result", result);
		    String url = "/back-end/movietime/time.jsp";
		    RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 keyword.jsp
		    successView.forward(req, res);
		}
		
		if ("getShowtimeDates".equals(action)) {
		    Integer movieId = Integer.valueOf(req.getParameter("movieId"));
		    MovieTimeService movieTimeService = new MovieTimeService();
		    Map<String, List<Integer>> showtimes = movieTimeService.getShowtimesByMovieId(movieId);

		    res.setContentType("application/json; charset=UTF-8");
		    res.getWriter().write(new Gson().toJson(showtimes));
		}

		
		


    }
}
