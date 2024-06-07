<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.cinema.model.*" %>
<%@ page import="java.util.*" %>
<%
CinemaService movieSvc = new CinemaService();
List<CinemaVO> list = movieSvc.getAllCinemas();
if(request.getAttribute("cinemaVO")==null) pageContext.setAttribute("cinemaVO",list);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cinema Results</title>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            max-width: 1000px;
            width: 100%;
        }
        h2 {
            color: #333333;
            text-align: center;
            margin-bottom: 20px;
        }
        .form-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            background-color: #e2f0e2;
            padding: 10px;
            border-radius: 10px;
        }
        .form-container form, .form-container .buttons {
            display: flex;
            align-items: center;
        }
        form label {
            margin-right: 10px;
            font-weight: bold;
            color: #333333;
        }
        form select {
            padding: 5px;
            margin-right: 10px;
            border: 1px solid #cccccc;
            border-radius: 5px;
        }
        form button {
            padding: 10px 20px;
            background-color: #68a16a;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            margin-right: 10px; /* 确保按钮之间有间距 */
        }
        form button:hover {
            background-color: #557a55;
        }
        .form-container .buttons button#openModal {
            background-color: #68a16a;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            margin-right: 0; /* 确保最后一个按钮没有右边距 */
        }
        .form-container .buttons button#openModal:hover {
            background-color: #557a55;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid #dddddd;
        }
        th {
            background-color: #68a16a;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #e2f0e2;
        }
        p {
            color: #666666;
            text-align: center;
        }
        .dataTables_wrapper .dataTables_paginate .paginate_button {
            padding: 0.5em 1em;
            margin-left: 2px;
            color: #333 !important;
            border: 1px solid transparent;
            border-radius: 2px;
        }
        .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
            border: 1px solid #68a16a;
            background-color: #68a16a;
            color: white !important;
        }
        .dataTables_wrapper .dataTables_paginate .paginate_button.current {
            background-color: #68a16a;
            color: white !important;
            border: 1px solid #68a16a;
        }
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            padding-top: 100px;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.4);
        }
        .modal-content {
            background-color: #fefefe;
            margin: auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 600px;
            border-radius: 10px;
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
    </style>
</head>
<body>

<jsp:useBean id="cinemaSvc" scope="page" class="com.cinema.model.CinemaService" />
<jsp:setProperty name="cinemaSvc" property="*" />
    
<div class="container">
    <div class="form-container">
        <form action="cinema.do" method="post">
            <label for="cinemaName">影廳名稱：</label>
            <select name="cinemaName" id="cinemaName">
                <c:forEach var="cinemaName" items="${cinemaSvc.uniqueCinemaNames}">
                    <option value="${cinemaName}">${cinemaName}</option>
                </c:forEach>
            </select>
            <input type="hidden" name="action" value="by_cinemaName">
            <button type="submit" name="type" value="submit_cinema">查詢影廳</button>
            <button type="submit" name="type" value="edit_cinema">編輯影廳</button>
        </form>
        <div class="buttons">
            <button type="button" id="openModal">新增座位表</button>
        </div>
    </div>

    <table id="cinemaTable" class="display">
        <thead>
            <tr>
                <th>Cinema ID</th>
                <th>影廳名稱</th>
                <th>座位(排)</th>
                <th>座位(列)</th>
                <th>座位狀態</th>
                <th>座位編號</th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${not empty cinemaVO}">
                <c:forEach var="cinema" items="${cinemaVO}">
                    <tr>
                        <td>${cinema.cinemaId}</td>
                        <td>${cinema.cinemaName}</td>
                        <td>${cinema.seatRow}</td>
                        <td>${cinema.seatColumn}</td>
                        <td>${cinema.seatStatus == 0 ? '可選' : '不可選'}</td>
                        <td>${cinema.seatNumber}</td>
                    </tr>
                </c:forEach>
            </c:if>
        </tbody>
    </table>
    <c:if test="${empty cinemaVO}">
        <p>No cinemas found.</p>
    </c:if>
</div>

<!-- The Modal -->
<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <iframe src="add.jsp" style="width:100%; height:400px;" frameborder="0"></iframe>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
<script>
    $(document).ready(function() {
        $('#cinemaTable').DataTable({
            searching: false,
            paging: true,
            ordering: true,
            info: true
        });
    });

    // Get the modal
    var modal = document.getElementById("myModal");

    // Get the button that opens the modal
    var btn = document.getElementById("openModal");

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // When the user clicks the button, open the modal 
    btn.onclick = function() {
        modal.style.display = "block";
    }

    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        modal.style.display = "none";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
</script>

</body>
</html>
