<!-- 當前會員登入時,這行程式碼註解掉 line103 -->
<% session.setAttribute("memberId", 3); %> 
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.article.model.*"%>

<%-- <% --%>
//  MemberVO member = (MemberVO) session.getAttribute("memberVO");
//  if (member == null) { 
//      out.println("會員不存在!");
//  }else{
// 	 Integer memberId=member.getMemberId();
//  }
<%-- %> --%>

<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add New Article</title>
<style>
* {
  box-sizing: border-box;
}

body {
  font-family: Arial, sans-serif;
   background-color: black; 
  margin: 0;
  padding: 0;
}

.container {
  max-width: 600px;
  margin: 50px auto;
  background: #fff;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  color: #333;
}

form {
  margin-top: 30px;
}

label {
  display: block;
  margin-bottom: 5px;
  color: #000; /* Black text color for labels */
}

input[type="text"],
textarea,
select {
  width: 100%; /* Set width to 100% for both input fields */
  padding: 10px;
  margin-bottom: 20px;
  border: 2px solid #a9a9a9; /* Iron gray border color for input fields */
  border-radius: 5px;
  background-color: #f7f7f7; /* Light gray background for input fields */
  color: #000; /* Black text color for input fields */
}

textarea {
  resize: vertical;
}

.input-group {
  display: flex;
}

.input-group > div {
  flex: 1;
  margin-right: 10px; /* Spacing between inputs */
}

.input-group > div:last-child {
  margin-right: 0; /* Remove margin for the last input */
}

input[type="submit"] {
  background: #000; /* Black background for submit button */
  color: #fff; /* White text color for submit button */
  border: none;
  padding: 12px 20px;
  cursor: pointer;
  border-radius: 5px;
  transition: background 0.3s ease;
}

input[type="submit"]:hover {
  background: #333; /* Darker shade of black for hover effect */
}

</style>
</head>
<body>

<div class="container">

	<div style="vertical-align: middle; text-align: right;">	
		<button type="button" class="home-button" onclick="window.location.href='<%=request.getContextPath()%>/front-end/article/home.jsp'">回到首頁</button>
	</div>
	
  <h2>新 增 文 章</h2>
  <form action="article.do" method="post">

    <div class="input-group">
      <div>
        <label for="memberId">會員:</label>
        <input type="text" id="memberId" name="memberId" value="${memberId}"  readonly="readonly"> <!-- !!!! 記得改 --> 
      </div>
      <div>
        <label for="typeId">電影類型:</label>
        <select id="typeId" name="typeId" required>
          <option value="">請選擇</option>
          <option value="1">劇情片</option>
          <option value="2">動作片</option>
          <option value="3">科幻片</option>
          <option value="4">喜劇片</option>
          <option value="5">愛情片</option>
          <option value="6">戰爭片</option>
          <option value="7">恐怖片</option>
          <option value="8">動畫片</option>
          <option value="9">紀錄片</option>
          <!-- Add more options as needed -->
        </select>
      </div>
    </div>
    
    <label for="theme">主題:</label>
    <input type="text" id="theme" name="theme" required>
    
    <label for="articleContent">文章內容:</label>
    <textarea id="articleContent" name="articleContent" rows="5" required></textarea>
    
 	<input type="hidden" name="action" value="insert">
    <input type="submit" value="發佈">
  </form>
</div>
</body>
</html>
