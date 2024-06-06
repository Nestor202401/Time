<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>payCompleted.jsp</title>
</head>
<body>
	<h1>完成付款 - payCompleted.jsp</h1>
<%-- 	
	${prodOrdVO}
org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.product_order.model.ProductOrderVO.prodDetails, could not initialize proxy - no Session
	<c:forEach var="detail" items="${prodOrdVO.getProdDetails()}">
		${detail}
	</c:forEach>
 --%>	
	<button onclick="window.location.href='${pageContext.request.contextPath}/front-end/product/shop.jsp'">																	
        回商城首頁
    </button>
</body>
</html>