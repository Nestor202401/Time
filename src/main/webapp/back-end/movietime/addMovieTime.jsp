<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

<div class="edit-form">
  <form METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/movietime/movietime.do" name="form1">
    <div class="row" >
      <div class="col-sm-6" >
        <label for="movieId">Movie ID:
        </label>
        <input type="text" id="movieId" name="movieId" value="<%= movieVO.getMovieId()%>" readonly>
      </div>
     
     <jsp:useBean id="cinemaSvc" scope="page" class="com.cinema.model.CinemaService" />
      
      <div class="col-sm-6">
        <label for="cinemaId">Cinema ID:</label>
        <select name="cinemaId" id="cinemaId">
            <c:forEach var="cinema" items="${cinemaSvc.uniqueCinemas}">
                <option value="${cinema.cinemaId}">${cinema.cinemaName}</option>
            </c:forEach>
        </select>
        
      </div>
      
      
      
      
    </div>

    <div class="row">
      <div class="col-sm-6">
        <label for="moviePlaybackType">Movie Playback Type:</label>
        <select id="moviePlaybackType" name="moviePlaybackType" class="form-control">
          <option value="" >請選擇</option>
          <option value="1" >數位</option>
          <option value="2" >3D</option>
        </select>
      </div>
      
      <div class="col-sm-6">
        <label for="price">Price:
          
        </label>
        <input type="TEXT" id="price" name="price" value="" size="45"/>
      </div>
    </div>

    <div class="row align-items-stretch">
      <div class="col-sm-6">
        <label for="showTimeDate">Show Time Date:
        </label>
        <input type="text" id="datepicker" name="showTimeDate" value="" size="45" >
      </div>
      <div class="col-sm-6">
        <label for="showTime">Show Time:
        </label>
        <select id="showTime" name="showTime" class="form-control">
          <option value="" >請選擇</option>
          <option value="0" >06:00</option>
          <option value="1" >12:00</option>
          <option value="2" >18:00</option>
        </select>
      </div>
    </div>
    
    <div class="row">
      <div class="col-sm-6">
      </div>
      
    </div>
    
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
 });

 // 根據播放類型自動設置價錢
 $('#moviePlaybackType').change(function() {
   var playbackType = $(this).val();
   var priceField = $('#price');
   
   if (playbackType == '1') {
     priceField.val(0);
   } else if (playbackType == '2') {
     priceField.val(20);
   } else {
     priceField.val(''); // 清空價錢
   }
 });

</script>
</body>
</html>
