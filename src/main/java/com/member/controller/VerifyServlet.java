package com.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberService;

public class VerifyServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	System.out.println("有近來");
    	MemberService memsvc=new MemberService();
        String token = req.getParameter("token");
        boolean verified=memsvc.verify(token);
        if(verified) {
		    req.setAttribute("verifySuccess", "帳號已成功開通，請登入!");
		    String url = "front-end/member/memberLogin.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);       
    }
}
    
    
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	
    	
    	
    	
    	
    	
    	
    }
    
    
    
}