<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product_order.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>
<%
	ProductOrderService detailSvc = new ProductOrderService(); 
	/* 此行的 list 物件將提供 page1.file 的 (rowNum = list.size();) 取得查詢到的總筆數，再由 page1.file 進行分頁 */
	List<ProductOrder> list = detailSvc.getAll();
	/* 將上一行的 list 變數(物件)存入當前頁面 pageContext，再由底下的 forEach 印出結果 */
	pageContext.setAttribute("list", list); 
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有訂單資料 - listAllOrder.jsp</title>
<style>
	table#table-1 {
		background-color: #CCCCFF;
		border: 2px;
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
		width: 800px;
		background-color: white;
		margin-top: 5px;
		margin-bottom: 5px;
	}
	table, th, td {
		border: 1px solid #CCCCFF;
	}
	th, td {
		padding: 5px;
		text-align: center;
	}
	img#back {
		width: 100px; 
		height: 32px; 
		border: 0;
	}
</style>
</head>
<body bgcolor='white'>
	<h4>此頁練習採用 EL 的寫法取值:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>所有訂單資料 - listAllOrder.jsp</h3>
				<h4><a href="selectPage.jsp">
					<img src="${pageContext.request.contextPath}/coi618Imgs/back1.gif"
					id="back">回首頁</a></h4>
				<%-- 
					<img src="${pageContext.request.contextPath}/images/back1.gif" 
				
					<img src="<%=request.getContextPath()%>/images/back1.gif"  # Worked
					<img src="${pageContext.request.contextPath}/images/back1.gif" # Worked
					<img src="images/back1.gif"
				 --%>
				 
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>商品訂單ID</th>
			<th>會員ID</th>
			<th>訂單成立時間</th>
			<th>訂單狀態</th>
			<th>總額</th>
			<th>收件人姓名</th>
			<th>收件人地址</th>

			<th>修改</th>
			<th>刪除</th>
		</tr>
		
		<%@ include file="page1.jp" %> 

		<c:forEach var="prodOrd" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
			<tr>
				<td>${prodOrd.prodOrdId}</td>
				<%-- To match member table, change VO 5/17 
				<td>${prodOrd.memId}</td>
				--%>
				<td>${prodOrd.memberId}</td>
				<td>${prodOrd.estTime}</td>
				<td>${prodOrd.ordStatus}</td>
				<td>${prodOrd.total}</td>
				<td>${prodOrd.recipient}</td>
				<td>${prodOrd.recAddr}</td>
				
				<td>
																	<%-- ^C from /url/pattern --%>
					<form method="post" action="<%=request.getContextPath()%>/order/order.do" style="margin-bottom:0px;">
						<input type="submit" value="修改">
						<input type="hidden" name="prodOrdId" value="${prodOrd.prodOrdId}">
						<input type="hidden" name="action" value="getOneForUpdate">
					</form>
				</td>
				<td>
					<form method="post" action="<%=request.getContextPath()%>/order/order.do" style="margin-bottom:0px;">
						<input type="submit" value="刪除">
						<input type="hidden" name="prodOrdId" value="${prodOrd.prodOrdId}">
						<input type="hidden" name="prodOrdId" value="1">
						<input type="hidden" name="action" value="delete">
					</form>
				</td>
			</tr>
		</c:forEach> 
	</table>

	<%@ include file="page2.jp" %> 
	</body>
</html>