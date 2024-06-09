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
	<br>
	<table style="text-align:center;">
		<c:choose>
			<%-- prodList 為空的情況 --%>
			<c:when test="${empty prodList}"><h3><font color=red>查無資料</font></h3></c:when>
			<c:otherwise>
			<%-- 欄位標題 --%>
				<tr>
					<th>商品 ID</th>
					<th>品項</th>
					<th>單價</th>
					<th>上架日期</th>
					<th>下架日期</th>
					<th>銷售狀態</th>
					<th>限時商品</th>
					<th>縮圖</th>
					<th>修改</th>
				</tr>				
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
						<%-- 縮圖先不放，不該顯示在 list / BUG: Lazy init
						<td>縮圖</td> 
						<td>DEBUG-路徑顯示: ${prod.prodImgs[0].imgSrc}</td>
						<td><img alt="商品圖片" src="#" width="50px"></td>
						<td><img alt="商品圖片" src="${pageContext.request.contextPath}/resources/images/product/${prod.prodImgs[0].imgName}" width="50px"></td>
						<td><img alt="商品圖片" src="${pageContext.request.contextPath}/${prod.prodImgs[0].imgSrc}" width="50px"></td>
						--%>					  
						
						<td>
							<img alt="商品圖片" src="${pageContext.request.contextPath}/${prod.prodImgs[0].imgSrc}" width="50px"
							onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/resources/images/product/noImg.jpg';">
						</td>
						<td> <!-- 修改 -->
							<form action="${pageContext.request.contextPath}/product/product.do" method="post">
								<input type="submit" value="修改">
								<input type="hidden" name=prodId value="${prod.prodId}">
								<input type="hidden" name="action" value="getOneForUpdate">
							</form>
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>	
	<br>
	
	<!-- http://localhost:8081/Time/back-end/product/productManage.jsp -->
	<a href="${pageContext.request.contextPath}/back-end/product/productManage.jsp">回首頁</a>	
	
</body>
</html>