<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product_detail.model.*"%>

<% 	// 見 controller.DetailServlet.java, *() 存入 req 的 prodDetail obj
	// (此為輸入格式有誤的 obj) ?
	ProductDetail prodDetail = (ProductDetail) request.getAttribute("prodDetail");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>明細資料新增 - addDetail.jsp</title>
<style>
	table#table-1 {
		background-color: #CCCCFF;
		border: 2px solid black;
		text-align: center;
	}
	table#table-1 h4 {
		color: red;
		display: block;
		margin-bottom: 1px;
	}
	h4 {
		color: blue;
		display: inline;
	}
	table {
		width: 450px;
		background-color: white;
		margin-top: 1px;
		margin-bottom: 1px;
	}
	table, th, td {
		border: 0px solid #CCCCFF;
	}
	th, td {
		padding: 1px;
	}
	img#back {
		width: 100px; 
		height: 32px; 
		border: 0;
	}
</style>
</head>
<body bgcolor='white'>
	<table id="table-1">
		<tr>
			<td>
				<h3>明細資料新增 - addDetail.jsp</h3>
				<h4><a href='selectPage.jsp'><img id="back" src="images/back1.gif">回首頁</a></h4>
			</td>
		</tr>
	</table>
	
	<h3>資料新增: 明細為依賴訂單產生，暫不實作手動新增</h3>
	<%-- ErrorMsgs list --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
</body>
</html>