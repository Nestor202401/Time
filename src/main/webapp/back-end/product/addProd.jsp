<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.product.model.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
	#preview {
		max-width:  300px;
		max-height: 200px;
		width:  auto;
		height: auto;
	}
</style>

<title>新增商品 - addProd.jsp</title>
</head>
<body>
	<h3><b>新增商品: </b></h3>
	<%-- 
	無上傳 
	<form action="${pageContext.request.contextPath}/product/product.do" method="post">  
	1. 修改上傳格式
	--%> <!-- name="form1" -->
	<form action="${pageContext.request.contextPath}/product/product.do" method="post" enctype="multipart/form-data">
	
		<table>
			<%-- 如果沒有，就是新增 (其它處理完再說) 
			<tr>
				<td>商品ID:</td>
				<td><input type="text" name="prodId" value="${param.prodId == null ? '新增' : param.prodId}"></td>
				<td>${errorMsgs.prodName}<td> 
			</tr>
			--%>
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
			<%-- 
			HERE 2. addProd 加上圖片欄 > ProdServlet.java > 暫不處理 
			--%>
			<tr>
			    <td>商品圖片:</td>
			    <td><input type="file" id="prodImg" name="prodImg" required accept="image/*"></td>
			    <td>${errorMsgs.prodImg}<td>
			</tr>
			<tr>
				<td></td>
				<td>
					<%-- 
					<img src="https://via.placeholder.com/300x200" id="placeholderImg" hidden="false">
					 --%>
					<img src="https://via.placeholder.com/300x200" id="placeholderImg">
					<img src="" hidden="true" id="preview" >
				</td>
			</tr>
			
		</table>
		<input type="hidden" name="action" value="insert"> 
		
		<input type="submit" value="送出">
	</form> <!-- 新增商品 結束 -->
	
	<script>
		// 當 <input> prodImg 改變時
	    document.getElementById('prodImg').addEventListener('change', function(event) {
	    	// 將檔案指定給 file 
	        const file = event.target.files[0]; // Base64 encode Data URL
	        if (file) {
	            const reader = new FileReader();
	            reader.onload = function(e) {
	            	// 將 preview 顯示，給圖的 src
	                const previewImg = document.getElementById('preview');
	                previewImg.src = e.target.result;
	                previewImg.hidden = false;
					// 將 placeholderImg 隱藏
	                const placeholderImg = document.getElementById('placeholderImg');
	                placeholderImg.hidden = true;
	            };
	            reader.readAsDataURL(file);
	        }
	    });
	</script>
</body>
</html>