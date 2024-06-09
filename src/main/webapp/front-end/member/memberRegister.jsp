<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>

<%
MemberVO member = (MemberVO) request.getAttribute("member");
%>



<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>會員註冊測試</title>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: 'Roboto', sans-serif;
}

body {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	background: linear-gradient(to bottom, #87619d, #4c1e2b, #1c1c1c);
	padding: 10px;
}

.container {
	max-width: 700px;
	width: 100%;
	background: #fff;
	padding: 25px 30px;
	border-radius: 5px;
}

.container .title {
	font-size: 25px;
	font-weight: 500;
	position: relative;
}

.container .title::before {
	content: '';
	position: absolute;
	left: 0;
	bottom: 0;
	height: 3px;
	width: 100px;
	background: linear-gradient(135deg, #71b7e6, #9b59b6)
}

.container form .user-details {
	display: flex;
	flex-wrap: wrap;
	justify-content: space-around
}

form .user-details .input-box {
	margin: 30px 0 20px 0;
	width: calc(50% - 30px);
}

.user-details .input-box .details {
	width: 100%;
	font-weight: 500;
	margin-bottom: 5px;
}

.user-details .input-box input {
	height: 45px;
	width: 100%;
	outline: none;
	border-radius: 5px;
	border: 1px solid #ccc;
	padding-left: 15px;
	font-size: 16px;
}

form .button {
	height: 45px;
	margin: 45px 0;
}

form .button input {
	height: 100%;
	width: 100%;
	outline: none;
	color: #fff;
	font-size: 18px;
	font-weight: 500;
	border: none;
	background: linear-gradient(135deg, #71b7e6, #9b59b6);
}

form .button input:hover {
	background: linear-gradient(135deg, #5a8fbb, #7e4585);
}
.home a {
  
      color: #5a8fbb;
      font-size: 15px;
      font-weight: bold;
      text-decoration: none;
    }
    .home{
      margin-top: -10px;
      margin-left: 560px;
    }
    
    .home a:hover {
      text-decoration: underline;
    }
</style>
</head>
<body>
	<div class="container">
		<div class="title">會員註冊</div>
<div class="home"><a href="${pageContext.request.contextPath}/front-end/member/memberLogin.jsp">回登入畫面</a></div>

		<form method="post" action="<%=request.getContextPath()%>/mem">
			<div class="user-details">
				<div class="input-box">
					<span class="details">會員名稱</span> <input type="text" name="mename"
						value="<%=(member == null) ? "" : member.getMemberName()%>"
						placeholder="請輸入會員名稱">
					<c:if test="${not empty erros}">

						<c:if test="${not empty erros.emptyMename}">
							<div style="color: red">${erros.emptyMename}</div>
						</c:if>
						<c:if test="${not empty erros.invalidMename}">
							<div style="color: red">${erros.invalidMename}</div>
						</c:if>

					</c:if>
				</div>

				<div class="input-box">
					<span class="details">會員信箱</span> <input type="text" name="email"
						value="<%=(member == null) ? "" : member.getMemberEmail()%>"
						placeholder="請輸入會員信箱">

					<c:if test="${not empty erros}">
					<c:if test="${not empty erros.emptyPhone}">
						<div style="color: red">${erros.emptyEmail}</div>
						</c:if>
						<c:if test="${not empty erros.ukEmail}">
						<div style="color: red">${erros.ukEmail}</div>
						</c:if>
					</c:if>
					
				</div>

				<div class="input-box">
					<span class="details">會員電話</span> <input type="text" name="phone"
						value="<%=(member == null) ? "" : member.getMemberPhone()%>"
						placeholder="請輸入會員電話">
					<c:if test="${not empty erros}">
						<c:if test="${not empty erros.emptyPhone}">
							<div style="color: red">${erros.emptyPhone}</div>
						</c:if>
						
						<c:if test="${not empty erros.invalidPhone}">
							<div style="color: red">${erros.invalidPhone}</div>
						</c:if>
					</c:if>
				</div>

				<div class="input-box">
					<span class="details">會員帳號</span> <input type="text" name="account"
						value="<%=(member == null) ? "" : member.getMemberAccount()%>"
						placeholder="請輸入會員帳號">
					<c:if test="${not empty erros}">

						<div style="color: red">${erros.emptyAccount}</div>

					</c:if>
				</div>

				<div class="input-box">
					<span class="details">會員密碼</span> <input type="password"
						name="paswd" placeholder="請輸入會員密碼">
					<c:if test="${not empty erros}">

						<div style="color: red">${erros.emptyPaswd}</div>

					</c:if>
				</div>

				<div class="input-box">
					<span class="details">確認會員密碼</span> <input type="password"
						name="paswdc" placeholder="請確認會員密碼" >
					<c:if test="${not empty erros}">

						<c:if test="${not empty erros.emptyPaswdc}">
							<div style="color: red">${erros.emptyPaswdc}</div>
						</c:if>
						<c:if test="${not empty erros.wrongPaswdc}">
							<div style="color: red">${erros.wrongPaswdc}</div>
						</c:if>

					</c:if>
				</div>
			</div>

			<div class="button">
				<input type="hidden" name="action" value="From_Register"> <input
					type="submit" value="註冊">
			</div>
		</form>

	</div>
</body>
</html>