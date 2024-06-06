<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product_order.model.*"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>

<%
  ProductOrderVO prodOrdVO = (ProductOrderVO) request.getAttribute("prodOrdVO"); //OrderServlet.java(Concroller), 存入req的prodOrdVO物件
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>listOneOrder.jsp</title>
</head>
<body>
	<h1>列出商品訂單 - listOneOrder.jsp</h1>
	<table>
		<thead>
			<tr>
				<th>商品訂單 ID</th>
				<th>會員 ID</th>
				<th>訂單成立時間</th>
				<th>訂單狀態</th>
				<th>總額</th>
				<th>收件人姓名</th>
				<th>收件人地址</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${prodOrdVO.prodOrdId}</td>
				<td>${prodOrdVO.member.memberId}</td>
				<td>${prodOrdVO.estTime}</td>
				<td>
					<c:if test = "${prodOrdVO.ordStatus == 0}">
						未完成
					</c:if>
					<c:if test = "${prodOrdVO.ordStatus == 1}">
						已完成
					</c:if>
				</td>
				<td>${prodOrdVO.total}</td>
				<td>${prodOrdVO.recipient}</td>
				<td>${prodOrdVO.recAddr}</td>
			</tr>		
		</tbody>
	</table>
	<a href="${pageContext.request.contextPath}/back-end/productorder/order.jsp">
		<button type="button">回訂單列表</button>
	</a>
</body>
</html>