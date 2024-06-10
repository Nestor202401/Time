package com.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberVO;

@WebServlet("/getMemberName")
public class IndexMembeServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("有近來會員名稱");
		MemberVO member = (MemberVO) request.getSession().getAttribute("memberVO");
		String memberName = (member != null) ? member.getMemberName() : "";

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"memberName\": \"" + memberName + "\"}");
	}

}
