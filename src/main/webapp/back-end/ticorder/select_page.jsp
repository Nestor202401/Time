<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.ticorder.model.*"%>
<%@page import="java.util.*"%>

<c:set var="ticOrderService" value="<%= new com.ticorder.model.TicketOrderService() %>"/>
<c:set var="list" value="${requestScope.ticOrderListData}"/>
<c:if test="${empty list && empty errorMsgs}">
    <c:set var="list" value="${ticOrderService.getAll()}"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>訂單管理</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
</head>
<body>
<header style="padding: 1rem 0rem;">
    <div style="padding: 0rem 3rem;">
        <h1 class="h3 fw-normal">訂單管理</h1>
        <form class="row g-3" action="TicketOrderServlet.do" method="post">
            <div class="col-auto">
                <input type="text" class="form-control" name="movieOrderId" placeholder="請輸入訂單ID">
            </div>
            <div class="col-auto">
                <input type="text" class="form-control" name="memberAccount" placeholder="請輸入會員帳號">
            </div>
            <div class="col-auto">
                <input type="hidden" name="action" value="listTicketOrder_ByCompositeQuery">
                <button type="submit" class="btn btn-primary mb-3">查詢</button>
            </div>
        </form>
    </div>
</header>

<div style="padding: 1.5rem 3rem;">
    

    <table class="table table-hover">
        <thead> 
        <tr>
            <th scope="col">訂單ID</th>
            <th scope="col">會員帳號</th>
            <th scope="col">狀態</th>
            <th scope="col">購票時間</th>
            <th scope="col">總金額</th>
        </tr>
        </thead>
        <tbody>
		    <%-- 顯示錯誤訊息 --%>
		    <c:if test="${not empty errorMsgs}">
		        <tr>
		            <c:forEach var="message" items="${errorMsgs}">
		                <td colspan="5" style="color:red; text-align: center;">${message}</td>
		            </c:forEach>
		        </tr>
		    </c:if>
    		<%-- 使用 JSTL 顯示訂單資料 --%>
    		<c:if test="${not empty list}">
		        <c:forEach var="order" items="${list}">
		            <tr>
		                <th scope="row">${order.movieOrderId}</th>
		                <td>${order.memberId.memberAccount}</td>
		                <td>${order.movieOrderStatusText}</td>
		                <td>${order.buyTicketsDate}</td>
		                <td>${order.movieOrderTotal}</td>
		            </tr>
		        </c:forEach>
	        </c:if>
		</tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.min.js"
        integrity="sha384-skAcpIdS7UcVUC05LJ9Dxay8AXcDYfBJqt1CJ85S/CFujBsIzCIv+l9liuYLaMQ/"
        crossorigin="anonymous"></script>
</body>
</html>
