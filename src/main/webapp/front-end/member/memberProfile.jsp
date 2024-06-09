<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.member.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Base64"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.ZoneId"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
MemberVO member = (MemberVO) session.getAttribute("memberVO");
%>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>會員中心</title>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
<link
	href="https://fontawesome.com/icons/right-to-bracket?f=classic&s=solid"
	rel='stylesheet'>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"
	charset="utf-8"></script>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: "Poppins", sans-serif;
}

body {
	/* min-height: 100vh;*/
	background: white;
	color: white;
	background-size: cover;
	background-position: center;
}

.side-bar {
	background: linear-gradient(to bottom, #87619d, #1c1c1c);
	backdrop-filter: blur(15px);
	width: 250px;
	height: 100vh;
	/*position: fixed;*/
	float: left;
	top: 0;
	left: 0 px;
	overflow-y: auto;
	transition: 0.6s ease;
	transition-property: left;
	left: 0;
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
	padding: 6px 30px;
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

.side-bar .menu .item a.home-link {
	padding-left: 34px; /* 調整回首頁標籤往右移動 */
}

.rotate {
	transform: rotate(90deg);
}

.menu-btn {
	position: absolute;
	color: rgb(0, 0, 0);
	font-size: 35px;
	margin: 26px;
	cursor: pointer;
}

.main {
	height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 50px;
}

.info-item {
	padding: 10px;
	line-height: 3.5;
	width: 380px;
}

.member-info {
	left: 0;
	width: 380;
	background-color: white;
	color: black;
	padding: 10px;
	z-index: 999;
	margin: -16px 0px 0px 70px; /* 調整margin-top的值 */
	display: inline-block;
	margin-left: 70px;
}

.fo {
	border: 6px solid #ddd;
	border-radius: 10px;
	box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.5);
	margin-top: 10px;
	margin-left: 20px;
}

.fo3 {
	border: 6px solid #ddd;
	border-radius: 10px;
	width: 250px;
	margin-top: -19px;
	margin-left: -45px;
	height: 615px;
	box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.5);
}

.title {
	color: black;
	float: left;
	margin: 50px 0px 10px 50px; /* 調整margin-top的值 */
	width: 600px;
	margin-left: 85px;
}

.info-item span {
	margin-right: 50px;
}

form {
	width: 550px;
}

.preview {
	background: #888888;
	width: 200px;
	height: auto;
	text-align: center;
	border-radius: 50%;
	/*   border: 2px solid black; */
}

/* #file.image_uploads {
   		object-fit: contain;
   		width: 500px;
   		height: 350px;
     color: black;
   	} */
.top {
	width: auto;
	height: auto;
	float: left;
}

.navigation-button {
	display: inline-block;
	padding: 10px 20px;
	background-color: #ffecb3;
	color: hsl(0, 0%, 0%);
	margin-left: 80px;
	margin-top: 4px;
	text-decoration: none;
	border-radius: 5px;
	transition: background-color 0.3s;
	width: 150px;
}

.navigation-button:hover {
	background-color: #ffcc80;
}

#btn_submit {
	padding: 6px 13px;
	background-color: #ffecb3;
	color: #333;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 11px;
	transition: background-color 0.3s;
	margin-left: -8px;
	width: 90px;
	height: 35px;
}

#btn_submit:hover {
	background-color: #ffcc80;
}

#file_name_display {
	padding: 6px 13px;
	background-color: hwb(187 50% 7%);
	color: #333;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 11px;
	transition: background-color 0.3s;
	margin-left: 60px;
	width: 90px;
	height: 35px;
}

#file_name_display:hover {
	background-color: #66acb5;
}

