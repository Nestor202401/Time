package com.cinema.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cinema.model.CinemaService;
import com.cinema.model.CinemaVO;

@WebServlet("/back-end/cinema/cinema.do")
public class CinemaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CinemaService cinemaService = new CinemaService();

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("create_session".equals(action)) {
            String sessionId = req.getParameter("sessionId");
            String cinemaName = req.getParameter("cinemaName");
            cinemaService.createSessionSeats(sessionId, cinemaName);
            req.getRequestDispatcher("/back-end/cinema/success.jsp").forward(req, res);
        } else if ("get_cinema_types".equals(action)) {
            List<CinemaVO> cinemaTypes = cinemaService.getCinemaNamesType();
            req.setAttribute("cinemaTypes", cinemaTypes);
            req.getRequestDispatcher("/back-end/cinema/createSession.jsp").forward(req, res);
        } else if ("query_session".equals(action)) {
            String sessionId = req.getParameter("sessionId");
            List<Map<String, String>> seats = cinemaService.getSessionSeats(sessionId);
            req.setAttribute("sessionId", sessionId);  // 设置 sessionId
            req.setAttribute("seats", seats);
            req.getRequestDispatcher("/back-end/cinema/queryResult.jsp").forward(req, res);
        } else if ("by_cinemaName".equals(action)) {
            String cinemaName = req.getParameter("cinemaName");
            List<CinemaVO> cinemaVO = cinemaService.getCinemaTemplate(cinemaName);
            req.setAttribute("cinemaVO", cinemaVO);
            req.getRequestDispatcher("/back-end/cinema/cinemaResult.jsp").forward(req, res);
        } else if ("update_seat_status".equals(action)) {  // 添加的操作
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
    }
}
