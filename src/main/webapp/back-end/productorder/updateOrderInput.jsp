<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.product_order.model.*" %>

<%
	ProductOrderVO prodOrdVO = (ProductOrderVO) request.getAttribute("prodOrdVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>updateOrderInput.jsp</title>
</head>
<body>
	<h1>修改商品訂單 - updateOrderInput.jsp</h1>
	<form method="post" action="${pageContext.request.contextPath}/product_order/product_order.do">
		<table>
			<tbody>
				<tr>
					<th>訂單編號: </th>
					<td>${prodOrdVO.prodOrdId}</td>
				</tr>
				<tr>
					<th>會員編號: </th>
					<td>${prodOrdVO.member.memberId}</td>
				</tr>
				<tr>
             		<th>訂單狀態：</th>
             		<td>
             			<select name="ordStatus">
             				<option value="0" ${(prodOrdVO.ordStatus == 0) ? 'selected' : ''}>未完成</option>
             				<option value="1" ${(prodOrdVO.ordStatus == 1) ? 'selected' : ''}>已完成</option>
             			</select>
             		</td>
				</tr>
				<tr>
					<th>訂單成立時間: </th>
					<td>${prodOrdVO.estTime}</td>
				</tr>
				<tr>
					<th>收件人姓名: </th>
					<td>
						<input type="text" name="recipient" value="${prodOrdVO.recipient}" required>
					</td>
				</tr>
				<tr>
					<th>收件人地址: </th>
					<td>
						<input type="text" name="recAddr" value="${prodOrdVO.recAddr}" required>
					</td>
				</tr>
				<tr>
					<th>總額: </th>
					<td>
						<input type="number" name="total" value="${prodOrdVO.total}" required>
					</td>
				</tr>
			</tbody>
		</table>
		<button type="submit">送出修改</button>
		<input type="hidden" name="prodOrdId" value="${prodOrdVO.prodOrdId}">
		<input type="hidden" name="memberId" value="${prodOrdVO.member.memberId}">
		<input type="hidden" name="estTime" value="${prodOrdVO.estTime}">
		<input type="hidden" name="action" value="update">
		<a href="${pageContext.request.contextPath}/back-end/productorder/order.jsp">
			<button type="button">取消</button>
		</a>
	</form>
</body>
</html>