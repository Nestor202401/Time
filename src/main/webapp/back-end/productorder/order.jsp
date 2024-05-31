<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product_order.model.*"%>

<%
	ProductOrderService prodOrdSvc = new ProductOrderService();
	List<ProductOrderVO> orderList = prodOrdSvc.getAll();
	request.setAttribute("orderList", orderList);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>order.jsp</title>
</head>
<body>
	<h1>商品訂單列表 - listProd.jsp</h1>
	
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
				<th>修改</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="prodOrdVO" items="${orderList}">
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
					<td>
						<%-- 
						<form method="post" action="<%=request.getContextPath() %>/product_order/product_order.do">
						--%>
						<form method="post" action="${pageContext.request.contextPath}/product_order/product_order.do"> 
							<button type="submit">修改</button>
							<input type="hidden" name="prodOrdId" value="${prodOrdVO.prodOrdId}">
							<input type="hidden" name="action" value="getOne_For_Update">
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<a href="${pageContext.request.contextPath}/back-end/productorder/addOrder.jsp">
		<button type="submit" class="btn btn-block bg-gradient-success">新增訂單</button>
	</a>
</body>
</html>