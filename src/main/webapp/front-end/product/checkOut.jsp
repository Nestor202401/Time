<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>checkOut.jsp</title>
</head>
<body>
	<h1>結帳 - checkOut.jsp</h1>
	<h3>明細</h3>
	<table>
		<thead>
			<tr>
				<th>品項</th>
				<th>單價</th>
				<th>數量</th>
				<th>小計</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="cartVO" items="${cartList}">
				<tr>
					<td>${cartVO.prodName}</td>
					<td>${cartVO.unitPrice}</td>
					<td>${cartVO.prodCount}</td>
					<td>${cartVO.prodSum}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<h3>總價</h3>
	<label>${total}</label>
	
	<h3>寄送資料</h3>
	<form action="${pageContext.request.contextPath}/product_cart/cart.do" method="post">
		<table>
			<tbody>
				<tr>
					<td>收件人</td>
					<td><input type="text" name="recipient" value="${recipient}" required></td>
					<td>${errorMsgs.recipient}</td>
				</tr>
				<tr>
					<td>收件人地址</td>
					<td><input type="text" name="recAddr" value="${recAddr}" required></td>
					<td>${errorMsgs.recAddr}</td>
				</tr>
				<tr>
					<td>
						<input type="submit" value="信用卡結帳">
						<input type="hidden" name="action" value="goPay">
						<input type="hidden" name="total" value="${total}">
						<input type="hidden" name="memberId" value="${memberId}">
						<input type="hidden" name="ordStatus" value="0">						
						<%-- 
						 --%>
					</td>
				</tr>
			</tbody>	
		</table>
	</form>
	<br><br>
	<button onclick="window.location.href='${pageContext.request.contextPath}/front-end/product/shop.jsp'">																	
        回商城首頁
    </button>
</body>
</html>