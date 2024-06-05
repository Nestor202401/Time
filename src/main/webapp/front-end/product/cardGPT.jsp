<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>信用卡结账</title>
<style>
    body {
        font-family: Arial, sans-serif;
    }
    .container {
        max-width: 400px;
        margin: 0 auto;
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    h2 {
        text-align: center;
    }
    label {
        display: block;
        margin-bottom: 8px;
    }
    input[type="text"], input[type="number"], input[type="submit"] {
        width: 100%;
        padding: 10px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }
    input[type="submit"] {
        background-color: #4CAF50;
        color: white;
        border: none;
    }
    input[type="submit"]:hover {
        background-color: #45a049;
    }
</style>
</head>
<body>
    <div class="container">
        <h2>信用卡结账</h2>
        <form action="${pageContext.request.contextPath}/checkout" method="post">
            <label for="cardNumber">信用卡号码</label>
            <input type="text" id="cardNumber" name="cardNumber" required>

            <label for="cardName">持卡人姓名</label>
            <input type="text" id="cardName" name="cardName" required>

            <label for="expiryDate">到期日期 (MM/YY)</label>
            <input type="text" id="expiryDate" name="expiryDate" required>

            <label for="cvv">CVV</label>
            <input type="number" id="cvv" name="cvv" required>

            <label for="amount">金额</label>
            <input type="text" id="amount" name="amount" required>

            <input type="submit" value="支付">
        </form>
    </div>
</body>
</html>
