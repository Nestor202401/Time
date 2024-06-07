package com.cinema.controller;

import java.io.IOException;
import java.util.Arrays;
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
import com.cinema.model.CinemaVO;
import com.movieimg.model.MovieImgService;

@WebServlet("/back-end/cinema/cinema.do")
public class CinemaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private CinemaService cinemaService = new CinemaService();

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		// 新建sql影廳座位表
		if ("generate_seat_map".equals(action)) {
			String cinemaName = req.getParameter("cinemaName");
			int rows = Integer.parseInt(req.getParameter("rows"));
			int columns = Integer.parseInt(req.getParameter("columns"));
			cinemaService.addSeatMap(cinemaName, rows, columns);
			req.getRequestDispatcher("/back-end/cinema/success.jsp").forward(req, res);
		}

		// 新建redis場次座位
		if ("create_session".equals(action)) {
			String sessionId = req.getParameter("sessionId");
			String cinemaName = req.getParameter("cinemaName");
			cinemaService.createSessionSeats(sessionId, cinemaName);
			req.getRequestDispatcher("/back-end/cinema/success.jsp").forward(req, res);
		}

		// 取得影廳名稱種類
		if ("get_cinema_types".equals(action)) {
			List<CinemaVO> cinemaTypes = cinemaService.getCinemaNamesType();
			req.setAttribute("cinemaTypes", cinemaTypes);
			req.getRequestDispatcher("/back-end/cinema/createSession.jsp").forward(req, res);
		}

		// 查詢場次取得redis座位表
		if ("query_session".equals(action)) {
			String sessionId = req.getParameter("sessionId");
			List<Map<String, String>> seats = cinemaService.getSessionSeats(sessionId);
			req.setAttribute("sessionId", sessionId); // 設置 sessionId
			req.setAttribute("seats", seats);
			req.getRequestDispatcher("/back-end/cinema/queryResult.jsp").forward(req, res);
		}

		// 根據影廳名稱查詢座位表
		if ("by_cinemaName".equals(action)) {
			String cinemaName = req.getParameter("cinemaName");
			String type = req.getParameter("type");
			List<CinemaVO> cinemaVO = cinemaService.getCinemaTemplate(cinemaName);
			if ("edit_cinema".equals(type)) {
				req.setAttribute("cinemaVO", cinemaVO);
				req.getRequestDispatcher("/back-end/cinema/main.jsp").forward(req, res);
			}else if ("submit_cinema".equals(type)) {
				req.setAttribute("cinemaVO", cinemaVO);
				req.getRequestDispatcher("/back-end/cinema/cinema.jsp").forward(req, res);
			}
		}

		// 更新redis座位狀態
		if ("update_seat_status".equals(action)) {
			String sessionId = req.getParameter("sessionId");
			String seatNumbers = req.getParameter("seatNumbers");
			String[] seats = seatNumbers.split(",");
			int status = Integer.parseInt(req.getParameter("status"));
			for (String seatNumber : seats) {
				cinemaService.updateSeatStatus(sessionId, seatNumber, status);
			}
			req.setAttribute("seatNumbers", seatNumbers);
			req.setAttribute("sessionId", sessionId);
			req.getRequestDispatcher("/back-end/cinema/success.jsp").forward(req, res);
		}

		// 用於批量修改sql座位狀態
		if ("update_seat_status_batch".equals(action)) {
			String cinemaName = req.getParameter("cinemaName");
			String[] seatNumbersArray = req.getParameter("seatNumbers").split(",");
			int newStatus = Integer.parseInt(req.getParameter("newStatus"));

			// 打印接收到的參數
			System.out.println("Cinema Name: " + cinemaName);
			System.out.println("Seat Numbers: " + Arrays.toString(seatNumbersArray));
			System.out.println("New Status: " + newStatus);

			List<String> seatNumbers = Arrays.asList(seatNumbersArray);
			cinemaService.updateSeatStatusBatch(cinemaName, seatNumbers, newStatus);
			req.getRequestDispatcher("/back-end/cinema/cinema.jsp").forward(req, res);
		}

		

	}
}
