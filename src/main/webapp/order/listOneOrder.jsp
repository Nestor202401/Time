<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.product_order.model.*" %>
<%-- 此頁練習採用 Script 的寫法取值 --%>

<%
	ProductOrder prodOrd = (ProductOrder) request.getAttribute("prodOrd"); 
	// OrderServlet.java: if("getOneForDisplay".equals(action)), 存入 req 的 prodOrd 物件
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>明細資料 - listOneOrder.jsp</title>
	<style>
		table#table-1 {
			background-color:#CCCCFF;
			border: 2px solid black;
			text-align: center;
		}
		table#table-1 h4 {
			color: red;
			display: black;
			margin-bottom: 1px;
		}
		h4 {
			color: blue;
			display: inline;
		}
		<%-- merge 2 css --%>
		table {
			width: 600px;
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
	</style>
</head>
<body bgcolor: 'white'>
	<%-- 
	<p>Zoaholic: 2</p>
	 --%>
	<h4>此頁練習暫採用 Script 的寫法取值:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>商品訂單資料 - listOneOrder.jsp</h3>
				<h4><a href="selectPage.jsp">
					<img src="${pageContext.request.contextPath}/coi618Imgs/back1.gif" width="100" height="32" border="0">
					回首頁</a></h4>
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
		</tr>
		<tr>
		<!-- p.198 JSP / p.241 JSTL -->
			<td><%=prodOrd.getProdOrdId()%></td>
			<td><%=prodOrd.getMemberId()%></td>
			<td><%=prodOrd.getEstTime()%></td>
			<td><%=prodOrd.getOrdStatus()%></td>
			<td><%=prodOrd.getTotal()%></td>
			<td><%=prodOrd.getRecipient()%></td>
			<td><%=prodOrd.getRecAddr()%></td>
		</tr>
	</table>

</body>
</html>