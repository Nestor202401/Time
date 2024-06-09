package com.member.model;

import javax.servlet.http.HttpServletRequest;
//用來獲取請求的地址，並回傳
public class UrlUtility {

    public static String getSiteUrl(HttpServletRequest request) {
    	/*
    	request.getRequestURL().toString();將會得到請求的全部路徑 EX:http://localhost:8080/member/From_Register
    	request.getRequestURI()會得到路徑部分 EX:/member/From_Register
    	再調用siteUrl.replace(request.getRequestURI(), ""),通過 replace() 方法將路徑部分替換為空字串，
    	就可以得到站點的URL，即 http://localhost:8080。*/
        String siteUrl = request.getRequestURL().toString();
        System.out.println("siteUrl="+siteUrl);
        return siteUrl.replace(request.getRequestURI(), "");
    }
	
}
