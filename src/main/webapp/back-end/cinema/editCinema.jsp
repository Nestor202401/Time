<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>影院結果</title>
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
            background-color: #ff0000;
        }
        .seat.selected {
            background-color: #add8e6;
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
        function toggleSeatStatus(seat) {
            seat.dataset.statusChanged = "true";
            if (seat.classList.contains('available')) {
                seat.classList.remove('available');
                seat.classList.add('unavailable');
                seat.dataset.status = 1;
            } else if (seat.classList.contains('unavailable')) {
                seat.classList.remove('unavailable');
                seat.classList.add('available');
                seat.dataset.status = 0;
            }
        }

        function submitSeatStatus() {
            const seats = document.querySelectorAll('.seat[data-status-changed="true"]');
            const seatNumbers = [];
            let newStatus = -1;
            seats.forEach(seat => {
                seatNumbers.push(seat.innerText);
                newStatus = seat.dataset.status; // 假設所有更改的座位都有相同的新狀態
            });
            const form = document.getElementById('seatStatusForm');
            form.seatNumbers.value = seatNumbers.join(',');
            form.newStatus.value = newStatus;

            // 打印 form 的值以調試
            console.log("cinemaName: ", form.cinemaName.value);
            console.log("seatNumbers: ", form.seatNumbers.value);
            console.log("newStatus: ", form.newStatus.value);

            form.submit();
        }
    </script>
</head>
<body>
    
    <c:if test="${not empty cinemaVO}">
        <c:set var="cinemaName" value="${param.cinemaName}" />
        <div class="cinema">
            <div class="screen">螢幕</div>
            <h3>影廳名稱: ${cinemaName}</h3>
            <form id="seatStatusForm" action="cinema.do" method="post">
                <input type="hidden" name="action" value="update_seat_status_batch" />
                <input type="hidden" name="cinemaName" value="${cinemaName}" />
                <input type="hidden" name="seatNumbers" />
                <input type="hidden" name="newStatus" />

                <c:set var="currentRow" value="" />
                <div class="seats">
                    <c:forEach var="cinema" items="${cinemaVO}">
                        <c:if test="${fn:substring(cinema.seatNumber, 0, 1) != currentRow}">
                            <c:if test="${currentRow != ''}">
                                </div> <!-- 關閉前一個 row -->
                            </c:if>
                            <c:set var="currentRow" value="${fn:substring(cinema.seatNumber, 0, 1)}" />
                            <div class="row">
                        </c:if>
                        <div class="seat ${cinema.seatStatus == 0 ? 'available' : 'unavailable'}" data-status="${cinema.seatStatus}" onclick="toggleSeatStatus(this)">
                            ${cinema.seatNumber}
                        </div>
                    </c:forEach>
                    <c:if test="${currentRow != ''}">
                        </div> <!-- 關閉最後一個 row -->
                    </c:if>
                </div>
                <div class="button-container">
                    <button type="button" onclick="submitSeatStatus()">更新座位狀態</button>
                </div>
            </form>
        </div>
    </c:if>
    <c:if test="${empty cinemaVO}">
        <p>未找到影院。</p>
    </c:if>
</body>
</html>
