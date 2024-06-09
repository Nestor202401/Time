package com.ticorder.controller;

import java.io.IOException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
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

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.cinema.model.*;
import com.movietime.model.*;
import com.member.model.*;
import com.ticlist.model.*;
import com.movie.model.MovieVO;
import com.ticorder.model.*;
import com.tictypes.model.*;
import com.util.HibernateUtil;
import com.util.JedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@WebServlet("/back-end/ticorder/TicketOrderServlet.do")
public class TicketOrderServlet extends HttpServlet {
	
	private Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        
        TicketOrderService ticOrderSvc = new TicketOrderService();
        TicketTypesService ticTypesSvc = new TicketTypesService();
        CinemaService cinemaSvc = new CinemaService();
        
        
        
        //訂單複合查詢
        if ("listTicketOrder_ByCompositeQuery".equals(action)) {
            List<String> errorMsgs = new LinkedList<String>();

            /*************************** 1.接收請求參數 ****************************************/
            String movieOrderId = req.getParameter("movieOrderId");
            String memberAccount = req.getParameter("memberAccount");

            /*************************** 2.開始查詢資料 ****************************************/
            Map<String, String[]> map = new HashMap<String, String[]>();

            if (movieOrderId != null && movieOrderId.trim().length() > 0) {
                map.put("movieOrderId", new String[]{movieOrderId});
            }
            if (memberAccount != null && memberAccount.trim().length() > 0) {
                map.put("memberAccount", new String[]{memberAccount});
            }

            List<TicketOrderVO> list = ticOrderSvc.getAll(map);

            if (list.isEmpty()) {
                errorMsgs.add("查無資料");
            }

            /*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
            req.setAttribute("ticOrderListData", list);
            req.setAttribute("errorMsgs", errorMsgs);

            RequestDispatcher successView = req.getRequestDispatcher("/back-end/ticorder/select_page.jsp");
            successView.forward(req, res);
        }
        
    	// 前端下拉選單動態抓取上映電影功能
 		if ("getAllShowingMovieNames".equals(action)) {
             res.setContentType("application/json");
             res.setCharacterEncoding("UTF-8");
             Gson gson = new Gson();
             String json = gson.toJson(ticOrderSvc.getAllShowingMovieNames());
             res.getWriter().write(json);          
         }
     		
 		// 前端下拉選單動態抓取日期功能
 		if ("getShowTimeDatesByMovieId".equals(action)) {
 		    Integer movieId = Integer.parseInt(req.getParameter("movieId"));
 		    List<Date> showTimeDates = ticOrderSvc.getShowTimeDatesByMovieId(movieId);

 		    Gson gson = new Gson();
 		    String json = gson.toJson(showTimeDates);
 		    res.setContentType("application/json");
 		    res.setCharacterEncoding("UTF-8");
 		    res.getWriter().write(json);
 		    
 		}
 		
 		// 前端下拉選單動態抓取場次功能
 		if ("getShowTimesByMovieIdAndDate".equals(action)) {
 		    Integer movieId = Integer.parseInt(req.getParameter("movieId"));
 		    Date showTimeDate = Date.valueOf(req.getParameter("showTimeDate"));
 		    List<Integer> showTimes = ticOrderSvc.getShowTimesByMovieIdAndDate(movieId, showTimeDate);
 		    // 將時間列表轉換為 JSON 格式並發送給前端
 		    Gson gson = new Gson();
 		    String json = gson.toJson(showTimes);
 		    res.setContentType("application/json");
 		    res.setCharacterEncoding("UTF-8");
 		    res.getWriter().write(json);
 		}
 		
 		//Redis存入第一個頁面會員的電影.日期.場次.場次ID
 		if ("saveMemberdData_Movie".equals(action)) {
            String movieId = req.getParameter("movieId");
            String showTimeDateStr = req.getParameter("showTimeDate");
            String showTimeStr = req.getParameter("showTime");
            
            Date showTimeDate = Date.valueOf(showTimeDateStr);
            Integer showTime = Integer.parseInt(showTimeStr);

            MovieTimeVO movieTimeVO = ticOrderSvc.getShowTimeIdByMovieIdAndDateAndTime(Integer.parseInt(movieId), showTimeDate, showTime);
            
            Integer showTimesId = movieTimeVO.getShowTimesId();
            String moviePlaybackType = null;
            switch(movieTimeVO.getMoviePlaybackType()) {
            case 0:
            	moviePlaybackType = "數位版";
                break;
            case 1:
            	moviePlaybackType = "3D版";
                break;
            };
            
            JedisPool pool = JedisUtil.getJedisPool();
            Jedis jedis = pool.getResource();
//            String sessionId = req.getSession().getId(); //會員

            Map<String, String> member_data = new HashMap<>();
            member_data.put("movieId", movieId);
            member_data.put("showTimeDate", showTimeDateStr);
            member_data.put("showTime", showTimeStr);
            member_data.put("showTimesId", showTimesId.toString());
            member_data.put("moviePlaybackType", moviePlaybackType);
            jedis.hmset("memberId:" + 3, member_data); //會員

            jedis.close(); // Make sure to close the Jedis instance

            res.getWriter().write("success");
        }
 		
 		//第二個頁面將會員的電影.日期.場次從Redis抓出印出在頁面
 		if ("getMemberData_Movie".equals(action)) {
 		    // 從 Redis 中獲取存儲的數據
 		    JedisPool pool = JedisUtil.getJedisPool();
 		    Jedis jedis = pool.getResource();
// 		    String sessionId = req.getSession().getId(); //會員
 		    Map<String, String> member_data = jedis.hgetAll("memberId:" + 3); //會員
 		    
 		    String showTimeDate = member_data.get("showTimeDate");
 		    String showTime = member_data.get("showTime");
 		    String moviePlaybackType = member_data.get("moviePlaybackType");
 		    String showTimesId = member_data.get("showTimesId");

 		    // 使用 getMovieNameById 方法獲取電影名稱
 		    String movieId = member_data.get("movieId");
 		    String movieName = ticOrderSvc.getMovieNameById(movieId);
 		    
 		    Map<String, String> data = new HashMap<>();
 		    data.put("movieName", movieName);
 		    data.put("showTimeDate", showTimeDate);
 		    data.put("showTime", showTime);
 		    data.put("moviePlaybackType", moviePlaybackType);
 		    data.put("showTimesId", showTimesId);
 		    
 		    jedis.close();
 		    
 		    // 將數據封裝成 JSON 返回給前端
 		    Gson gson = new Gson();
 		    String json = gson.toJson(data);
 		    res.setContentType("application/json");
 		    res.setCharacterEncoding("UTF-8");
 		    res.getWriter().write(json);
 		}
 		
 		if ("getAllTicketTypes".equals(action)) {
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            String json = new Gson().toJson(ticTypesSvc.getAll());
            res.getWriter().write(json);          
        }
 		
 		
 		//取得
 		if ("calculateTotal".equals(action)) {
 		// 取得票種ID和數量
 	        String[] ticketTypeIds = req.getParameterValues("ticketTypeIds");
 	        String[] quantities = req.getParameterValues("quantities");

 	        // 從資料庫中取得所有票種資訊
 	        List<TicketTypesVO> ticketTypes = ticTypesSvc.getAll();
 	        Map<Integer, Integer> ticketPriceMap = new HashMap<>();
 	        for (TicketTypesVO ticketType : ticketTypes) {
 	            ticketPriceMap.put(ticketType.getTicketTypesId(), ticketType.getTicketPrice());
 	        }
 	        Map<Integer, String> ticketNameMap = new HashMap<>();
 	        for (TicketTypesVO ticketType : ticketTypes) {
 	    	    ticketNameMap.put(ticketType.getTicketTypesId(), ticketType.getTicketTypeName());
	        }

 	        int totalAmount = 0;
 	        int totalQuantity = 0;
 	        List<Map<String, Object>> ticketSummary = new ArrayList<>();

 	        // 計算總金額並生成票種摘要
 	        for (int i = 0; i < ticketTypeIds.length; i++) {
 	        	int ticketTypeId = Integer.parseInt(ticketTypeIds[i]);
 	        	String ticketTypeName = ticketNameMap.get(ticketTypeId);
 	            int quantity = Integer.parseInt(quantities[i]);
 	            int ticketPrice = ticketPriceMap.get(ticketTypeId);
 	            int amount = quantity * ticketPrice;
 	            totalQuantity += quantity;
 	            totalAmount += amount;

 	            Map<String, Object> ticketInfo = new HashMap<>();
 	            ticketInfo.put("ticketTypeId", ticketTypeId);
 	            ticketInfo.put("ticketTypeName", ticketTypeName);
 	            ticketInfo.put("quantity", quantity);
 	            ticketInfo.put("amount", amount);
 	            ticketSummary.add(ticketInfo);
 	        }

 	        // 準備回傳結果
 	        Map<String, Object> result = new HashMap<>();
 	        result.put("totalQuantity", totalQuantity);
 	        result.put("totalAmount", totalAmount);
 	        result.put("ticketSummary", ticketSummary);

 	        // 設定回應型態為 JSON
 	        res.setContentType("application/json");
 	        res.setCharacterEncoding("UTF-8");

 	        // 回傳結果
 	        res.getWriter().write(new Gson().toJson(result));
 	    }
 		
 		//Redis存入第二個頁面選擇的票種及數量
 		if ("saveMemberdData_Ticket".equals(action)) {
            String ticketDataJson = req.getParameter("ticketData");
            
         // 使用 Gson 將 JSON 字符串轉換為 Java 對象
            Gson gson = new Gson();
            List<TicketTypesVO> ticketData = gson.fromJson(ticketDataJson, new TypeToken<List<TicketTypesVO>>(){}.getType());
            
            int i = 1;
            int totalQuantityN = 0;
            JedisPool pool = JedisUtil.getJedisPool();
            Jedis jedis = pool.getResource();
//            String sessionId = req.getSession().getId(); //會員
            Map<String, String> member_data = new HashMap<>();
            
            // 遍歷和處理數據
            for (TicketTypesVO TicketTypesVO : ticketData) {
            String ticketTypeName = TicketTypesVO.getTicketTypeName();
            int quantity = TicketTypesVO.getQuantity();
            totalQuantityN += quantity;
            String ticketTypeQuantity = ""+quantity;
            
            // 處理數據，例如存儲到數據庫
            member_data.put("ticketTypeName"+i, ticketTypeName);
            member_data.put("ticketTypeQuantity"+i, ticketTypeQuantity);
            i++;
            }
            
            String totalQuantity = ""+totalQuantityN;
            member_data.put("totalQuantity", totalQuantity);
            jedis.hmset("memberId:" + 3, member_data); //會員
            jedis.close();

            res.getWriter().write("success");
        }
 		
 		if ("get_seats".equals(action)) {
            String showTimesId = req.getParameter("showTimesId");
            List<Map<String, String>> seats = cinemaSvc.getSessionSeats(showTimesId);
            
            Gson gson = new Gson();
            String json = gson.toJson(seats);
            
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(json);
        }
 		
 		if ("get_totalQuantity".equals(action)) {
 			JedisPool pool = JedisUtil.getJedisPool();
 		    Jedis jedis = pool.getResource();
// 		    String sessionId = req.getSession().getId();
 		    Map<String, String> member_data = jedis.hgetAll("memberId:" + 3); //會員
 		    String totalQuantity = member_data.get("totalQuantity");
 		    
 		    Map<String, String> data = new HashMap<>();
		    data.put("totalQuantity", totalQuantity);
		    jedis.close();
            
            Gson gson = new Gson();
            String json = gson.toJson(data);
            
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(json);
        }
 		
 		if ("saveMemberdData_SeatNumber".equals(action)) {
 			String showTimesId = req.getParameter("showTimesId");
 			String[] seatNumbers = req.getParameterValues("seatNumbers[]");
 			String[] seats = null;
 			for(int i=0;i < seatNumbers.length; i++) {
 				seats = seatNumbers[i].split(",");
            }
            int status = Integer.parseInt(req.getParameter("status"));
            for (String seatNumber : seats) {
            	cinemaSvc.updateSeatStatus(showTimesId, seatNumber, status);
            }
            
            Gson gson = new Gson();
            String seatNumbersJson = gson.toJson(seatNumbers);
            
            JedisPool pool = JedisUtil.getJedisPool();
            Jedis jedis = pool.getResource();
//            String sessionId = req.getSession().getId(); //會員

            Map<String, String> member_data = new HashMap<>();
            member_data.put("seatNumbers", seatNumbersJson);
            jedis.hmset("memberId:" + 3, member_data); //會員
            jedis.close(); 

            res.getWriter().write("success");
        }
 		
 		if ("getMemberData_SeatNumber".equals(action)) {
 		    // 從 Redis 中獲取存儲的數據
 		    JedisPool pool = JedisUtil.getJedisPool();
 		    Jedis jedis = pool.getResource();
// 		    String sessionId = req.getSession().getId(); //會員
 		    Map<String, String> member_data = jedis.hgetAll("memberId:" + 3); //會員
 		    
 		    String seatNumbersJson = member_data.get("seatNumbers")
 		    		.replace("[", "")
 		    		.replace("]", "")
 		    		.replace("\"", "");
           
 		    String[] seatNumbersArray = seatNumbersJson.split(",");
 		    String seatNumbers = String.join("|", seatNumbersArray);
 		    
 		    Map<String, String> data = new HashMap<>();
		    data.put("seatNumbers", seatNumbers);
		    jedis.close();
		    
 		    // 將數據封裝成 JSON 返回給前端
 		    Gson gson = new Gson();
 		    String json = gson.toJson(data);
 		    res.setContentType("application/json");
 		    res.setCharacterEncoding("UTF-8");
 		    res.getWriter().write(json);
 		}
 		
 		if ("getMemberData_Ticketlist".equals(action)) {
 		    // 從 Redis 中獲取存儲的數據
 		    JedisPool pool = JedisUtil.getJedisPool();
 		    Jedis jedis = pool.getResource();
// 		    String sessionId = req.getSession().getId(); //會員
 		    Map<String, String> member_data = jedis.hgetAll("memberId:" + 3); //會員
 		    
 		    int ticketTypeNameCount = 0;
            for (String key : member_data.keySet()) {
                if (key.startsWith("ticketTypeName")) {
                    ticketTypeNameCount++;
                }
            }
            
            List<TicketTypesVO> ticketTypes = ticTypesSvc.getAll();
            Map<String, Integer> ticketPriceMap = new HashMap<>();
 	        for (TicketTypesVO ticketType : ticketTypes) {
 	            ticketPriceMap.put(ticketType.getTicketTypeName(), ticketType.getTicketPrice());
 	        }
            
           	int totalAmount = 0;
	        List<Map<String, Object>> ticketSummary = new ArrayList<>();
	        for (int i = 1; i <= ticketTypeNameCount; i++) {
	        	String ticketTypeName = member_data.get("ticketTypeName"+i);
	        	int ticketPrice = ticketPriceMap.get(ticketTypeName);
	        	String ticketTypeQuantity = member_data.get("ticketTypeQuantity"+i);
 	            int quantity = Integer.parseInt(ticketTypeQuantity);
 	            int amount = quantity * ticketPrice;
 	            totalAmount += amount;

 	            Map<String, Object> ticketInfo = new HashMap<>();
 	            ticketInfo.put("ticketTypeName", ticketTypeName);
 	            ticketInfo.put("quantity", quantity);
 	            ticketInfo.put("amount", amount);
 	            ticketSummary.add(ticketInfo);
 	        }
	        
	        Map<String, Object> result = new HashMap<>();
 	        result.put("totalAmount", totalAmount);
 	        result.put("ticketSummary", ticketSummary);
 	        jedis.close();
 		    // 將數據封裝成 JSON 返回給前端
 		    Gson gson = new Gson();
 		    String json = gson.toJson(result);
 		    res.setContentType("application/json");
 		    res.setCharacterEncoding("UTF-8");
 		    res.getWriter().write(json);
 		}
 		
 		if ("addOrderData".equals(action)) {
 			String movieOrderTotal_Data = req.getParameter("movieOrderTotal");
 			
 			JedisPool pool = JedisUtil.getJedisPool();
 		    Jedis jedis = pool.getResource();
// 		    String sessionId = req.getSession().getId(); //會員
 		    Map<String, String> member_data = jedis.hgetAll("memberId:" + 3); //會員
 		    
 		    MemberVO memberId = ticOrderSvc.findMemberById(3);
		    int movieOrderTotal = Integer.parseInt(movieOrderTotal_Data);
		    Integer movieOrderId = ticOrderSvc.addOrderData(memberId, movieOrderTotal);
//		    
		    int movieId = Integer.parseInt(member_data.get("movieId"));
 		    
 		    int showTimesId = Integer.parseInt(member_data.get("showTimesId"));
 		    
 		    String seatNumbersJson = member_data.get("seatNumbers")
		    		.replace("[", "")
		    		.replace("]", "")
		    		.replace("\"", "");
          
		    String[] seatNumbersArray = seatNumbersJson.split(",");		    
		    
		    List<TicketTypesVO> ticketTypes = ticTypesSvc.getAll();
 	        Map<String, Integer> ticketIdMap = new HashMap<>();
 	        for (TicketTypesVO ticketType : ticketTypes) {
 	            ticketIdMap.put(ticketType.getTicketTypeName(), ticketType.getTicketTypesId());
 	        }
		    
		    //票名 拿票ID用
 	       int ticketTypeNameCount = 0;
	 	      for (String key : member_data.keySet()) {
	              if (key.startsWith("ticketTypeName")) {
	                  ticketTypeNameCount++;
	              }
	          }
	 	    Transaction transaction = getSession().beginTransaction();

	 	    int k = 0;
	        for (int i = 1; i <= ticketTypeNameCount; i++) {
	        	String ticketTypeName = member_data.get("ticketTypeName"+i);
	        	int ticketTypesId = ticketIdMap.get(ticketTypeName);
	        	String ticketTypeQuantity = member_data.get("ticketTypeQuantity"+i);
 	            int quantity = Integer.parseInt(ticketTypeQuantity);
 	            
 	            for(int j = 0; j< quantity; j++) {
 	            	TicketListVO ticketList = new TicketListVO();
 	            	ticketList.setMovieOrderId(movieOrderId);	
 	            	ticketList.setMovieId(movieId);
 	            	ticketList.setTicketTypesId(ticketTypesId);
 	            	ticketList.setShowTimesId(showTimesId);
 	            	ticketList.setSeatNumber(seatNumbersArray[k]);
 	            	ticketList.setTicketStatus(false);
 	            	getSession().save(ticketList);
 	            	k++;
 	            }
	        }
	        
	        transaction.commit();
            res.getWriter().write("success");
        }
     		
    }
}