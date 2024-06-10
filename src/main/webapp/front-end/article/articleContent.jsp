<!-- 當前會員登入時,這行程式碼註解掉 line 202  215-->
<%
session.setAttribute("memberId", 3);
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${ articleVO.theme }</title>

<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
}

header {
	background-color: #000;
	padding: 20px 0;
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
	color: #fff; /* 上標題 功能鍵的字體顏色 */
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
}

h2 {
	/*           color: #1e88e5;  藍色標題 */
	font-size: 2em;
	margin-bottom: 20px;
	/*             border-bottom: 2px solid #1e88e5; */
	/*             display: inline-block; */
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

.edit-button {
	background-color: #1e88e5; /* 藍色按鈕 */
	color: #ffffff;
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
<style>
.container {
	max-width: 800px;
	margin: 0 auto;
	padding: 20px;
}

.article {
	background-color: black;
	border-radius: 5px;
	padding: 20px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	color: white;
}

/**.function-area {
			display: flex;
			justify-content: space-between;
		}
		**/
.metadata {
	color: #666;
	font-size: 14px;
}

.content {
	line-height: 1.6;
}

.comment-button {
	padding: 10px 20px;
	background-color: #007bff;
	color: #fff;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 16px;
	margin-top: 20px;
}

.comment-button:hover {
	background-color: #0056b3;
}

::backdrop {
	background-image: linear-gradient(#0056b3);
	opacity: 0.75;
}

.input-group {
	display: flex;
}

.input-group>div {
	flex: 1;
	margin-right: 10px; /* Spacing between inputs */
}

.input-group>div:last-child {
	margin-right: 0; /* Remove margin for the last input */
}

#commentContent {
	width: 100%;
}

.comment {
	display: flex;
	align-items: center;
	margin-bottom: 20px;
}

.avatar {
	width: 50px;
	height: 50px;
	border-radius: 50%;
	background-color: #ccc;
	margin-right: 15px;
}

.comment-content {
	flex: 1;
}

.comment-author {
	font-weight: bold;
	margin-bottom: 5px;
}

.comment-time {
	font-size: 0.8em;
	color: #666;
}
</style>
</head>


<%-- ${ articleVO.articleId } --%>
<%-- ${ articleVO.typeId } --%>
<%-- ${ articleVO.memberId } --%>
<%-- ${ articleVO.theme } --%>
<%-- ${ articleVO.articleContent } --%>
<%-- ${ articleVO.browsePeoples } --%>
<%-- ${ articleVO.articleStatus } --%>
<%-- ${ articleVO.releaseTime } --%>

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
				</ul>
			</nav>
			<div class="auth-buttons">
				<button>登入/註冊</button>
			</div>
		</div>
	</header>



	<%--  留言按鈕 按下去  跳到新增留言畫面 --%>
	<div class="container">
		<div class="article">
			<h1>${ articleVO.theme }</h1>
			<div class="function-area">
				<p class="metadata">Views: ${ articleVO.browsePeoples } |
					Published: ${ articleVO.releaseTime }</p>

				<%-- <div style="vertical-align: middle; text-align: right;">
					<button type="button" class="home-button" onclick="window.location.href='<%=request.getContextPath()%>/front-end/article/home.jsp'">回到首頁</button>
				</div>
				--%>

				<div style="vertical-align: middle; text-align: right;">
					<button type="button" class="home-button"
						onclick="window.location.href='<%=request.getContextPath()%>/front-end/article/home.jsp'">回到首頁</button>
					<button type="button" id="reportButton"
						style="background-color: red;">檢舉</button>
				</div>
			</div>
			<hr>
			<p class="content">${ articleVO.articleContent }</p>
			<!-- 留言表單 -->
			<form
				action="<%=request.getContextPath()%>/front-end/comments/commentadd.jsp"
				method="post">
				<input type="hidden" name="articleId"
					value="${ articleVO.articleId }">

			</form>
		</div>
	</div>

	<div class="container">
		<div class="article">
			<h2>留 言</h2>
			<!-- 			<div class="comment"> -->
			<!-- 				<div class="avatar"></div> -->
			<!-- 				<div class="comment-content"> -->
			<!-- 					<div class="comment-author">用戶1</div> -->
			<!-- 					<div class="comment-text">這是一條留言。</div> -->
			<!-- 					<div class="comment-time">2024-06-04 10:00</div> -->
			<!-- 				</div> -->
			<!-- 			</div> -->

			<!-- 			<div class="comment"> -->
			<!-- 				<div class="avatar"></div> -->
			<!-- 				<div class="comment-content"> -->
			<!-- 					<div class="comment-author">用戶2</div> -->
			<!-- 					<div class="comment-text">另一條留言。</div> -->
			<!-- 					<div class="comment-time">2024-06-04 10:05</div> -->
			<!-- 				</div> -->
			<!-- 			</div> -->

			<c:forEach var="commentsVO" items="${commentsVOs}">
				<div class="comment">
					<div class="avatar"></div>
					<div class="comment-content">
						<div class="comment-author">${commentsVO.commentId}</div>
						<div class="comment-text">${commentsVO.commentContent}</div>
						<div class="comment-time">${commentsVO.releaseTime}</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<div class="container">
		<div class="article">
			<h2>新 增 留 言</h2>
			<form
				action="<%=request.getContextPath()%>/front-end/comments/comments.do"
				method="post">
				<textarea id="commentContent" name="commentContent" rows="5"
					required></textarea>
				<input type="hidden" name="articleId"
					value="${ articleVO.articleId }"> <input type="hidden"
					name="memberId" value="${memberId}">
				<!-- !!!! 記得改 -->
				<input type="hidden" name="action" value="insert">
				<button type="submit" class="comment-button">留言</button>
			</form>
		</div>
	</div>

	<dialog>
	<button autofocus>Close</button>
	<form
		action="<%=request.getContextPath()%>/front-end/reports/reports.do"
		method="post">

		<div>
			<label for="memberId">會員:</label> <input type="text" id="memberId"
				name="memberId" value="${memberId}" required>
			<!-- !!!! 記得改 -->
		</div>

		<div>
			<label for="articleId">文章:</label> <input type="text" id="articleId"
				name="articleId" value="${ articleVO.articleId }" required>
		</div>

		<div class="form-group">
			<label for="reportType">檢 舉 類 型:</label> <select id="reportType"
				name="reportType"
				style="width: 100%; height: 40px; font-size: 16px;">
				<option value="1">仇恨言論</option>
				<option value="2">散佈廣告</option>
				<option value="3">其他</option>
			</select>
		</div>
		<div class="form-group">
			<label for="reportReason">檢 舉 原 因:</label>
			<textarea id="reportReason" name="reportReason" placeholder="請輸入..."></textarea>
		</div>

		<input type="hidden" name="reportStatus" value="0"> <input
			type="hidden" name="commentId" value="null">

		<div class="form-group">
			<input type="hidden" name="action" value="insert"> <input
				type="submit" value="送出">
		</div>
	</form>
	</dialog>

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


	<script type="text/javascript">
		const dialog = document.querySelector("dialog");
		const showButton = document.querySelector("#reportButton");
		const closeButton = document.querySelector("dialog button");
	
		// "Show the dialog" button opens the dialog modally
		
		showButton.addEventListener("click", () => {
		  dialog.showModal();
		});
	
		// "Close" button closes the dialog
		closeButton.addEventListener("click", () => {
		  dialog.close();
		});
	</script>


</body>
</html>