<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product_order.model.*"%>

<% 	// 見 controller.OrderServlet.java, *() 存入 req 的 prodOrder obj
	// (此為輸入格式有誤的 obj) ?
	ProductOrder prodOrder = (ProductOrder) request.getAttribute("prodOrder");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>商品訂單資料新增 - addOrder.jsp</title>
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
				<h3>商品訂單資料新增 - addOrder.jsp</h3>
				<h4><a href='selectPage.jsp'>
				<%-- 
				http://localhost:8081/TIA10110-Webapp/coi618Imgs/back1.gif
				Where to learn?
				 --%>
				<img src="${pageContext.request.contextPath}/coi618Imgs/back1.gif" alt="backImg">
				回首頁</a></h4>
			</td>
		</tr>
	</table>
	
	<h3>資料新增: 需實作新增</h3>
	<%-- ErrorMsgs list --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<form method="post" action="order.do" name="form1">
	<%-- 
	Why with name? is it necessary?
	<form method="post" action="order.do" name="form1>
	--%>
		<table>
			<tr>
				<td>商品訂單ID:<font color=red><b>*</b></font></td>
				<td>PK 自增</td>
			</tr>
			
			<tr>
				<td>會員ID:</td>
				<td><input type="number" name="memberId" value="<%= (prodOrder == null) ? "" : prodOrder.getMemberId()%>"></td>
			</tr>
			
			<!-- Q: -->
			<tr>
				<td>訂單成立時間:<font color=red><b>*</b></font></td>
				<td>取當下時間</td>
				<%-- 
				<td>取當下時間 ${(prodOrder != null) ? prodOrder.estTime : ""}</td>
				 For check, remember to remove --%>
			</tr>
			
			<!-- Q: 怎麼設定初值? selectPage.jsp (link to) addOrder.jsp, 中間沒透過 controller, 無法直接給 obj 測試。update 時看看-->
			<tr>
			<%-- For check, remember to remove 
				<td>訂單狀態: ${(prodOrder != null) ? prodOrder.ordStatus : ""}</td> 
			--%>
				<td>訂單狀態:
				<td><input type="radio" id="yet"    name="ordStatus" value="0" <%= (prodOrder != null && prodOrder.getOrdStatus() == 0) ? "checked" : ""%>>
				<label for="yet"   >未完成</label></td>
				
				<td><input type="radio" id="finish" name="ordStatus" value="1" <%= (prodOrder != null && prodOrder.getOrdStatus() == 1) ? "checked" : ""%>>
				<label for="finish">已完成</label>
				</td>
			</tr>
			
			<tr>
				<td>總額:</td>
				<td><input type="number" name="total" value="<%= (prodOrder == null) ? "" : prodOrder.getTotal()%>"></td>
			</tr>
			
			<tr>
				<td>收件人姓名:</td>
				<td><input type="text" name="recipient" value="<%= (prodOrder == null) ? "" : prodOrder.getRecipient()%>"></td>
			</tr>
			
			<tr>
				<td>收件人地址:</td>
				<td><input type="text" name="recAddr" value="<%= (prodOrder == null) ? "" : prodOrder.getRecAddr()%>"></td>
			</tr>
		</table>
		<br>
		<input type="hidden" name="action" value="insert">
		<input type="submit" value="送出新增">
	</form>
	
</body>
</html>