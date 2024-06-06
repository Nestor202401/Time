<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>creditCardPay</title>
</head>
<body>
	<h1>信用卡結帳 - creditCardPay.jsp</h1>
	<%-- 
	${prodOrdVO} 
	--%>
	<form method="post" action="${pageContext.request.contextPath}/product_cart/cart.do">
		<label for="cardId">信用卡號碼</label>
		<input type="text" id="cardId" name="cardId" value="${cardId}" required>
		${errorMsgs.cardId}
		<br>
		<label for="cardName">持卡人姓名</label>
		<input type="text" id="cardName" name="cardName" value="${cardName}" required>
		${errorMsgs.cardName}
		<br>
		<%-- 
		<option value="1" ${param.salesStatus == 1 ? 'selected' : ''}>停售</option>${ } 
		--%>
		<label for="expiryYear">到期年: 20(YY)</label>
		<select id="expiryYear" name="expiryYear" required>
		    <option value="">年</option>
		    <c:forEach var="i" begin="24" end="33">
		        <option value="${i}" ${expiryYear == i ? 'selected' : ''}>${i}</option>
		    </c:forEach>
		</select>
		<label for="expiryMonth">到期月份</label>
		<select id="expiryMonth" name="expiryMonth" required>
			<option value="">月</option>
			<c:forEach var="m" begin="1" end="12">
		        <option value="${m}" ${expiryMonth == m ? 'selected' : ''}>${m}</option>
		    </c:forEach>
		    <%-- 
		    <option value="">月份</option>${ }
		    <option value="01">01</option>
		    <option value="02">02</option>
		    <option value="03">03</option>
		    <option value="04">04</option>
		    <option value="05">05</option>
		    <option value="06">06</option>
		    <option value="07">07</option>
		    <option value="08">08</option>
		    <option value="09">09</option>
		    <option value="10">10</option>
		    <option value="11">11</option>
		    <option value="12">12</option>
		     --%>
		</select>
		<br>
		
		<label for="cvv">CVV</label>
		<input type="text" id="cvv" name="cvv" value="${cvv}" required>
		${errorMsgs.cvv}
		<br>
		<label for="amount">金額: ${prodOrdVO.total}</label>
		<br>	
		<input type="submit" value="支付">
		<input type="hidden" name="action" value="payByCard">
		<input type="hidden" name="prodOrdId" value="${prodOrdVO.prodOrdId}">
		<%-- 
		--prodOrdVO.prodOrdId: ${prodOrdVO.prodOrdId}--
		 --%>
	</form>
	<br><br>
	<button onclick="window.location.href='${pageContext.request.contextPath}/front-end/product/shop.jsp'">																	
        回商城首頁
    </button>
</body>
</html>