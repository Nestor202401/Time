<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>查询结果</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            padding: 20px;
        }
        h2 {
            color: #333;
        }
        .cinema {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .screen {
            background-color: #555;
            color: white;
            padding: 10px;
            margin-bottom: 20px;
            width: 80%;
            text-align: center;
            border-radius: 5px;
        }
        .seats {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .row {
            display: flex;
            justify-content: center;
            margin-bottom: 5px;
        }
        .seat {
            width: 40px;
            height: 40px;
            margin: 5px;
            display: inline-block;
            text-align: center;
            line-height: 40px;
            border: 2px solid #ddd;
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .seat.available {
            background-color: #90ee90;
        }
        .seat.unavailable {
            background-color: #ffcccb;
            cursor: not-allowed;
        }
        .seat.selected {
            background-color: #add8e6;
        }
        .seat:hover:not(.unavailable) {
            border-color: #333;
        }
        .button-container {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
        button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
    <script>
        function selectSeat(seatNumber) {
            var seatElement = document.getElementById('seat-' + seatNumber);
            if (seatElement.classList.contains('available')) {
                seatElement.classList.toggle('selected');
            }
        }

        function submitSelectedSeats() {
            var selectedSeats = document.querySelectorAll('.selected');
            var seatNumbers = [];
            selectedSeats.forEach(function(seat) {
                seatNumbers.push(seat.getAttribute('data-seat-number'));
            });
            document.getElementById('seatNumbers').value = seatNumbers.join(',');
            document.getElementById('seatForm').submit();
        }
    </script>
</head>
<body>
<h2>场次座位查询结果</h2>

<p>场次ID: ${sessionId}</p> <!-- 显示场次ID -->

<div class="cinema">
    <div class="screen">屏幕</div>
    <div class="seats">
        <c:forEach var="seat" items="${seats}" varStatus="status">
            <c:if test="${status.index == 0 || seat.seatNumber.substring(0, 1) != seats[status.index - 1].seatNumber.substring(0, 1)}">
                <div class="row">
            </c:if>
            <div id="seat-${seat.seatNumber}" data-seat-number="${seat.seatNumber}" class="seat ${seat.seatStatus == 0 ? 'available' : 'unavailable'}" onclick="selectSeat('${seat.seatNumber}')">
                ${seat.seatNumber}
            </div>
            <c:if test="${status.index == seats.size() - 1 || seat.seatNumber.substring(0, 1) != seats[status.index + 1].seatNumber.substring(0, 1)}">
                </div>
            </c:if>
        </c:forEach>
    </div>
    <div class="button-container">
        <button type="button" onclick="submitSelectedSeats()">送出</button>
    </div>
</div>

<form id="seatForm" action="cinema.do" method="post">
    <input type="hidden" name="action" value="update_seat_status">
    <input type="hidden" name="sessionId" value="${sessionId}"> <!-- 隐藏字段传递 sessionId -->
    <input type="hidden" id="seatNumbers" name="seatNumbers">
    <input type="hidden" name="status" value="1">
</form>

</body>
</html>
