<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.product_detail.model.*" %>
<%-- 此頁練習採用 Script 的寫法取值 --%>

<%
	ProductDetail prodDetail = (ProductDetail) request.getAttribute("prodDetail"); 
	// DetailServlet.java: 93, 存入 req 的 prodDetail 物件
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>明細資料 - listOneDetail.jsp</title>
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
				<h3>明細資料 - listOneDetail.jsp</h3>
				<h4><a href="selectPage.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
			</td>
		</tr>
	</table>
	
	<table>
		<tr>
			<th>明細ID</th>
			<th>訂單ID</th>
			<th>產品ID</th>
			<th>單價</th>
			<th>數量</th>
			<th>小計</th>
		</tr>
		<tr>
		<!-- p.198 JSP / p.241 JSTL -->
			<td><%=prodDetail.getProdDetailId()%></td>
			<td><%=prodDetail.getProdOrdId()%></td>
			<td><%=prodDetail.getProdId()%></td>
			<td><%=prodDetail.getUnitPrice()%></td>
			<td><%=prodDetail.getProdCount()%></td>
			<td><%=prodDetail.getProdSum()%></td>
		</tr>
	</table>

</body>
</html>