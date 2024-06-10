<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.article.model.*"%>
<%@ page import="java.util.*"%>

<%
ArticleService articleSvc = new ArticleService();
List<ArticleVO> list = articleSvc.getAll();
pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
<meta charset="UTF-8">
<title>討論區-文章</title>
<!-- icon link -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.11.3/css/jquery.dataTables.css">
<!-- jQuery -->
<script type="text/javascript" charset="utf8"
	src="https://code.jquery.com/jquery-3.5.1.js"></script>
<!-- DataTables JS -->
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.js"></script>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap"
	rel="stylesheet">
<style>
body {
	margin: 0;
	font-family: 'Poppins', sans-serif;
	color: #fff;
	background-color: #0c0c0c;
}

header {
	background-color: #000;
	position: fixed; /* Fixed positioning */
	width: 100%; /* Full width */
	top: 0; /* Positioned at the top */
	z-index: 1000; /* Ensure it is above other elements */
}

.container99 {
	width: 90%;
	max-width: 1200px;
	margin: 0 auto;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

nav ul {
	list-style: none;
	display: flex;
	gap: 30px; /* Increased gap between items */
	margin: 0;
	padding: 0;
}

nav ul li a {
	color: #fff;
	text-decoration: none;
	font-weight: 600;
	font-size: 18px; /* Increased font size */
	padding: 10px 15px; /* Added padding */
	transition: color 0.3s ease; /* Added transition for smooth effect */
}

nav ul li a:hover {
	color: #ffd700; /* Change text color on hover */
}

.auth-buttons button {
	background-color: #ffd700;
	border: none;
	padding: 10px 20px;
	cursor: pointer;
	font-size: 18px; /* Increased font size */
	border-radius: 5px;
	font-weight: 600;
	transition: background-color 0.3s ease;
	/* Added transition for smooth effect */
}

.auth-buttons button:hover {
	background-color: #e0c000; /* Change background color on hover */
}

/* Add padding to the body to prevent content from being hidden behind the fixed header */
body {
	padding-top: 80px; /* Adjust based on the header height */
}
</style>
<style>
body {
	font-family: 'Arial', sans-serif;
	background-color: #121212; /* 黑色背景 */
	margin: 0;
	color: #e0e0e0; /* 淺灰色文字 */
}

h2 {
	color: #1e88e5; /* 藍色標題 */
	font-size: 2em;
	margin-bottom: 20px;
	border-bottom: 2px solid #1e88e5;
	display: inline-block;
	padding-bottom: 5px;
}

table.dataTable {
	width: 100%;
	border-collapse: separate;
	border-spacing: 0 10px;
}

table.dataTable thead {
	background-color: #333333; /* 深灰色表頭 */
	color: #ffffff;
	text-align: left;
}

table.dataTable thead th {
	padding: 12px 15px;
	font-size: 1.1em;
}

table.dataTable tbody tr {
	background-color: #1e1e1e; /* 統一背景顏色 */
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	border-radius: 8px;
	transition: transform 0.2s ease;
}
/* 明確禁用滑鼠移過時的背景顏色變化 */
table.dataTable tbody tr:hover {
	background-color: #1e1e1e !important; /* 保持原背景顏色 */
	transform: scale(1.02);
}

table.dataTable tbody td {
	padding: 15px;
	text-align: center;
	font-size: 1em;
	color: #e0e0e0; /* 淺灰色文字 */
}

table.dataTable tbody td.article-column {
	text-align: left;
	width: 400px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.article-column .title {
	font-size: 1.2em;
	font-weight: bold;
	color: #f5f5f5; /* 淺色標題文字 */
	margin-bottom: 5px;
}

.article-column .content {
	font-size: 0.9em;
	color: #a0a0a0; /* 灰色內容文字 */
}

.edit-button {
	/*            background-color: #1e88e5; /* 藍色按鈕 */ */
	/*             color: #ffffff; */
	border: none;
	padding: 8px 12px;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s ease;
	font-size: 1em;
}

.edit-button:hover {
	background-color: #1565c0;
}

.main-column {
	max-width: 800px;
	max-height: 50px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}

/* 禁用 DataTables 的條紋效果 */
table.dataTable.stripe tbody tr.odd, table.dataTable.display tbody tr.odd,
	table.dataTable.stripe tbody tr.even, table.dataTable.display tbody tr.even
	{
	background-color: #1e1e1e !important; /* 統一為深色背景 */
}
</style>
<style>
body {
	margin: 0;
	font-family: Arial, sans-serif;
	background-color: #000;
	color: #fff;
}

.footer {
	background-color: #1c1c1c;
	padding: 20px;
	text-align: center;
}

.footer .nav-links {
	margin-bottom: 10px;
}

.footer .nav-links a {
	margin: 0 15px;
	color: #fff;
	text-decoration: none;
	font-size: 14px;
}

.footer .nav-links a:hover {
	text-decoration: underline;
}

.footer .logo {
	display: block;
	margin: 0 auto 10px;
	max-width: 150px;
}

.footer .social-icons {
	margin: 10px 0;
}

.footer .social-icons a {
	margin: 0 10px;
	color: #fff;
	text-decoration: none;
	font-size: 20px;
}

.footer .social-icons a:hover {
	color: #ccc;
}

.footer .copyright {
	font-size: 12px;
	color: #ccc;
}

.footer .policy-links {
	margin-top: 10px;
}

.footer .policy-links a {
	margin: 0 5px;
	color: #ccc;
	text-decoration: none;
	font-size: 12px;
}

.footer .policy-links a:hover {
	text-decoration: underline;
}
</style>
</head>
<body>
	<header>
		<div class="container99">
			<div class="logo">
				<h1>
					<a href="/Time/index.html" style="color: white; text-decoration: none;">Time影城</a>
				</h1>
			</div>
			<nav>
				<ul>
					<li><a href="/Time/front-end/movie/movie.html">電影</a></li>
					<li><a href="/Time/front-end/buytickets/buy_tickets_page.html">立即購票</a></li>
					<li><a href="#">周邊商品</a></li>
					<li><a href="/Time/front-end/article/home.jsp">討論區</a></li>
					<li><a href="#">會員中心</a></li>
						<li style="color: yellow;  margin-left: 30px;">
						<i class="fas fa-user" style="margin-right: 5px;"></i>
					</li>
					<li id="member"
						style="color: yellow;  margin-left: -25px;">
						<span id="memberName"></span>
					</li>
				</ul>
			</nav>
			<div class="auth-buttons">
				<button>登入/註冊</button>
			</div>
		</div>
	</header>
	<div style="height: 130px;"></div>
	<div
		style="position: relative; margin-bottom: 20px; margin-right: 20px;">
		<button id="addButton" class="edit-button"
			style="position: absolute; top: 0; right: 0;"
			onclick="location.href='<%=request.getContextPath()%>/front-end/article/addArticle.jsp'">新增文章</button>
	</div>
	<table id="ticketTable" class="display" style="padding: 20px;">
		<thead>
			<tr>
				<th>類型</th>
				<th class="main-column">文章</th>
				<th>人氣</th>
				<th>發佈時間</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="articleVO" items="${list}">
				<tr>
					<%--                 <td>【${articleVO.typeId}】</td> --%>
					<td><c:choose>
							<c:when test="${articleVO.typeId == 1}">
				            【劇情片】
				        </c:when>
							<c:when test="${articleVO.typeId == 2}">
				            【動作片】
				        </c:when>
							<c:when test="${articleVO.typeId == 3}">
				            【科幻片】
				        </c:when>
							<c:when test="${articleVO.typeId == 4}">
				            【喜劇片】
				        </c:when>
							<c:when test="${articleVO.typeId == 5}">
				            【愛情片】
				        </c:when>
							<c:when test="${articleVO.typeId == 6}">
				            【戰爭片】
				        </c:when>
							<c:when test="${articleVO.typeId == 7}">
				            【恐怖片】
				        </c:when>
							<c:when test="${articleVO.typeId == 8}">
				            【動畫片】
				        </c:when>
							<c:when test="${articleVO.typeId == 9}">
				            【紀錄片】
				        </c:when>
						</c:choose></td>

					<td class="main-column"><a
						href="<%= request.getContextPath() %>/front-end/article/article.do?action=getOne_For_Display&articleId=${articleVO.articleId}">
							<div class="title">${articleVO.theme}</div>
							<div class="content">${articleVO.articleContent}</div>
					</a></td>
					<td>${articleVO.browsePeoples}</td>
					<td>${articleVO.releaseTime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div style="height: 50px;"></div>

	<div class="footer">
		<div class="nav-links">
			<a href="#">工作機會</a> <a href="#">關於</a> <a href="#">客戶支援</a> <a
				href="#">聯繫我們</a> <a href="#">媒體報導</a> <a href="#">API</a>
		</div>
		<div class="social-icons">
			<a href="#"><i class="fab fa-twitter"></i></a> <a href="#"><i
				class="fab fa-facebook"></i></a> <a href="#"><i
				class="fab fa-reddit"></i></a> <a href="#"><i
				class="fab fa-instagram"></i></a> <a href="#"><i
				class="fab fa-youtube"></i></a>
		</div>
		<div class="copyright">© 2024 Time娛樂股份有限公司 ( Entertainment,
			Inc.) 在此提及的所有 商標 是其各自所有人的財產。台灣商頁影城有限公司台灣分公司 28992446</div>
		<div class="policy-links">
			<a href="#">隱私</a> <a href="#">法律聲明</a> <a href="#">條款</a> <a
				href="#">Cookie 政策</a> <a href="#">Cookie 設定</a>
		</div>
	</div>
	<script>
		$(document).ready(function() {
			$('#ticketTable').DataTable({
				"ordering" : false,
				"searching" : false,
				"lengthChange" : false,
				"pagingType" : "simple_numbers",
				"language" : {
					"zeroRecords" : "沒有找到符合的資料",
					"info" : "顯示第 _PAGE_ 頁，共 _PAGES_ 頁",
					"infoEmpty" : "沒有符合的資料",
					"infoFiltered" : "(從 _MAX_ 筆資料過濾)",
					"paginate" : {
						"previous" : "上一頁",
						"next" : "下一頁"
					}
				},
				"columnDefs" : [ {
					"className" : "article-column",
					"targets" : 1
				} ]
			});
		});
		
		function fetchMemberName() {
	        var xhr = new XMLHttpRequest();
	        var url = "/Time/getMemberName?timestamp=" + new Date().getTime(); // 添加時間戳來防止緩存
	        xhr.open("GET", url, true);
	        xhr.onreadystatechange = function() {
	            if (xhr.readyState === 4) {
	                if (xhr.status === 200) {
	                    var response = JSON.parse(xhr.responseText);
	                    var username = response.memberName;
	                    var memberElement = document.getElementById('member');
	                    if (username) {
	                        memberElement.textContent =username;
	                        document.querySelector('.auth-buttons button').textContent = "重新登入";
	                    } else {
	                        memberElement.textContent = "未登入會員";
	                    }
	                } else {
	                    console.error("Error fetching member name: " + xhr.status);
	                }
	            }
	        };
	        xhr.send();
	    }
	    // 當頁面加載時獲取會員名稱
	    window.onload = fetchMemberName;
	</script>
</body>
</html>
