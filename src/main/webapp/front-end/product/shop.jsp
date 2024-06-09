<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.product.model.*"%>

<%
	ProductService prodSvc = new ProductService();
	List<ProductVO> prodList = prodSvc.listOnSale();
	request.setAttribute("prodList", prodList);
	Integer memberId = 4; // 先固定 > 怎麼抓會員資料?
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop.jsp</title>
</head>
<body>
	<h1>商城 - shop.jsp</h1>
	<%--  
		1. 列出所有商品
		2. 導向單一商品
		3. 顯示購物車
		4. 結帳 (怎麼顯示信用卡?)
	--%>
	<label>${msg}</label>
	<table>
		<thead>
			<tr>
				<th></th>
				<th>品項</th>
				<th>單價</th>
				<th>限時商品</th>
				<th>縮圖</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="prodVO" items="${prodList}">
				<tr>
					<td>
						<%-- 跳到 front productDetail.jsp, 由 productServlet 轉交 --%>
						<form action="${pageContext.request.contextPath}/product/product.do" method="post">
							<input type="hidden" name="action" value="frontFindByPK"> 
							<input type="hidden" name="prodId" value="${prodVO.prodId}">
							<input type="submit" value="詳細資料">
						</form>
					</td>
					<td>${prodVO.prodName}</td>
					<td>${prodVO.prodPrice}</td>
					<td>
						<c:choose>
							<c:when test="${prodVO.timeLimitProd == false}"></c:when>
							<c:when test="${prodVO.timeLimitProd == true}">限時商品</c:when>
						</c:choose>
					</td>
					<%-- 
					<td><img alt="商品圖片" src="${pageContext.request.contextPath}/resources/images/product/${prodVO.prodImgs[0].imgName}" width="50px"></td>
					--%>
					<td>
						<img alt="商品圖片" src="${pageContext.request.contextPath}/${prodVO.prodImgs[0].imgSrc}" width="50px"
						onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/resources/images/product/noImg.jpg';">
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br><br>
	<button onclick="window.location.href='${pageContext.request.contextPath}/front-end/product/cart.jsp'">																
        顯示購物車
    </button>
    
</body>
</html>