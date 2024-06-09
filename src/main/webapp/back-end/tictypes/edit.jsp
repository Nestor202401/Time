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
    <title>�קﲼ��</title>
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
        <h2>�קﲼ��</h2>
<%-- ���~��C --%>
		<c:if test="${not empty errorMsgs}">
			<font style="color:red">�Эץ��H�U���~:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color:red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
		
        <form action="tictypes.do" method="post">
            <div class="form-group">
                <label for="ticketId">����ID</label>
                <input type="text" class="form-control" id="ticketTypesId" name="ticketId" value="<%=ticTypesVO.getTicketTypesId()%>" readonly>
            </div>
            <div class="form-group">
                <label for="ticketName">���ئW��</label>
                <input type="text" class="form-control" id="ticketName" name="ticketTypeName" value="<%=ticTypesVO.getTicketTypeName()%>" required>
            </div>
            <div class="form-group">
                <label for="ticketPrice">����</label>
                <input type="number" class="form-control" id="ticketPrice" name="ticketPrice" value="<%=ticTypesVO.getTicketPrice()%>" required>
            </div>
            
            <input type="hidden" name="action" value="update">
			<input type="hidden" name="ticketTypesId" value="<%=ticTypesVO.getTicketTypesId()%>">
            <button type="submit" class="btn btn-primary">�O�s</button>
        </form>
    </div>
</body>
</html>
