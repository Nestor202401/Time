package com.ticorder.controller;

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

import com.ticorder.model.TicketOrderService;
import com.ticorder.model.TicketOrderVO;

@WebServlet("/back-end/ticorder/TicketOrderServlet.do")
public class TicketOrderServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

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

            TicketOrderService ticOrderSvc = new TicketOrderService();
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
    }
}