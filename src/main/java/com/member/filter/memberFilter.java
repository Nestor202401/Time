package com.member.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class memberFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("filter啟動了!");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res= (HttpServletResponse) response;
		HttpSession session = req.getSession();
		 Object memberVO = session.getAttribute("memberVO");
		 if (memberVO == null) {
//				session.setAttribute("location", req.getRequestURI());
			 req.setAttribute("noLogIn", "請先登入會員!");
			 req.getRequestDispatcher("/front-end/member/memberLogin.jsp").forward(req, res);			
				return;
			} else {
				chain.doFilter(request, response);
			}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Filter.super.destroy();
	}

	
}
