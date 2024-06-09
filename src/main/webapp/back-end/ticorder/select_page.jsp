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
    <title>票種表格</title>
    <!-- DataTables CSS -->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.3/css/jquery.dataTables.css">
    <!-- Bootstrap CSS for Modal -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- jQuery -->
    <script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <!-- DataTables JS -->
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.js"></script>
    <!-- Bootstrap JS for Modal -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 20px;
        }
        table.dataTable {
            width: 100%;
            border-collapse: collapse;
        }
        table.dataTable thead {
            background-color: #343a40;
            color: #ffffff;
        }
        table.dataTable thead th {
            padding: 10px;
            text-align: center;
        }
        table.dataTable tbody tr:nth-child(odd) {
            background-color: #ffffff;
        }
        table.dataTable tbody tr:nth-child(even) {
            background-color: #f1f1f1;
        }
        table.dataTable tbody td {
            padding: 10px;
            text-align: center;
        }
        .edit-button {
            background-color: #007bff;
            color: #ffffff;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .edit-button:hover {
            background-color: #0056b3;
        }
        </style>
</head>
<body>
<h2 >訂單管理</h2>
<header style="padding: 1rem 0rem;">
    <div style="padding: 0rem 3rem;">
        
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
    

    <table id="ticketTable" class="display dataTable no-footer">
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
		                <th scope="row" style="text-align: center;">${order.movieOrderId}</th>
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

<script>
        $(document).ready(function() {
            $('#ticketTable').DataTable({
            	"pageLength": 6,
                "ordering": false,
                "searching": false,
                "lengthChange": false,
                "pagingType": "simple_numbers",
                "language": {
                    "zeroRecords": "沒有找到符合的資料",
                    "info": "顯示第 _PAGE_ 頁，共 _PAGES_ 頁",
                    "infoEmpty": "沒有符合的資料",
                    "infoFiltered": "(從 _MAX_ 筆資料過濾)",
                    "paginate": {
                        "previous": "上一頁",
                        "next": "下一頁"
                    }
                }
            });
        });
    </script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.min.js"
        integrity="sha384-skAcpIdS7UcVUC05LJ9Dxay8AXcDYfBJqt1CJ85S/CFujBsIzCIv+l9liuYLaMQ/"
        crossorigin="anonymous"></script>
</body>
</html>
