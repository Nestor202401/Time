<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>查询场次</title>
</head>
<body>
    <form action="cinema.do" method="post">
        <input type="hidden" name="action" value="query_session">
        场次ID: <input type="text" name="sessionId">
        <input type="submit" value="查询">
    </form>
</body>
</html>
