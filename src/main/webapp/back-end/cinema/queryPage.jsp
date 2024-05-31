<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查詢場次座位</title>
</head>
<body>
<h2>查詢場次座位</h2>
<form action="cinema.do" method="post">
    <input type="hidden" name="action" value="query_session">
    場次ID: <input type="text" name="sessionId" required>
    <button type="submit">查詢座位</button>
</form>
</body>
</html>
