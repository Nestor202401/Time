<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List Product</title>
</head>
<body>
	<h1>產品列表 - listProd.jsp</h1>
	<%-- 分頁設定  
	<c:if test="${prodPageQty > 0}">
		<b><font color = red>第${currentPage}/${prodPageQty}頁</font></b>
	</c:if>
	--%>
	<br>
	<%-- 圖片顯示
	<img width="140px" height="100px" alt="要飛囉貓貓" src="${pageContext.request.contextPath}/img/cat.png">
	--%>
	<table style="text-align:center;">
		<c:choose>
			<%-- prodList 為空的情況 --%>
			<c:when test="${empty prodList}"><h3><font color=red>查無資料</font><h3></c:when>
			<c:otherwise>
			<%-- 欄位標題 --%>
				<tr><th>商品 ID</th><th>品項</th><th>單價</th><th>上架日期</th><th>下架日期</th><th>銷售狀態</th><th>限時商品</th><th>縮圖</th></tr>	
		<%-- 用 let 報錯 
		<c:forEach let="prod" items="${prodList}">
		/back-end/product/listAllProd.jsp (line: [26], column: [2]) Attribute [let] invalid for tag [forEach] according to TLD
		--%>
			<%-- 列舉欄位內容 --%>
				<c:forEach var="prod" items="${prodList}">
					<tr>
						<td>${prod.prodId}</td>
						<td>${prod.prodName}</td>
						<td>${prod.prodPrice}</td>
						<td>${prod.releaseDate}</td>
						<td>${prod.removeDate}</td>
						<td>
							<c:choose>
								<c:when test="${prod.salesStatus == 0}">銷售中</c:when>
								<c:when test="${prod.salesStatus == 1}">停售</c:when>
							</c:choose>
						</td>
						<td>${prod.timeLimitProd}</td>
						<td>縮圖</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>	

	<%-- 分頁設定 
	<c:if test="${currentPage > 1}">
	<a href="${pageContext.request.contextPath}/emp/emp.do?action=getAll&page=1">至第一頁</a>&nbsp;
	</c:if>
	<c:if test="${currentPage - 1 != 0}">
		<a href="${pageContext.request.contextPath}/emp/emp.do?action=getAll&page=${currentPage - 1}">上一頁</a>&nbsp;
	</c:if>
	<c:if test="${currentPage + 1 <= empPageQty}">
		<a href="${pageContext.request.contextPath}/emp/emp.do?action=getAll&page=${currentPage + 1}">下一頁</a>&nbsp;
	</c:if>
	<c:if test="${currentPage != empPageQty}">
		<a href="${pageContext.request.contextPath}/emp/emp.do?action=getAll&page=${empPageQty}">至最後一頁</a>&nbsp;
	</c:if>
	--%>	
	<br>
	<%-- 圖片顯示 
	<img width="140px" height="100px" alt="要飛囉貓貓" src="${pageContext.request.contextPath}/img/inversecat.png">
	--%>
	<br><br>
	
	<!-- http://localhost:8081/Time/back-end/product/productManage.jsp -->
	<a href="${pageContext.request.contextPath}/back-end/product/productManage.jsp">回首頁</a>
	<!-- /index.jsp  |  /product/productManage.jsp -->	
	
</body>
</html>