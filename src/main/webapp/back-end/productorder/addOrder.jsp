<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product_order.model.*"%>

<% 
	ProductOrderVO prodOrdVO = (ProductOrderVO) request.getAttribute("prodOrdVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>addOrder.jsp</title>
</head>
<body>
	<h1>新增商品訂單 - addOrder.jsp</h1>
	<form method="post" action="${pageContext.request.contextPath}/product_order/product_order.do">
		<table>
			<tbody>
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
		<input type="hidden" name="ordStatus" value="0">
		<input type="hidden" name="action" value="insert">
		<a href="${pageContext.request.contextPath}/back-end/productorder/order.jsp">
			<button type="button">取消</button>
		</a>
	</form>
</body>
</html>