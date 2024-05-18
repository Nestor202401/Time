<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 41-1: 核心功能標籤庫(Core tag library) p.248 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Select Page 0201</title>
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
		<tr><td><h3>TIA10110-Webapp Product detail: Home</h3><h4>( MVC )</h4></td></tr>
	</table>
	<p>This is the Home page for TIA10110-Webapp Product detail</p>

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
		<li><a href='listAllDetail.jsp'>List</a> all Detail.<br><br></li>
		
		<li>
			<FORM METHOD="post" ACTION="detail.do" >
			<span>輸入明細ID (Ex: 12000001):</span>
			<input type="text" name="prodDetailId">
			<input type="hidden" name="action" value="getOneForDisplay">
			<input type="submit" value="送出">
			</FORM>
		</li>
		
		<li>
											  <%-- name="form1" for JS --%>
			<FORM METHOD="post" ACTION="detail.do" name="form1">
				<span>輸入明細ID (Ex: 12000001):</span>
				<input type="text" name="prodDetailId">
				<input type="hidden" name="action" value="getOneForDisplay">
				<input type="button" value="送出" onclick="fun1()">
				<h4>(資料格式驗証 by JavaScript).</h4>
			</FORM>
		</li>
		
		<jsp:useBean id="detailDao" scope="page" class="com.product_detail.model.ProductDetailDAOImpl" />
		 
		<li> 
			<FORM METHOD="post" ACTION="detail.do">
				<span>選擇明細ID:</span>
				<select size="1" name="prodDetailId">
				
					<c:forEach var="productDetail" items="${detailDao.all}"> 
						<option value="${productDetail.prodDetailId}">${productDetail.prodDetailId}					
					</c:forEach>
				</select>
				<input type="hidden" name="action" value="getOneForDisplay">
				<input type="submit" value="送出">
			</FORM>
		</li>
		 	
	</ul>
	
	<h3>明細管理</h3>
	<ul><li><a href='addDetail.jsp'>Add</a> a new Detail. (Unimplemented)</li></ul>
	
	<script>
		function fun1() {
			with(document.form1){
				if (prodDetailId.value=="") {
					alert("請輸入明細ID!");
				} else if (isNaN(prodDetailId.value)){ // isNaN(): Not a Number
					alert("明細ID格式不正確"); 
				} else if ((prodDetailId.value > 12999999) || (prodDetailId.value < 12000000))  {
					alert("請輸入介於12000000~12999999之間的數字!");
				} else {
					submit();
				}
			}
		}
	</script>
</body>
</html>