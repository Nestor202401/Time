<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>會員登入</title>
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css'
	rel='stylesheet'>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: "Poppins", sans-serif;
}

body {
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	background: linear-gradient(to bottom, #87619d, #1c1c1c);
	height: 380px;
}

.wrapper {
	width: 420px;
	background: #fff;
	color: black;
	border-radius: 13px;
	padding: 30px 40px
}

.wrapper h1 {
	font-size: 36px;
	text-align: center;
}

.wrapper .input-box {
	position: relative;
	width: 100%;
	height: 50px;
	margin: 30px 0;
}

.input-box input {
	width: 100%;
	height: 100%;
	background: transparent;
	border: none;
	outline: none;
	border: 2px solid rgba(188, 180, 180, 0.2);
	border-radius: 40px;
}

.input-box input::placeholder {
	color: rgb(119, 125, 126);
}

.input-box i {
	position: absolute;
	right: 20px;
	top: 30%;
	transform: translate(-50%);
	font-size: 20px;
}

.wrapper .forgot{
display: flex;
justify-content: space-between;
font-size: 20.5px;
margin: 35px 0 15px;
}
.wrapper .forgot a {
	color: rgb(0, 119, 255);;
	text-decoration: none;
	font-size: 15px;
}

.wrapper .forgot a:hover {
	color: rgb(0, 119, 255);;
	text-decoration: underline;
	font-size: 15px;
}

.wrapper .btn {
	width: 100%;
	height: 45px;
	background: rgb(168, 175, 176);;
	border: none;
	border-radius: 40px;
	box-shadow: 0 0 10px rgba(0, 0, 0, .1);
	cursor: pointer;
	font-size: 16px;
	color: #333;
	font-weight: 600;
}

.wrapper .register-link {
	font-size: 16px;
	text-align: center;
	margin-top: 30px;
	color: rgb(119, 125, 126);
}

.wrapper p a {
	color: rgb(0, 119, 255);
	text-decoration: none;
}

.wrapper p a:hover {
	text-decoration: underline;
}

.wrapper .btn:hover {
	background: rgba(150, 150, 150, 0.9); /* 深灰色的透明度調整為 0.9 */
	color: #333; /* 字體顏色 */
}
</style>
</head>

<body>
	<div class="wrapper">
	
		<form method="post"  action="<%=request.getContextPath()%>/mem">
			<h1>Login</h1>
			<div class="input-box">
				<input type="text" name="account" placeholder="Useraccount">
				<i class='bx bxs-user'></i>
				<c:if test="${not empty erros}">
					<div style="color: red">${erros.emptyAccount}</div>
				</c:if>
			</div>

			<div class="input-box">
				<input type="password" name="paswd" placeholder="Password">
				<i class='bx bxs-lock'></i>
				<c:if test="${not empty erros}">
					<div style="color: red">${erros.emptyPaswd}</div>
				</c:if>
				<c:if test="${not empty erros.wrongMem}">
							<div style="color: red">${erros.wrongMem}</div>
						</c:if>
			</div>

			<div class="forgot">
				<a href="${pageContext.request.contextPath}/front-end/member/memberForget.jsp">忘記密碼?</a>
			</div>
			<button type="submit" class="btn">Login</button>
			<p style="color: red; text-align: center; margin-top: 10px">${verifyMes}</p>		
			<p style="color: red; text-align: center; margin-top: 10px">${verifyFalse}</p>		
			<p style="color: red; text-align: center; margin-top: 10px">${verifySuccess}</p>
			<p style="color: red; text-align: center; margin-top: 10px">${noLogIn}</p>		
			<input type="hidden" name="action" value="Log_In">
			<div class="register-link">
				<p>
					還沒有帳號?<a href="${pageContext.request.contextPath}/front-end/member/memberRegister.jsp">註冊</a>
				</p>
			</div>
		</form>
	</div>

</body>

</html>