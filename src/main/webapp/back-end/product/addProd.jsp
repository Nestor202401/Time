<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.product.model.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增商品 - addProd.jsp</title>
</head>
<body>
	<h3><b>新增商品: </b></h3>
	<form action="${pageContext.request.contextPath}/product/product.do" method="post"> <!-- name="form1" -->
		<table>
			<tr>
				<td>商品名稱:</td>
				<td><input type="text" name="prodName" value="${param.prodName}"></td>
				<td>${errorMsgs.prodName}<td> 
			</tr>
			
			<tr>
				<td>商品介紹:</td>
				<td><input type="text" name="prodIntro" value="${param.prodIntro}"></td>
				<td>${errorMsgs.prodIntro}<td> 
			</tr>
			
			<tr>
				<td>商品價格:</td>
				<td><input type="number" name="prodPrice" value="${param.prodPrice}"></td>
				<td>${errorMsgs.prodPrice}<td> 
			</tr>
			
			<tr>
				<td>上架日期:</td>
				<td><input type="date" name="releaseDate" value="${param.releaseDate}"></td>
				<td>${errorMsgs.releaseDate}<td> 
			</tr>
			
			<tr>
				<td>下架日期:</td>
				<td><input type="date" name="removeDate" value="${param.removeDate}"></td>
				<td>${errorMsgs.removeDate}<td> 
			</tr>
			
			<tr>
				<td>銷售狀態:</td>
				<td>
					<select name="salesStatus">
						<option value="1" ${param.salesStatus == 1 ? 'selected' : ''}>停售</option>
						<option value="0" ${param.salesStatus == 0 ? 'selected' : ''}>銷售中</option>
					</select>
				<td>${errorMsgs.salesStatus}<td> 
			</tr>
			<tr>
				<td>限時商品:</td>
					<td>
						<select name="timeLimitProd">
							<option value="false" ${param.timeLimitProd == 'false' ? 'selected' : ''}>非限時</option>
							<option value="true" ${param.timeLimitProd == 'true' ? 'selected' : ''}>限時</option>
						</select> 
					</td>
				<td>${errorMsgs.timeLimitProd}<td> 
			</tr>
			
		</table>
		<input type="hidden" name="action" value="insert"> 
		<input type="submit" value="送出">
	</form> <!-- 新增商品 結束 -->
</body>
</html>