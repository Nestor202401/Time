<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.movie.model.*"%>

<% //見com.emp.controller.EmpServlet.java第238行存入req的empVO物件 (此為輸入格式有錯誤時的empVO物件)
   MovieVO movieVO = (MovieVO) request.getAttribute("movieVO");
%>

<!DOCTYPE html>
<html lang="zh-TW">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>新增電影</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">

<style>
    body {
        font-family: Arial, sans-serif;
    }
    .edit-form {
        max-width: 600px;
        margin: 0 auto;
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 5px;
        background-color: #f9f9f9;
    }
    .edit-form label {
        display: block;
        margin-bottom: 5px;
    }
    .edit-form input[type="text"], .edit-form textarea {
        width: 100%;
        padding: 8px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }
    .edit-form textarea {
        width: 100%;
        height: 100px;
        padding: 8px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
        resize: vertical; /* 只能垂直调整大小 */
    }
    .edit-form button {
        background-color: #4CAF50;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }
    .edit-form button:hover {
        background-color: #45a049;
    }
</style>
</head>
<body>


<%-- <c:if test="${not empty errorMsg.movieName}"> --%>
<%--     <span style="color:red">${errorMsg.movieName}</span> --%>
<%-- </c:if> --%>




<div class="edit-form">
  <form METHOD="post" ACTION="movie.do" name="form1">
    
    
    <div class="row" >
      <div class="col-sm-6" >
        <label for="movieName">電影名稱:
        	<c:if test="${not empty errorMsgs.movieName}">
    		<span style="color:red; font-size:12px;">*${errorMsgs.movieName}*</span>
			</c:if>
		</label>
        <input type="TEXT" name="movieName" value="<%= (movieVO==null)? "" : movieVO.getMovieName()%>" size="45"/>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-6">
        <label for="director">導演:
            <c:if test="${not empty errorMsgs.director}">
    		<span style="color:red; font-size:12px;">*${errorMsgs.director}*</span>
			</c:if>
        </label>
        <input type="TEXT" name="director" value="<%= (movieVO==null)? "" : movieVO.getDirector()%>" size="45"/>
      </div>
      
      <div class="col-sm-6">
        <label for="actor">演员:
            <c:if test="${not empty errorMsgs.actor}">
    		<span style="color:red; font-size:12px;">*${errorMsgs.actor}*</span>
			</c:if>
        </label>
        <input type="TEXT" name="actor" value="<%= (movieVO==null)? "" : movieVO.getActor()%>" size="45"/>
      </div>
    </div>

    <div class="row align-items-stretch">
      <div class="col-sm-6">
        <label for="runtime">片長:
        	<c:if test="${not empty errorMsgs.runtime}">
    		<span style="color:red; font-size:12px;">*${errorMsgs.runtime}*</span>
			</c:if>
        </label>
        <input type="TEXT" name="runtime"   value="<%= (movieVO==null)? "" : movieVO.getRuntime()%>" size="45"/>
      </div>
      <div class="col-sm-6">
        <label for="rating">電影等級:
        	<c:if test="${not empty errorMsgs.movieRating}">
    		<span style="color:red; font-size:12px;">*${errorMsgs.movieRating}*</span>
			</c:if>
        </label>
        <select id="rating" name="movieRating" class="form-control">
          <option value="" <% if (movieVO==null ) { %> selected <% } %>>請選擇</option>
            <option value="1" <% if ((movieVO!=null) && (movieVO.getMovieRating().equals(1))) { %> selected <% } %>>普遍級</option>
            <option value="2" <% if ((movieVO!=null) && movieVO.getMovieRating().equals(2)) { %> selected <% } %>>保護級</option>
            <option value="3" <% if ((movieVO!=null) && movieVO.getMovieRating().equals(3)) { %> selected <% } %>>輔導級</option>
            <option value="4" <% if ((movieVO!=null) && movieVO.getMovieRating().equals(4)) { %> selected <% } %>>限制級</option>
        </select>
      </div>
    </div>
    
    <div class="row">
      <div class="col-sm-6">
        <label for="releaseDate">上映時間:
        	<c:if test="${not empty errorMsgs.releaseDate}">
    		<span style="color:red; font-size:12px;">*${errorMsgs.releaseDate}*</span>
			</c:if>
        </label>
        <input type="text" id="datepicker" name="releaseDate" value="<%= (movieVO==null)? "" : movieVO.getReleaseDate()%>" size="45" >
      </div>
      
      <div class="col-sm-6">
        <label for="endDate">下映時間:
        	<c:if test="${not empty errorMsgs.endDate}">
    		<span style="color:red; font-size:12px;">*${errorMsgs.endDate}*</span>
			</c:if>
        </label>
        <input type="text" id="datepicker2" name="endDate" value="<%= (movieVO==null)? "" : movieVO.getEndDate()%>" size="45">
      </div>
    </div>
    
    <label for="introduction">簡介:
    	<c:if test="${not empty errorMsgs.introduction}">
    	<span style="color:red; font-size:12px;">*${errorMsgs.introduction}*</span>
		</c:if>
    </label>
    <textarea id="introduction" name="introduction" rows="5" cols="45"><%= (movieVO==null)? "" : movieVO.getIntroduction()%></textarea>
    
    <div style="text-align: right;margin-right: 20px;">
      <input type="hidden" name="action" value="insert">
      <button type="submit">新增</button>
    </div>
    
  </form>
</div>

<script src="//apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<script>
 // 初始化日期選擇器
 $( "#datepicker" ).datepicker({
   showAnim: "slideDown", // 設置動畫為滑下
   dateFormat: "yy-mm-dd", // 設置日期格式為年-月-日
   onSelect: function(selectedDate) {
     // 在選擇開始日期後，更新結束日期選擇器的最小日期
     $( "#datepicker2" ).datepicker( "option", "minDate", selectedDate );
   }
 });
 
 $( "#datepicker2" ).datepicker({
   showAnim: "slideDown", // 設置動畫為滑下
   dateFormat: "yy-mm-dd", // 設置日期格式為年-月-日
   onSelect: function(selectedDate) {
     // 在選擇結束日期後，更新開始日期選擇器的最大日期
     $( "#datepicker" ).datepicker( "option", "maxDate", selectedDate );
   }
 });
 
 // 清除開始日期時，重置結束日期選擇器的最小日期
 $("#datepicker").on("input", function() {
   if ($(this).val() === "") {
     $( "#datepicker2" ).datepicker( "option", "minDate", null );
   }
 });
 
 // 清除結束日期時，重置開始日期選擇器的最大日期
 $("#datepicker2").on("input", function() {
   if ($(this).val() === "") {
     $( "#datepicker" ).datepicker( "option", "maxDate", null );
   }
 });

 
 
 </script>
</body>
</html>