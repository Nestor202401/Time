<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.product_cart.model.*"%>
<%

%>
<%-- 
	CartService cartSvc = new CartService();
	List<CartVO> list = cartSvc.getCartItem();
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>productDetail.jsp</title>
</head>
<body>
	<h1>商品詳細資料 - productDetail.jsp</h1>
	<%-- ${prodVO.prodId} - ${prodVO.prodName} --%>
							 <%-- <a href="${pageContext.request.contextPath}/front-end/product/shop.jsp">回商城首頁 - a</a> --%>
	<button onclick="window.location.href='${pageContext.request.contextPath}/front-end/product/shop.jsp'">																	
        回商城首頁
    </button>
    
    <button onclick="window.location.href='${pageContext.request.contextPath}/front-end/product/cart.jsp'">																
        顯示購物車
    </button>
	
	<table>
		<thead>
			<tr>
				<th>數量|加入購物車</th> <%-- 加入購物車/數量 --%>
				<th>品項</th>
				<th>單價</th>
				<th>介紹</th>
				<!-- <th>縮圖</th> -->
			</tr>
		</thead>
		<tbody>
			<tr> <%-- 加入購物車 --%>
				<td> 
					<form action="${pageContext.request.contextPath}/product_cart/cart.do" method="post">
						<input type="hidden" name="action" value="addToCart"> 
						<input type="hidden" name="memberId" value="4" > <%-- 先寫死 --%>
						<input type="hidden" name="prodId" value="${prodVO.prodId}">
						<input type="hidden" name="prodName" value="${prodVO.prodName}">
						<input type="hidden" name="unitPrice" value="${prodVO.prodPrice}">
						<input type="number" name="prodCount" value="1" min="1" max="10">
						<%-- ${小計} 交由後端計算 --%>
						<button type="submit">加入購物車</button>
					</form>
				</td>
				<td>${prodVO.prodName}</td>
				<td>${prodVO.prodPrice}</td>
				<td>${prodVO.prodIntro}</td>				
				<%-- 
				<td><img alt="商品圖片" src="${pageContext.request.contextPath}/resources/images/product/${prodVO.prodImgs[0].imgName}" width="500px"></td>
				--%>
			</tr>
		</tbody>
	</table>
	<%-- 
	<img alt="商品圖片" src="${pageContext.request.contextPath}/resources/images/product/${prodVO.prodImgs[0].imgName}" width="500px">
 	--%>	
	<img alt="商品圖片" src="${pageContext.request.contextPath}/${prodVO.prodImgs[0].imgSrc}" width="500px"
	onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/resources/images/product/noImg.jpg';">
	
</body>
</html>