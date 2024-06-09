<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tictypes.model.*"%>

<% 
	TicketTypesVO ticTypesVO = (TicketTypesVO) request.getAttribute("ticTypesVO");
%>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <title>新增票種</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <!-- Bootstrap JS -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 20px;
        }
        .container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .form-group {
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>新增票種</h2>
        <%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
			<font style="color:red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color:red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
        <form action="tictypes.do" method="post">
            <div class="form-group">
                <label for="ticketName">票種名稱</label>
                <input type="text" class="form-control" id="ticketTypeName" name="ticketTypeName" >
            </div>
            <div class="form-group">
                <label for="ticketPrice">價格</label>
                <input type="number" class="form-control" id="ticketPrice" name="ticketPrice" >
            </div>
            
            <input type="hidden" name="action" value="insert">
            <button type="submit" class="btn btn-primary">保存</button>
        </form>
    </div>
    
</body>
</html>
