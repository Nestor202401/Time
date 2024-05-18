<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product_order.model.*"%>

<% // 見 OrderServlet.java: action is "update" 存入req的empVO物件 
   // (此為從資料庫取出的 ProductOrder obj, 也可以是輸入格式有錯誤時的物件) ?
	ProductOrder prodOrd = (ProductOrder) request.getAttribute("prodOrd"); // name check ?
	//request.setAttribute("prodOrd", prodOrd); // 同時再存入，為 update 時使用 Loop | try session? 
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%-- // 有需要嗎?
 --%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>商品訂單資料修改 - updateOrderInput.jsp</title>
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
</style>
</head>
<body bgcolor='white'>
	<%-- 
	<p>Zoaholic: 1</p>
	 --%>
	<table id="table-1">
		<tr>
			<td>
				<h3>商品訂單資料修改 - updateOrderInput.jsp</h3>
				<%-- 
				<p>單純功能展示，商品訂單在正式專案實際不修改</p>
				 --%>
				<h4><a href="selectPage.jsp">
				<img src="${pageContext.request.contextPath}/coi618Imgs/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
			</td>
		</tr>
	</table>
	<h3>資料修改:</h3>
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
		<table>
			<tr>
				<td>商品訂單ID:<font color=red><b>*</b></font></td>
				<td><%=prodOrd.getProdOrdId()%></td>
			</tr>
			<tr>
				<td>會員ID:<font color=red><b>*</b></font></td>
				<td><%=prodOrd.getMemberId()%></td> 
			</tr>
			<tr>
				<td>訂單成立時間:<font color=red><b>*</b></font></td>
				<td><%=prodOrd.getEstTime()%></td>
			</tr>
			<tr>
				<td>訂單狀態:<font color=red><b>*</b></font></td>
				<td><%=prodOrd.getOrdStatus()%></td>
			</tr>
			<tr>
				<td>總額:<font color=red><b>*</b></font></td>
				<td><%=prodOrd.getTotal()%></td>
			</tr>
			<tr>
				<td>收件人姓名:</td>
				<%-- 
				<td><span><%=prodOrd.getRecipient()%></span></td>
				 --%>
				<td><input type="text" name="recipient" value="<%=prodOrd.getRecipient()%>"></td>
			</tr>
			<tr>
				<td>收件人地址:</td>
				<%-- 
				<td><span><%=prodOrd.getRecAddr()%></span></td>
				 --%>
				<td><input type="text" name="recAddr" value="${prodOrd.recAddr}"></td>
			</tr>
		</table>
		<br>
									   <%-- value update, how to configure? --%>
		<input type="hidden" name="action" value="update" > 
		<input type="hidden" name="prodOrdId" value="<%=prodOrd.getProdOrdId()%>" >
		<input type="hidden" name="memberId" value="${prodOrd.memberId}" >
		<input type="hidden" name="estTime" value="${prodOrd.estTime}" >
		<input type="hidden" name="ordStatus" value="${prodOrd.ordStatus}" >
		<input type="hidden" name="total" value="${prodOrd.total}" >
		<input type="hidden" name="recipient" value="${prodOrd.recipient}" >
		<input type="hidden" name="recAddr" value="${prodOrd.recAddr}" >
		
		<%-- 
		<input type="hidden" name="prodOrdId" value="${prodOrd.prodOrdId}"/> 
		Check EL work or not? Worked --%>
		<input type="submit" value="送出修改" > 
	</form>
</body>
</html>