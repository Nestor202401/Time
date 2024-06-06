<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product.model.*" %>
<%-- 以下配合 <%= %> 
<% 
	ProductVO prodVO = (ProductVO) request.getAttribute("prodVO");
%>
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改商品 - updateProd.jsp</title>
</head>
<body>
	<h3><b>修改商品: </b></h3>
	<form action="${pageContext.request.contextPath}/product/product.do" method="post"> <!-- name="form1" -->
		<table>
			
			<tr>
				<td>商品 ID:</td>
				<%--
				<td><%=prodVO.getProdId() %></td>
				--%>
				<td>${prodVO.prodId}</td>
				<td><input type="hidden" name="prodId" value="${prodVO.prodId}"></td> 
				<td>${errorMsgs.prodName}<td> 
			</tr>
			
			<tr>
				<td>商品名稱:</td>
				<%-- 
				<td><input type="text" name="prodName" value="<%=prodVO.getProdName() %>"></td>
				--%>
				<td><input type="text" name="prodName" value="${prodVO.prodName}"></td> 
				<td>${errorMsgs.prodName}<td> 
			</tr>
			
			<tr>
				<td>商品介紹:</td>
				<%-- 
				<td><input type="text" name="prodIntro" value="<%=prodVO.getProdIntro() %>"></td>
				--%>
				<td><input type="text" name="prodIntro" value="${prodVO.prodIntro}"></td> 
				<td>${errorMsgs.prodIntro}<td> 
			</tr>
			
			<tr>
				<td>商品價格:</td>
				<%-- 
				<td><input type="number" name="prodPrice" value="<%=prodVO.getProdPrice() %>"></td>
				--%>
				<td><input type="number" name="prodPrice" value="${prodVO.prodPrice}"></td> 
				<td>${errorMsgs.prodPrice}<td> 
			</tr>
			
			<tr>
				<td>上架日期:</td>
				<%-- 
				<td><input type="date" name="releaseDate" value="<%=prodVO.getReleaseDate() %>"></td>
				--%>
				<td><input type="date" name="releaseDate" value="${prodVO.releaseDate}"></td> 
				<td>${errorMsgs.releaseDate}<td> 
			</tr>
			
			<tr>
				<td>下架日期:</td>
				<%-- 
				<td><input type="date" name="removeDate" value="<%=prodVO.getRemoveDate() %>"></td>
				--%>
				<td><input type="date" name="removeDate" value="${prodVO.removeDate}"></td> 
				<td>${errorMsgs.removeDate}<td> 
			</tr>
			
			<tr>
				<td>銷售狀態:</td>
				<td>
					<select name="salesStatus">
					<%-- 
						<option value="1" <%=prodVO.getSalesStatus() == 1 ? "selected" : "" %>>停售</option>
						<option value="0" <%=prodVO.getSalesStatus() == 0 ? "selected" : "" %>>銷售中</option>
					 --%>
						<option value="1" ${prodVO.salesStatus == 1 ? 'selected' : ''}>停售</option>
						<option value="0" ${prodVO.salesStatus == 0 ? 'selected' : ''}>銷售中</option>
					</select>
				<td>${errorMsgs.salesStatus}<td> 
			</tr>
			<tr>
				<td>限時商品:</td>
					<td>
						<select name="timeLimitProd">
						<%-- 
							<option value="false" <%=prodVO.getTimeLimitProd() == false ? "selected" : "" %>>非限時</option>
							<option value="true"  <%=prodVO.getTimeLimitProd() == true  ? "selected" : "" %>>限時</option>
						 --%>
							<option value="false" ${prodVO.timeLimitProd == 'false' ? 'selected' : ''}>非限時</option>
							<option value="true"  ${prodVO.timeLimitProd == 'true'  ? 'selected' : ''}>限時</option>
						</select> 
					</td>
				<td>${errorMsgs.timeLimitProd}<td> 
			</tr>
			
		</table>
		<input type="hidden" name="action" value="update"> 
		<input type="submit" value="送出修改">
	</form> <!-- 新增商品 結束 -->
</body>
</html>