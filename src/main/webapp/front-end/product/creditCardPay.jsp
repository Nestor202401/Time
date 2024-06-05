<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
	<form method="post" action="#">
		<label for="cardId">信用卡號碼</label>
		<input type="text" id="cardId" name="cardId" value="${cardId}" required>
		<br>
		<label for="cardName">持卡人姓名</label>
		<input type="text" id="cardName" name="cardName" required>
		<br>
		<label for="expiryDate">到期日期 (MM/YY)</label>
		<input type="text" id="expiryDate" name="expiryDate" required>
		<br>
		<label for="cvv">CVV</label>
		<input type="number" id="cvv" name="cvv" required>
		<br>
		<label for="amount">金額: ${prodOrdVO.total}</label>
		<br>	
		<input type="submit" value="支付">
		<input type="hidden" name="action" value="payByCard">
		<input type="hidden" name="prodOrdId" value="${prodOrdVO.prodOrdId}">
	</form>
	
</body>
</html>