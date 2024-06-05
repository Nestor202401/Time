package com.tictypes.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.tictypes.model.*;

@WebServlet("/back-end/tictypes/TicketTictypesServlet")
public class TicketTypesServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

				String str = req.getParameter("ticketTypesId");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入票種ID");
				}
	
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/tictypes/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				Integer ticketTypesId = null;
				try {
					ticketTypesId = Integer.valueOf(str);
				} catch (Exception e) {
					errorMsgs.add("票種ID格式不正確");
				}
		
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/tictypes/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
	
				TicketTypesService ticTypesSvc = new TicketTypesService();
				TicketTypesVO ticTypesVO = ticTypesSvc.getOneTicketTypes(ticketTypesId);
				if (ticTypesVO == null) {
					errorMsgs.add("查無資料");
				}
		
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/tictypes/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
		
				req.setAttribute("ticTypesVO", ticTypesVO); 
				String url = "/back-end/tictypes/listOneTicTypes.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
		}
		
		
		if ("getOne_For_Update".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
		
				Integer ticketTypesId = Integer.valueOf(req.getParameter("ticketTypesId"));
				
			
				TicketTypesService ticTypesSvc = new TicketTypesService();
				TicketTypesVO ticTypesVO = ticTypesSvc.getOneTicketTypes(ticketTypesId);
								
	
				req.setAttribute("ticTypesVO", ticTypesVO);        
				String url = "/back-end/tictypes/update_TicTypes_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
		}
		
		
		if ("update".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			
				Integer ticketTypesId = Integer.valueOf(req.getParameter("ticketTypesId").trim());
				
				String ticketTypeName = req.getParameter("ticketTypeName");
				String ticketTypeNameReg = "[\u4e00-\u9fa5]+";
				if (ticketTypeName == null || ticketTypeName.trim().length() == 0) {
					errorMsgs.add("票種：請勿空白");
				} else if(!ticketTypeName.trim().matches(ticketTypeNameReg)) { 
					errorMsgs.add("票種：只能輸入中文");
	            }
				
				Integer ticketPrice = null;
				try {
					ticketPrice = Integer.valueOf(req.getParameter("ticketPrice").trim());
				} catch (NumberFormatException e) {
					ticketPrice = 0;
					errorMsgs.add("票價請填入數字");
				}

				TicketTypesVO ticTypesVO = new TicketTypesVO();
				ticTypesVO.setTicketTypesId(ticketTypesId);
				ticTypesVO.setTicketTypeName(ticketTypeName);
				ticTypesVO.setTicketPrice(ticketPrice);

		
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ticTypesVO", ticTypesVO); 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/tictypes/update_TicTypes_input.jsp");
					failureView.forward(req, res);
					return; 
				}
				
				TicketTypesService ticTypesSvc = new TicketTypesService();
				ticTypesVO = ticTypesSvc.updateTicketTypes(ticketTypesId, ticketTypeName, ticketPrice);
				
				
				req.setAttribute("ticTypesVO", ticTypesVO);
				String url = "/back-end/tictypes/listOneTicTypes.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
		}

        if ("insert".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			
				String ticketTypeName = req.getParameter("ticketTypeName");
				String ticketTypeNameReg = "[\u4e00-\u9fa5]+";
				if (ticketTypeName == null || ticketTypeName.trim().length() == 0) {
					errorMsgs.add("票種：請勿空白");
				} else if(!ticketTypeName.trim().matches(ticketTypeNameReg)) { 
					errorMsgs.add("票種：只能輸入中文");
	            }
				
				Integer ticketPrice = null;
				try {
					ticketPrice = Integer.valueOf(req.getParameter("ticketPrice").trim());
				} catch (NumberFormatException e) {
					ticketPrice = 0;
					errorMsgs.add("票價請填入數字");
				}

				TicketTypesVO ticTypesVO = new TicketTypesVO();
				ticTypesVO.setTicketTypeName(ticketTypeName);
				ticTypesVO.setTicketPrice(ticketPrice);

		
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ticTypesVO", ticTypesVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/tictypes/addTicTypes.jsp");
					failureView.forward(req, res);
					return;
				}
				
	
				TicketTypesService ticTypesSvc = new TicketTypesService();
				ticTypesVO = ticTypesSvc.addTicketTypes(ticketTypeName, ticketPrice);
				
		
				String url = "/back-end/tictypes/listAllTicTypes.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);				
		}
		
		
		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
	
			
				Integer ticketTypesId = Integer.valueOf(req.getParameter("ticketTypesId"));
				
				
				TicketTypesService ticTypesSvc = new TicketTypesService();
				ticTypesSvc.deleteTicketTypes(ticketTypesId);
				
											
				String url = "/back-end/tictypes/listAllTicTypes.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
		}
		
	}
}
