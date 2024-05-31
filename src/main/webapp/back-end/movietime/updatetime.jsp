<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.movietime.model.*"%>

<% //見com.emp.controller.EmpServlet.java第163行存入req的empVO物件 (此為從資料庫取出的empVO, 也可以是輸入格式有錯誤時的empVO物件)
   MovieTimeVO movieTimeVO = (MovieTimeVO) request.getAttribute("movieTimeVO");
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
  <form METHOD="post" ACTION="movietime.do" name="form1">
    <div class="row" >
     <div class="col-sm-6" >
        <label for="showTimesId">showTimesId:
        </label>
        <input type="TEXT" name="showTimesId" value="${movieTimeVO.showTimesId}" size="45"/>
      </div>
    
      <div class="col-sm-6" >
        <label for="movieId">Movie ID:
        </label>
        <input type="TEXT" name="movieId" value="${movieTimeVO.movieId}" size="45"/>
      </div>
      
      <div class="col-sm-6">
        <label for="cinemaId">Cinema ID:</label>
        <input type="TEXT" name="cinemaId" value="${movieTimeVO.cinemaId}" size="45"/>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-6">
        <label for="moviePlaybackType">Movie Playback Type:</label>
        <select id="moviePlaybackType" name="moviePlaybackType" class="form-control">
          <option value="" ${movieTimeVO == null ? "selected" : ""}>請選擇</option>
          <option value="0" ${movieTimeVO != null && movieTimeVO.getMoviePlaybackType() == 0 ? "selected" : ""}>數位</option>
		  <option value="1" ${movieTimeVO != null && movieTimeVO.getMoviePlaybackType() == 1 ? "selected" : ""}>3D</option>
        </select>
      </div>
      
      <div class="col-sm-6">
        <label for="price">Price:
          
        </label>
        <input type="TEXT" id="price" name="price" value="${movieTimeVO.price}" size="45"/>
      </div>
    </div>

    <div class="row align-items-stretch">
      <div class="col-sm-6">
        <label for="showTimeDate">Show Time Date:
        </label>
        <input type="text" id="datepicker" name="showTimeDate" value="${movieTimeVO.showTimeDate}" size="45" >
      </div>
      <div class="col-sm-6">
        <label for="showTime">Show Time:
        </label>
        <select id="showTime" name="showTime" class="form-control">
          <option value="" ${movieTimeVO == null ? "selected" : ""}>請選擇</option>
          <option value="0" ${movieTimeVO != null && movieTimeVO.getShowTime() == 0 ? "selected" : ""}>18:00</option>
		  <option value="1" ${movieTimeVO != null && movieTimeVO.getShowTime() == 1 ? "selected" : ""}>06:00</option>
		  <option value="2" ${movieTimeVO != null && movieTimeVO.getShowTime() == 2 ? "selected" : ""}>12:00</option>
        </select>
      </div>
    </div>
    
    <div class="row">
      <div class="col-sm-6">
      </div>
      
    </div>
    
    <div style="text-align: right;margin-right: 20px;">
      <input type="hidden" name="action" value="update">
      <button type="submit">送出</button>
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
