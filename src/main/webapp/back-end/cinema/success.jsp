<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>購買成功</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            padding: 20px;
            text-align: center;
        }
        .container {
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            display: inline-block;
            padding: 20px;
            margin-top: 50px;
        }
        h2 {
            color: #333;
        }
        p {
            color: #666;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>購買成功</h2>
        <p>您已成功購買以下座位：</p>
        <p><strong>${seatNumbers}</strong></p>
        <a href="cinema.do?action=query_session&sessionId=${sessionId}">返回场次查询</a>
    </div>
</body>
</html>
