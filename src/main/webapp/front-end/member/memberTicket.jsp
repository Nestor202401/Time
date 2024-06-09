<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>個人周邊商品訂單</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
<link
	href="https://fontawesome.com/icons/right-to-bracket?f=classic&s=solid"
	rel='stylesheet'>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"
	charset="utf-8"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<style>
.side-bar {
	background: linear-gradient(to bottom, #87619d, #1c1c1c);
	backdrop-filter: blur(15px);
	width: 250px;
	height: 100vh;
	position: fixed;
	top: 0;
	left: 0;
	overflow-y: auto;
	transition: 0.6s ease;
	transition-property: left;
}

.side-bar::-webkit-scrollbar {
	width: 0px;
}

.side-bar.active {
	left: 0;
}

.side-bar .menu {
	width: 100%;
	margin-top: 30px;
}

.side-bar .menu .item {
	position: relative;
	cursor: pointer;
}

.side-bar .menu .item a {
	color: #fff;
	font-size: 16px;
	text-decoration: none;
	display: block;
	padding: 5px 25px;
	line-height: 60px;
}

.side-bar .menu .item a:hover {
	background: #33363a;
	transition: 0.3s ease;
}

.side-bar .menu .item i {
	margin-right: 20px;
}

.side-bar .menu .item a .dropdown {
	position: absolute;
	right: 0;
	margin: 20px;
	transition: 0.3s ease;
}

.side-bar .menu .item .sub-menu {
	background: #262627;
	display: none;
}

.side-bar .menu .item .sub-menu a {
	padding-left: 64px;
}

.rotate {
	transform: rotate(90deg);
}

.table {
	width: 82%;
	margin-left: 220px;
	border: 2px solid #ddd;
}


.title {
	margin-left: 230px;
}
.modal-dialog {
    max-width: 700px;
    margin: 1.75rem auto;
}

.modal-header {
    justify-content: center;
}

.modal-body {
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: Arial, sans-serif;
    width: 100%;
    padding: 0;  /* 确保 modal-body 没有额外的 padding */
}

.info-table {
    display: flex;
    flex-direction: column;
    width: 100%;
}

.info-row {
    display: flex;
    align-items: center;
    border-bottom: 1px solid #ddd;
    width: 100%;
}

.info-label {
    background-color: hwb(122 21% 38%);
    color: white;
    padding: 10px;  
    width: 28%;
    text-align: center;
    height: 150px;
    padding-top: 60px;
}

.info-content {
    padding: 10px;
    flex-grow: 1;
    text-align: center;
}

.ticket-table {
   	width: 50%;
    margin-top: 8px;
    margin-bottom: 8px;
    border-collapse: collapse;
    margin-left: 130px;
       
}

.ticket-table .ticket-item {
    padding: 10px;
    border: 1px solid #ddd;
    text-align: center;
}

.ticket-table .ticket-item:nth-child(3n+1) {
    background-color: hwb(122 21% 38%);
    color: white;
}




</style>
</head>

<body>
	<div class="side-bar">
		<div class="menu">
			<div class="item">
				<a href="#"><i class="fas fa-home"></i>回首頁</a>
			</div>
			<div class="item">
				<a
					href="${pageContext.request.contextPath}/front-end/member/memberProfile.jsp"><i
					class="fas fa-user"></i>會員中心</a>
			</div>
			<div class="item">
				<div class="item"><a href="${pageContext.request.contextPath}/mem?action=comment"><i class="fas fa-comments"></i>討論區</a></div>
			</div>
			<div class="item">
				<a class="sub-btn"><i class="fas fa-cart-arrow-down"></i>查看個人訂單<i
					class="fas fa-angle-right dropdown"></i></a>
				<div class="sub-menu">
					<a href="#" class="sub-item">電影票查詢</a> <a href="${pageContext.request.contextPath}/mem?action=product" class="sub-item">個人周邊商品</a>
				</div>
			</div>
		</div>
	</div>

	<div class="container mt-5">
		<h2 class="title">已預訂</h2>
		<table class="table table-bordered table-hover"
			style="width: 82%; border: 2px solid #ddd;">
			<thead>
				<tr>
					<th>購票日期</th>
					<th>電影名稱</th>
					<th>狀態</th>
					<th>付款方式</th>
					<th>金額</th>

					<th>詳細資訊</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="order" items="${orders}">
					<tr>
						<td>${order.buyTicketsDate}</td>
						<td><c:forEach var="ticket" items="${order.ticketLists}"
								varStatus="iterStat">
								<c:if test="${iterStat.index == 0}">
                                ${ticket.movieVO.movieName}
                            </c:if>
							</c:forEach></td>
						<td>${order.movieOrderStatusText}</td>
						<td>現場付款</td>
						<td>${order.movieOrderTotal}</td>
						<td><button class="btn btn-primary"
								onclick="showDetails(${order.movieOrderId})">查看</button></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>


	<c:forEach var="order" items="${orders}">
		<div class="modal fade" id="detailsModal_${order.movieOrderId}" tabindex="-1" aria-labelledby="detailsModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="detailsModalLabel" style="color: #52c252; margin-left: 300px;">訂票名細</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body info-table">
						<div class="info-row">
							<div class="info-label">電影名稱：</div>
							<div class="info-content">
								${order.ticketLists[0].movieVO.movieName}
							</div>
						</div>
						<div class="info-row">
							<div class="info-label">播映時間：</div>
							<div class="info-content">
								<c:choose>
									<c:when test="${order.ticketLists[0].movieTimeVO.showTime == 0}">6:00</c:when>
									<c:when test="${order.ticketLists[0].movieTimeVO.showTime == 1}">12:00</c:when>
									<c:when test="${order.ticketLists[0].movieTimeVO.showTime == 2}">18:00</c:when>
								</c:choose>
							</div>
						</div>
						<div class="info-row">
							<div class="info-label">票種：</div>
							<div class="info-content">
								<table class="ticket-table">
									<tbody>
										<c:forEach var="ticket" items="${order.ticketLists}">
											<tr>
												<td class="ticket-item small">${ticket.ticketTypesVO.ticketTypeName}</td>
												<td class="ticket-item ticket-value1">X1張</td>
												<td class="ticket-item ticket-value">${ticket.ticketTypesVO.ticketPrice}元</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
<!-- 						<div class="info-row"> -->
<!-- 							<div class="info-label">影廳：</div> -->
<!-- 							<div class="info-content"> -->
<%-- 								${order.ticketLists[0].cinemaId.cinemaName} --%>
<!-- 							</div> -->
<!-- 						</div> -->
						<div class="info-row">
							<div class="info-label">座位：</div>
							<div class="info-content">
								<c:forEach var="ticket" items="${order.ticketLists}" varStatus="iterStat">
									<c:if test="${iterStat.index != 0}">,</c:if>
									<span style="display: inline-block;">${ticket.seatNumber}</span>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>

	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			// 側邊攔的下拉功能
			$('.sub-btn').click(function () {
				 event.stopPropagation();
				$(this).next('.sub-menu').slideToggle();
				$(this).find('.dropdown').toggleClass('rotate');
			});
		});

		function showDetails(orderId) {
			var modalId = '#detailsModal_' + orderId;
			$(modalId).modal('show');
		}
	</script>
</body>
</html>



