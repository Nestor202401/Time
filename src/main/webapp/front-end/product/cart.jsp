<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List"%>
<%@ page import="com.product_cart.model.*"%>
<%@ page import="com.product.model.*"%>
<%
 	Integer memberId = 4; // 會員資訊
 	request.setAttribute("memberId", memberId);
 	
 	ProductService prodSvc = new ProductService(); // 用 prodId 提取 prodImg 
	CartService cartSvc = new CartService();
	List<CartVO> cartList = cartSvc.getCart(memberId); 
	request.setAttribute("cartList", cartList); // 常沒有 set
	request.setAttribute("prodSvc", prodSvc); // Service 也要 set ?
	// 總金額計算
	int total = 0;
	for (CartVO c : cartList) {
		total += c.getProdSum();
	}
	request.setAttribute("total", total); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>購物車 - cart.jsp</title>
</head>
<body>
	<h1>購物車 - cart.jsp</h1>
	<button onclick="window.location.href='${pageContext.request.contextPath}/front-end/product/shop.jsp'">																	
        回商城首頁
    </button>
	<table>
		<thead>
			<tr>
				<td></td>
				<td>刪除</td>
				<td>圖片</td>
				<td>商品</td>
				<td>單價</td>
				<td>數量</td>
				<td>小計</td>
				<td>修改數量</td>
			</tr>
		</thead>
		<tbody>
		<%-- ${ cartList } DEBUG 方法 --%>
			<c:forEach var="cartVO" items="${cartList}">
			
			<%-- ${ cartVO } DEBUG 方法 --%>
				<tr>
					<td></td>
					<td>
						<form action="${pageContext.request.contextPath}/product_cart/cart.do" method="post"> <%-- deleteItem() --%>
							<input type="hidden" name="action" value="deleteOneItem">
							<input type="hidden" name="memberId" value="${cartVO.memberId}">
							<input type="hidden" name="prodId" value="${cartVO.prodId}">
							<button type="submit">刪除</button>
						</form>
					</td>
					<%-- 取得圖片 怎麼從 cart 到 product 到 img? --%>
					<%-- 
					<td><img alt="商品圖片" src="${pageContext.request.contextPath}/resources/images/product/${prodVO.prodImgs[0].imgName}" width="50px"></td>
					<td><img alt="商品圖片" src="${pageContext.request.contextPath}/resources/images/product/${prodSvc.getOneProd(cartVO.prodId).prodImgs[0].imgName}" width="50px"></td>
	 				--%>																				<%-- 方法用 完整(getOneProd) --%>
					<td>
						<img alt="商品圖片" src="${pageContext.request.contextPath}/${prodSvc.getOneProd(cartVO.prodId).prodImgs[0].imgSrc}" width="50px"
						onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/resources/images/product/noImg.jpg';">
					</td>	
					<%-- 取得商品名 --%>
					<td>${cartVO.prodName}</td>
					<td>${cartVO.unitPrice}</td>
					<%-- 可動 --%>
					<%-- 
					<td><input type=number" name="prodCount" value="${cartVO.prodCount}" min="1" max="10"></td>
					 --%>
					<td>${cartVO.prodCount}</td>
					<%-- 連動數量 --%>
					<td>${cartVO.prodSum}</td>
					<td>
						<form action="${pageContext.request.contextPath}/product/product.do" method="post">
							<input type="hidden" name="action" value="frontFindByPK"> 
							<input type="hidden" name="prodId" value="${cartVO.prodId}">
							<input type="submit" value="修改數量">
						</form>
					</td>
				</tr>
			</c:forEach>	
		</tbody>
	</table>
	<span>總金額</span>
	<span>${total}</span>
	<form action="${pageContext.request.contextPath}/product_cart/cart.do" method="post"> <%-- clearCart(memberId) --%>
		<input type="hidden" name="action" value="clearCart">
		<input type="hidden" name="memberId" value="${memberId}">
		<button type="submit">刪除所有商品</button>
	</form>
	
	<%-- 
	? checkout(memberId)
	<form action="#" method="post">
	 --%>
	<form action="${pageContext.request.contextPath}/product_cart/cart.do" method="post">
		<input type="hidden" name="action" value="checkOut">
		<input type="hidden" name="memberId" value="${memberId}">
		<input type="hidden" name="total" value="${total}">
		<button type="submit">去結帳</button>
	</form>
	
</body>
</html>