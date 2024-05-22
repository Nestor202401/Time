package com.movieimg.controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.movieimg.model.MovieImgService;
import com.movieimg.model.MovieImgVO;

@MultipartConfig
@WebServlet("/back-end/movieimg/movieimg.do")
public class MovieImgServlet extends HttpServlet {

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
			String str = req.getParameter("movieImgId");
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

			
			Integer movieImgId = null;
			try {
				movieImgId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("電影編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/movie/select_page2.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}

				
				/***************************2.開始查詢資料*****************************************/
				MovieImgService movieImgSvc = new MovieImgService();
				MovieImgVO movieImgVO = movieImgSvc.getOneMovieImg(movieImgId);
				if (movieImgVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/movie/select_page2.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("movieImgVO", movieImgVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/movie/listOneMovieImg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
				/***************************1.接收請求參數****************************************/
				Integer movieImgId = Integer.valueOf(req.getParameter("movieImgId"));
				
				/***************************2.開始查詢資料****************************************/
				MovieImgService movieImgSvc = new MovieImgService();
				MovieImgVO movieImgVO = movieImgSvc.getOneMovieImg(movieImgId);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("movieImgVO", movieImgVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/movieimg/updateimg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);
		}
		
		
		if ("update".equals(action)) {
		    List<String> errorMsgs = new LinkedList<String>();
		    req.setAttribute("errorMsgs", errorMsgs);

		    Integer movieImgId = Integer.valueOf(req.getParameter("movieImgId").trim());

		    Integer movieId = null;
		    try {
		        movieId = Integer.valueOf(req.getParameter("movieId").trim());
		    } catch (NumberFormatException e) {
		        movieId = 0; // 或其他適合的預設值
		        errorMsgs.add("電影ID請勿空白");
		    }

		    String movieImgName = req.getParameter("movieImgName").trim();
		    if (movieImgName == null || movieImgName.trim().length() == 0) {
		        errorMsgs.add("圖片名稱請勿空白");
		    }    

		    // 處理文件上傳
		    Part filePart = req.getPart("movieImgFile");
		    String movieImgFile = null;
		    if (filePart != null && filePart.getSize() > 0) {
		        String fileName = filePart.getSubmittedFileName();
		        String saveDirectory = "/images_uploaded";
		        String realPath = getServletContext().getRealPath(saveDirectory);
		        File fsaveDirectory = new File(realPath);
		        if (!fsaveDirectory.exists()) {
		            fsaveDirectory.mkdirs();
		        }

		        String absolutePath = realPath + File.separator + fileName;
		        try {
		            filePart.write(absolutePath);
		        } catch (IOException e) {
		            errorMsgs.add("文件上傳失敗：" + e.getMessage());
		        }

		        movieImgFile = req.getContextPath() + saveDirectory + "/" + fileName;
		    } else {
		        // 如果没有上传新文件，则保留现有的文件路径
		        movieImgFile = req.getParameter("currentImgFile");
		    }

		    MovieImgVO movieImgVO = new MovieImgVO();
		    movieImgVO.setMovieImgId(movieImgId);
		    movieImgVO.setMovieId(movieId);
		    movieImgVO.setMovieImgName(movieImgName);
		    movieImgVO.setMovieImgFile(movieImgFile);

		    // Send the user back to the form, if there were errors
		    if (!errorMsgs.isEmpty()) {
		        req.setAttribute("movieImgVO", movieImgVO); // 含有輸入格式錯誤的物件,也存入 req
		        RequestDispatcher failureView = req
		                .getRequestDispatcher("/back-end/movie/update_movieimg_input.jsp");
		        failureView.forward(req, res);
		        return; // 程式中斷
		    }

		    // 開始修改資料
		    MovieImgService movieImgSvc = new MovieImgService();
		    movieImgVO = movieImgSvc.updateMovieImg(movieImgId, movieId, movieImgName, movieImgFile);

		    // 修改完成,準備轉交
		    req.setAttribute("movieImgVO", movieImgVO); // 資料庫 update 成功後, 正確的的物件, 存入 req
		    String url = "/back-end/movie/Success.html";
		    RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交 listOneEmp.jsp
		    successView.forward(req, res);
		}



		if ("insert".equals(action)) { // 來自addEmp.jsp的請求  

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            Integer movieId = null;
            try {
                movieId = Integer.valueOf(req.getParameter("movieId").trim());
            } catch (NumberFormatException e) {
                movieId = 0;
                errorMsgs.add("電影ID請勿空白");
            }

            int fileCount = 0;
            while (req.getPart("file" + fileCount) != null) {
                fileCount++;
            }

            if (fileCount == 0) {
                errorMsgs.add("請選擇要上傳的文件");
            }

            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req.getRequestDispatcher("/back-end/movieimg/addMovieImg.jsp");
                failureView.forward(req, res);
                return;
            }

            MovieImgService movieImgSvc = new MovieImgService();

            for (int i = 0; i < fileCount; i++) {
                Part filePart = req.getPart("file" + i);
                String movieImgName = req.getParameter("name" + i);
                String fileName = filePart.getSubmittedFileName();
                String saveDirectory = "/images_uploaded";
                String contextPath = req.getContextPath();
                String relativePath = contextPath + saveDirectory + "/" + fileName;
                String realPath = getServletContext().getRealPath(saveDirectory);
                File fsaveDirectory = new File(realPath);
                if (!fsaveDirectory.exists()) {
                    fsaveDirectory.mkdirs();
                }
                String absolutePath = realPath + File.separator + fileName;
                try {
                    filePart.write(absolutePath);
                } catch (IOException e) {
                    errorMsgs.add("文件上傳失敗：" + e.getMessage());
                }

                MovieImgVO movieImgVO = new MovieImgVO();
                movieImgVO.setMovieId(movieId);
                movieImgVO.setMovieImgName(movieImgName);
                movieImgVO.setMovieImgFile(relativePath);
                movieImgSvc.addMovieImg(movieId, movieImgName, relativePath);
            }

            String url = "/back-end/movie/Success.html";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);	
		}

		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
				/***************************1.接收請求參數***************************************/
				Integer movieImgId = Integer.valueOf(req.getParameter("movieImgId"));
				
				/***************************2.開始刪除資料***************************************/
				MovieImgService movieImgSvc = new MovieImgService();
				movieImgSvc.deleteMovieImg(movieImgId);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/movie/listAllMovieImg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
		}
		
		if ("getKey_Word".equals(action)) { // 來自select_page.jsp的請求
	        /***************************1.接收請求參數****************************************/
	        String keyword = req.getParameter("keyword");

	        /***************************2.開始查詢資料*****************************************/
	        MovieImgService movieImgSvc = new MovieImgService();
	        List<MovieImgVO> movieImgVOList;
	        if (keyword == null || keyword.trim().isEmpty()) {
	            // 如果關鍵字為空，則查詢全部資料
	            movieImgVOList = movieImgSvc.getAll();
	        } else {
	            // 否則根據關鍵字查詢
	            movieImgVOList = movieImgSvc.getKeyword(keyword);
	        }

	        /***************************3.查詢完成,準備轉交(Send the Success view)*************/
	        req.setAttribute("movieImgVOList", movieImgVOList); // 資料庫取出的movieImgVO物件,存入req
	        String url = "/back-end/movieimg/keyword.jsp";
	        RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 keyword.jsp
	        successView.forward(req, res);
		}
	}
}
