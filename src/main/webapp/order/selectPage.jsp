<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 41-1: 核心功能標籤庫(Core tag library) p.248 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ProductOrder SelectPage</title>
	<style>
		table#table-1 {
			width: 450px;
			background-color: #CCCCFF;
			margin-top: 5px;
			margin-bottom: 10px;
			border: 3px ridge Gray;
			height: 80px;
			text-align: center;
		}
		table#table-1 h4 {
			color: red;
			display: block;
			margin-bottom: 1px;
		}
		h4{
			color: blue;
			display:inline;
		}
	</style>
</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr><td><h3>TIA10110-Webapp Product order: Home</h3><h4>( MVC )</h4></td></tr>
	</table>
	<p>This is the Home page for TIA10110-Webapp Product order</p>

	<h3>資料查詢:</h3>

	<%-- ErrorMsgs list --%>
	<c:if test="${not empty errorMsgs}">
		<p style="color:red">請修正以下錯誤:</p>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<ul>
		<li><a href='listAllOrder.jsp'>List</a> all Order.<br><br></li>
		
		<li>
			<FORM METHOD="post" ACTION="order.do" >
			<span>輸入商品訂單ID (Ex: 11000001):</span>
			<input type="text" name="prodOrdId">
			<input type="hidden" name="action" value="getOneForDisplay">
			<input type="submit" value="送出">
			</FORM>
		</li>
		
		<li>
											  <%-- name="form1" for JS --%>
			<FORM METHOD="post" ACTION="order.do" name="form1">
				<span>輸入商品訂單ID (Ex: 11000001):</span>
				<input type="text" name="prodOrdId">
				<input type="hidden" name="action" value="getOneForDisplay">
				<input type="button" value="送出" onclick="fun1()">
				<h4>(資料格式驗証 by JavaScript).</h4>
			</FORM>
		</li>
		
		<jsp:useBean id="orderDao" scope="page" class="com.product_order.model.ProductOrderDAOImpl" />
		 
		<li> 
			<FORM METHOD="post" ACTION="order.do">
				<span>選擇商品訂單ID:</span>
				<select size="1" name="prodOrdId">
				
					<c:forEach var="productOrder" items="${orderDao.all}"> 
						<option value="${productOrder.prodOrdId}">${productOrder.prodOrdId}					
					</c:forEach>
				</select>
				<input type="hidden" name="action" value="getOneForDisplay">
				<input type="submit" value="送出">
			</FORM>
		</li>
		 	
	</ul>
	
	<h3>商品訂單管理</h3>
	<ul><li><a href='addOrder.jsp'>Add</a> a new Order.</li></ul>
	
	<script>
		function fun1() {
			with(document.form1){
				if (prodOrdId.value=="") {
					alert("請輸入商品訂單ID!");
				} else if (isNaN(prodOrdId.value)){ // isNaN(): Not a Number
					alert("商品訂單ID格式不正確"); 
				} else if ((prodOrdId.value > 11999999) || (prodOrdId.value < 11000000))  {
					alert("請輸入介於11000000~11999999之間的數字!");
				} else {
					submit();
				}
			}
		}
	</script>
</body>
</html>