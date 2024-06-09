<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
     <%@ page import="com.member.model.*"%>
<% 
    MemberVO member = (MemberVO) session.getAttribute("memberVO");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>會員資料修改</title>

<style>

  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
    margin-top: 5px;
  }
  h4 {
    color: blue;
    display: inline;
  }
  form {
    border: 6px solid #ddd; 
    border-radius: 10px; 
    padding: 20px; 
    max-width: 600px; 
    margin: 0 auto;
    box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.5);
    position: relative;
}


table {
    width: 450px;
    background-color: white;
    margin: auto; 
    margin-top: 10px; 
    margin-bottom: 10px;
    padding-top: 0px; 
} 

table td {
    margin-bottom: 30px;
    padding-top: 20px; 
    padding-bottom: 7px; 
}

tr {
    vertical-align: middle; 
    padding: 50px 0; 
}

h3 {
    position: absolute; 
    top: 0;
    left: 0;
    margin: 20px; 
    color: hsl(16, 88%, 64%); 
    font-size: 24px;
}
h4 a {
    margin-left: -60px;
    text-decoration: none; 
}

h4 a:hover {
    text-decoration: underline;
}
input[type="submit"] {
    margin-left: 240px; 
    background-color: #ffecb3;
    color: #333; 
    font-size: 20px; 
    padding: 10px 20px;
    border: none; 
    border-radius: 5px; 
    cursor: pointer; 
}
input[type="submit"]:hover {
    background-color: #ffcc80; 
}
input[type="text"] {
    border-radius: 5px;
    border: 2px solid #ccc;
    padding: 3px  3px;
}

</style>

</head>
<body bgcolor='white'>

<FORM METHOD="post" ACTION="mem" name="form1">
  <table id="table-1">
    <tr><td>
       <h3>資料修改</h3>
       <h4><a href="front-end/member/memberProfile.jsp">返回會員頁面</a></h4>
    </td></tr>
  </table>
  
<table>
	<tr>
		<td>會員編號:<font color=red><b>*</b></font></td>
		<td><%=member.getMemberId()%></td>
	</tr>
	<tr>
		<td>會員姓名:</td>
		<td><input type="TEXT" name="mename" value="<%=member.getMemberName()%>" size="45"/>
				<c:if test="${not empty erros}">

						<c:if test="${not empty erros.emptyMename}">
							<div style="color: red">${erros.emptyMename}</div>
						</c:if>
						<c:if test="${not empty erros.invalidMename}">
							<div style="color: red">${erros.invalidMename}</div>
						</c:if>

					</c:if>
		</td>
	</tr>
	<tr>
		<td>會員帳號:</td>
    <td><input type="TEXT" name="account"  value="<%=member.getMemberAccount()%>" size="45"/>
    <c:if test="${not empty erros}">

						<div style="color: red">${erros.emptyAccount}</div>

					</c:if></td>
	</tr>
	<tr>
		<td>會員信箱:</td>
		<td><input type="TEXT" name="email"  value="<%=member.getMemberEmail()%>" size="45"/>
		<c:if test="${not empty erros}">
					<c:if test="${not empty erros.emptyPhone}">
						<div style="color: red">${erros.emptyEmail}</div>
						</c:if>
					</c:if>
		</td>
	</tr>
	<tr>
		<td>會員電話:</td>
		<td><input type="TEXT" name="phone"   value="<%=member.getMemberPhone()%>" size="45"/>
		<c:if test="${not empty erros}">
						<c:if test="${not empty erros.emptyPhone}">
							<div style="color: red">${erros.emptyPhone}</div>
						</c:if>
						
						<c:if test="${not empty erros.invalidPhone}">
							<div style="color: red">${erros.invalidPhone}</div>
						</c:if>
					</c:if></td>
	</tr>
	<tr>
		<td>會員密碼:</td>
		<td><input type="TEXT" name="paswd"  value="<%=member.getMemberPassword()%>" size="45"/>
			<c:if test="${not empty erros}">

						<div style="color: red">${erros.emptyPaswd}</div>

					</c:if></td>
	</tr>
  <tr>
		<td>確認更改密碼:</td>
		<td><input type="password" name="paswdc"  value="" size="45"/>
			<c:if test="${not empty erros}">

						<c:if test="${not empty erros.emptyPaswdc}">
							<div style="color: red">${erros.emptyPaswdc}</div>
						</c:if>
						<c:if test="${not empty erros.wrongPaswdc}">
							<div style="color: red">${erros.wrongPaswdc}</div>
						</c:if>

					</c:if></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="Mem_Update">
<input type="hidden" name="memberId" value="<%=member.getMemberId()%>">
<input type="hidden" name="memDate" value="<%=member.getMemberRegisterDatetime()%>">
<input type="submit" value="送出修改">
</FORM>
</body>