button {
	border: none;
	outline: none;
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
				<a href="#"><i class="fas fa-user"></i>會員中心</a>
			</div>
			<div class="item">
				<a href="#"><i class="fas fa-comments"></i>討論區</a>
			</div>
			<div class="item">
				<a class="sub-btn"><i class="fas fa-cart-arrow-down"></i>查看個人訂單<i
					class="fas fa-angle-right dropdown"></i></a>
				<div class="sub-menu">
					<a href="${pageContext.request.contextPath}/mem?action=ticket"
						class="sub-item">電影票查詢</a> <a
						href="${pageContext.request.contextPath}/mem?action=product"
						class="sub-item">個人周邊商品</a>
				</div>
			</div>
		</div>
	</div>


	<div style="float: left; width: 500px;">
		<form method="post" action="${pageContext.request.contextPath}/mem"
			class="fo">
			<div class="title">
				<h1>會員中心</h1>
			</div>
			<div class="member-info">
				<div class="info-item">
					<span>會員編號</span> <span><%=member.getMemberId()%></span>
				</div>

				<div class="info-item">
					<span>會員姓名</span> <span><%=member.getMemberName()%></span>
				</div>

				<div class="info-item">
					<span>會員帳號</span> <span><%=member.getMemberAccount()%></span>
				</div>

				<div class="info-item">
					<span>會員信箱</span> <span><%=member.getMemberEmail()%></span>
				</div>

				<div class="info-item">
					<span>會員電話</span> <span><%=member.getMemberPhone()%></span>
				</div>

				<%
				LocalDateTime localDateTime = member.getMemberRegisterDatetime(); // 拿到 LocalDateTime物件
				Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); // 把 LocalDateTime 轉成DATE
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 定義日期格式
				String formattedDateTime = formatter.format(date); // 格式化日期時間
				%>
				<div class="info-item">
					<span>註冊日期</span> <span><%=formattedDateTime%></span>
				</div>


				<input type="hidden" name="action" value="Get_One_Update">
				<button type="submit" class="navigation-button">修改資料</button>
			</div>
		</form>
	</div>



	<div
		style="float: left; width: 10px; margin-top: 30px; margin-left: 120px;">
		<form method="post" enctype="multipart/form-data"
			action="${pageContext.request.contextPath}/mem" class="fo3">
			<div style="margin-top: 5%; margin-left: 20px">
				<div class="preview"
					style="background: white; height: 202px; width: 202px; text-align: center; z-index: 1; border: solid black; margin-right: 200px;">



					<img src="data:image/jpg;base64,${base64Img}" alt="" style="width: 198px; height: 198px; border-radius: 50%;">



					<p style="line-height: 180px;">未選擇任何檔案</p>
				</div>

				<div style="margin-top: 20px;">
					<input type="button" value="選擇圖片" id="file_name_display">
					<input type="hidden" name="action" value="Update_Img"> <input
						type="file" id="image_uploads" name="images"
						style="display: none;">
				</div>
				<div style="margin-top: 20px; margin-left: 68px;">
					<button type="submit" id="btn_submit">送出資料</button>
				</div>
			</div>
		</form>
	</div>









	<script type="text/javascript">
		 var input = document.getElementById('image_uploads');
		 var preview = document.querySelector('.preview');
		 input.addEventListener('change', updateImageDisplay); function updateImageDisplay() {
			 while (preview.firstChild) {
				 preview.removeChild(preview.firstChild);
			 }

			 if (input.files.length === 0) {
				 var para = document.createElement('p');
				 para.textContent = '未選擇任何檔案';
				 para.style = "line-height: 300px;";
				 preview.appendChild(para);
			 }
			 else {
        var para = document.createElement('p');
            var image = document.createElement('img');
            image.src = window.URL.createObjectURL(input.files[0]);
            image.style.width = "198px"; // 修改圖片寬度
            image.style.height = "198px"; // 修改圖片高度
            image.style.borderRadius = "50%"; // 添加圓形邊框
            

            preview.appendChild(image);
            preview.appendChild(para);
			 }
		 }

		 $(document).ready(function () {
			 // jquery for toggle sub menus
			 $('.sub-btn').click(function () {
				 $(this).next('.sub-menu').slideToggle();
				 $(this).find('.dropdown').toggleClass('rotate');
			 });
		 });
		 
		 window.onload = (event) => {
             main();
         }
         
         function main() {
             const inputButton = document.querySelector("input[type='button']")
             const inputFile = document.querySelector("input[type='file']")
             
             inputButton.onclick = (event) => {
                 inputFile.click();
             }
             
             inputFile.onchange = (event) => {
                 const files = event.target.files;
                 
                 console.log(files);
             }
         }
 	</script>

</body>
</html>
