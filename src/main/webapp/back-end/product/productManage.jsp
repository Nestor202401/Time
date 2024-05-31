<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!-- 沒加 taglib 的話: Multiple annotations found at this line:
	- Unknown tag (c:forEach). > useBean 抓的資料無法顯示 -->
<%-- 只是為了方便導向
<%@ page import="com.product.controller.ProdServlet" %>
--%>
<%@ page import="com.product.model.ProductService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.product.model.ProductVO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product Manage</title>
</head>
<body>
	<h1>產品管理頁面 (抄 HibernateEx-Web/index.jsp)</h1>
	<%--  
	<a href="#">查詢所有產品</a> 
	--%>
	<h3><b>所有商品:</b></h3>
	<ul>
		<!--  
		<li>
			<a href="${pageContext.request.contextPath}/product/product.do?action=getAll">查詢所有商品</a>
			<br>
		</li>
		-->
		<li>
			<!-- 查詢所有產品按鈕 -->
			<label>查詢所有商品</label>
		    <button onclick="window.location.href='${pageContext.request.contextPath}/product/product.do?action=getAll'">
		        列出
		    </button>
		</li>
	</ul>
	<!-- 上方錯誤表列 有需要參考 0201 -->
	
	<h3><b>單一查詢:</b></h3>
	<jsp:useBean id="prodSvc" scope="page" class="com.product.model.ProductService"></jsp:useBean>
	<ul>
		<li>
			<form action="${pageContext.request.contextPath}/product/product.do" method="post">
				<label>選擇商品 ID: </label>
				<select size="1" name="prodId">
					<c:forEach var="prodVO" items="${prodSvc.all}"> 
						<option value="${prodVO.prodId}">${prodVO.prodId}-${prodVO.prodName}
					</c:forEach>
				</select>
				<input type="hidden" name="action" value="findByPK"> <!-- getOne_For_Display -->
				<input type="submit" value="送出">
			</form> <!-- 選擇商品 ID 結束 -->
		</li>
		<li>
			<form action="${pageContext.request.contextPath}/product/product.do" method="post">
				<label>輸入商品 ID:</label>
		   		<input type="text" name="prodId" required value="${param.prodId}"> <font color=red>${errorMsgs.prodId}</font>
			<%--<input type="text" name="prodId" value="${param.prodId}"> <font color=red>${errorMsgs.prodId}</font> --%>
				<input type="hidden" name="action" value="findByPK"> <!-- getOne_For_Display -->
				<input type="submit" value="送出">
			</form> <!-- 輸入商品 ID 結束 -->
		</li>
	</ul>
	
	<h3><b>複合查詢: </b></h3>
	<form action="${pageContext.request.contextPath}/product/product.do" method="post">
		<ul>
			<li>
				<label>最早上架日期 - 最晚上架日期</label>
				<input type="date" name="startReleaseDate"/> - 
				<input type="date" name="endReleaseDate"/>
			</li>
			<li>
				<label>最低價 - 最高價</label>
				<input type="number" name="prodPriceLow"/> - 
				<input type="number" name="prodPriceHigh"/>
			</li>
				<%-- 其它複合查詢條件 --%>
			<li>	
				<label>搜尋商品名稱: </label>
				<input type="text" name="prodName"><br>
			</li>
			<li>	
				<!-- 寫死 > 如何動態? > 以 -1 表示沒選 -->
				<label>搜尋商品狀態: </label>
				<select name="salesStatus">
					<option value="-1">請選擇</option>
					<option value="0">銷售中</option>
					<option value="1">停售</option>
				<!-- 			
		 		-->		
		 		</select>
			</li>
			<li>
				<label>搜尋限時商品: </label>
				<select name="timeLimitProd">
					<option value="-1">請選擇</option> 		<%-- {} --%>
					<option value=true>限時</option> 	<%-- {timeLimitProd=true} true --%>
					<option value=false>非限時</option>	 <%-- {timeLimitProd=1} false --%>
					<%--
					<option value="0">限時-"0"</option> 		<%-- {timeLimitProd=0} false
					<option value=0>限時-0</option> 			<%-- {timeLimitProd=0} false
					<option value="true">限時-"true"</option> <%-- {timeLimitProd=true} true
					<option value="1">非限時-"1"</option> 	<%-- {timeLimitProd=1} false
					<option value=1>非限時-1</option>	 		<%-- {timeLimitProd=1} false
					<option value="false">非限時-"false"</option> <%-- {timeLimitProd=false} false 
					--%>
				</select>
				<%-- 其它複合查詢條件 --%>
			</li>
			
		</ul>
		<p><input type="submit" value="複合查詢"></p>
		<input type="hidden" name="action" value="CompositeQuery">	
	</form> <!-- 複合查詢 結束 -->
	
	<h3><b>新增商品: </b></h3>
	<ul>
		<li>
			<a href="${pageContext.request.contextPath}/back-end/product/addProd.jsp">新增</a>
			<!--  
			<a href="#">新增</a>
			-->
		</li>
	</ul>
	
</body>
</html>