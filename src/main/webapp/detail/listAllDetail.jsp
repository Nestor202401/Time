<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product_detail.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>
<%
	ProductDetailService detailSvc = new ProductDetailService(); 
	/* 此行的 list 物件將提供 page1.file 的 (rowNum = list.size();) 取得查詢到的總筆數，再由 page1.file 進行分頁 */
	List<ProductDetail> list = detailSvc.getAll();
	/* 將上一行的 list 變數(物件)存入當前頁面 pageContext，再由底下的 forEach 印出結果 */
	pageContext.setAttribute("list", list); 
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有明細資料 - listAllDetail.jsp</title>
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
				<h3>所有明細資料 - listAllDetail.jsp</h3>
				<h4><a href="selectPage.jsp"><img src="images/back1.gif" id="back">回首頁</a></h4>
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

			<th>修改</th>
			<th>刪除</th>
		</tr>
		
		<%@ include file="page1.jp" %> 

		<c:forEach var="prodDetail" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
			<tr>
				<td>${prodDetail.prodDetailId}</td>
				<td>${prodDetail.prodOrdId}</td>
				<td>${prodDetail.prodId}</td>
				<td>${prodDetail.unitPrice}</td>
				<td>${prodDetail.prodCount}</td>
				<td>${prodDetail.prodSum}</td> 	
				
				<td>
																	<%-- ^C from /url/pattern --%>
					<form method="post" action="<%=request.getContextPath()%>/detail/detail.do" style="margin-bottom:0px;">
						<input type="submit" value="修改">
						<input type="hidden" name="prodDetailId" value="${prodDetail.prodDetailId}"> 
						<input type="hidden" name="action" value="getOneForUpdate">
					</form>
				</td>
				<td>
					<form method="post" action="<%=request.getContextPath()%>/detail/detail.do" style="margin-bottom:0px;">
						<input type="submit" value="刪除">
						<input type="hidden" name="prodDetailId" value="${prodDetail.prodDetailId}">
						<input type="hidden" name="action" value="delete">
					</form>
				</td>
			</tr>
		</c:forEach> 
	</table>

	<%@ include file="page2.jp" %> 
	</body>
</html>