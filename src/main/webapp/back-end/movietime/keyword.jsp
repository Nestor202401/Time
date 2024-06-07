<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.movietime.*" %>
<%@ page import="com.movie.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>電影查詢與編輯</title>
<!-- 引入必要的 CSS 檔案 -->
<link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/fixedcolumns/5.0.0/css/fixedColumns.dataTables.min.css">
<!-- 自定義 CSS -->
<link rel="stylesheet" href="../movie/style.css">
<style>
  body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f9;
      margin: 0;
      padding: 20px;
      color: #333;
  }
  .container {
      text-align: center;
      margin-bottom: 20px;
  }
  .title {
      font-size: 2em;
      color: #68a16a;
      margin-bottom: 20px;
  }
  .subtitle {
      font-size: 1.5em;
      color: #4e7c4d;
      margin: 10px 0;
  }
  .search-container {
      display: flex;
      align-items: center;
      justify-content: center;
      margin-bottom: 20px;
  }
  .search-container div {
      margin: 10px;
      font-size: 1.25em;
      color: #333;
  }
  .search-container input, .search-container button {
      height: 30px;
      border: 1px solid #ddd;
      border-radius: 5px;
      padding: 5px;
      margin: 0 5px;
  }
  .search-container input {
      width: 150px;
  }
  .search-container button {
      background-color: #68a16a;
      color: white;
      cursor: pointer;
      transition: background-color 0.3s ease;
  }
  .search-container button:hover {
      background-color: #4e7c4d;
  }
  table {
      width: 100%;
      border-collapse: collapse;
      border: 1px solid #ddd;
      margin-top: 20px;
      background-color: #fff;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      border-radius: 8px;
      overflow: hidden;
  }
  th, td {
      padding: 10px;
      text-align: center;
      border-bottom: 1px solid #ddd;
  }
  th {
      background-color: #68a16a;
      color: white;
  }
  tr:nth-child(even) {
      background-color: #f9f9f9;
  }
  .fixed-column {
      position: sticky;
      left: 0;
      background-color: #f2f2f2;
  }
  .fixed-column th {
      position: sticky;
      left: 0;
      background-color: #f2f2f2;
      z-index: 1;
  }
  .main-column {
      max-width: 100px;
      max-height: 50px;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
  }
  img {
      max-width: auto;
      max-height: 100px;
      border-radius: 8px;
  }
  .table-container {
      min-width: auto;
      min-height: auto;
      overflow: auto;
  }
  .modal-content {
      background-color: #fefefe;
      margin: 5% auto;
      padding: 20px;
      border: 1px solid #888;
      width: 60%;
      max-width: 800px;
      box-shadow: 0 5px 15px rgba(0,0,0,0.3);
      border-radius: 10px;
  }
  .close {
      color: #aaa;
      float: right;
      font-size: 28px;
      font-weight: bold;
  }
  .close:hover, .close:focus {
      color: black;
      text-decoration: none;
      cursor: pointer;
  }
  .button {
      background-color: #68a16a;
      color: white;
      border: none;
      border-radius: 5px;
      padding: 5px 10px;
      cursor: pointer;
      transition: background-color 0.3s ease;
      font-size: 14px;
  }
  .button:hover {
      background-color: #4e7c4d;
  }
  .no-results {
      text-align: center;
      font-size: 1.25em;
      color: #333;
  }
</style>
</head>
<body>

<div class="container">
  <div class="title">電影場次查詢及修改：</div>
    <form id="dateForm" action="movietime.do" method="get">
        <input type="text" id="search" name="keyword" placeholder="電影名稱" value="">
        <input type="hidden" name="action" value="getKey_Word">
        <button type="submit" class="button">送出</button>
    </form>
</div>

<div id="addModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeAddModal()">&times;</span>
        <div class="subtitle">新增電影圖片</div>
        <iframe id="addModalIframe" src="" style="width: 100%; height: 500px; border: none;"></iframe>
    </div>
</div>

<div id="editModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeEditModal()">&times;</span>
        <div class="subtitle">編輯電影圖片</div>
        <iframe id="editModalIframe" src="" style="width: 100%; height: 500px; border: none;"></iframe>
    </div>
</div>

<div style="height: 20px;"></div>
<div class="table-container">
  <!-- 顯示 MovieVO 表格 -->
  <c:if test="${not empty result['movies']}">
    <table id="movieTable" class="display nowrap" style="width:100%">
      <thead>
        <tr>
            <th class="main-column">ID</th>
            <th class="main-column">電影名稱</th>
            <th class="main-column"></th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="movieVO" items="${result['movies']}">
        <tr>
          <td class="main-column">${movieVO.movieId}</td>
          <td class="main-column">${movieVO.movieName}</td>
          <td class="main-column"><button class="button" onclick="openAddModal(${movieVO.movieId})">新增</button></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:if>

  <!-- 顯示 MovieTimeVO 表格 -->
  <c:if test="${not empty result['movieTimes']}">
    <table id="movieTimeTable" class="display nowrap" style="width:100%">
      <thead>
        <tr>
            <th class="main-column">ID</th>
            <th class="main-column">電影名稱</th>
            <th class="main-column">影廳名稱</th>
            <th class="main-column">播放類型</th>
            <th class="main-column">價錢</th>
            <th class="main-column">日期</th>
            <th class="main-column">時間</th>
            <th class="main-column"></th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="movieTimeVO" items="${result['movieTimes']}">
        <tr>
          <td class="main-column">${movieTimeVO.movieVO.movieId}</td>
          <td class="main-column">${movieTimeVO.movieVO.movieName}</td>
          <td class="main-column">${movieTimeVO.cinemaVO.cinemaName}</td>
          <td class="main-column">${movieTimeVO.moviePlaybackType}</td>
          <td class="main-column">${movieTimeVO.price}</td>
          <td class="main-column">${movieTimeVO.showTime}</td>
          <td class="main-column">${movieTimeVO.showTimeDate}</td>
          <td class="main-column"><button class="button" onclick="openEditModal(${movieTimeVO.showTimesId})">修改</button></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:if>

  <c:if test="${empty result['movieTimes'] and empty result['movies']}">
    <p class="no-results">没有找到匹配的结果。</p>
  </c:if>
</div>

<!-- 引入必要的 JavaScript 檔案 -->
<script src="//apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/fixedcolumns/5.0.0/js/dataTables.fixedColumns.min.js"></script>
<script src="../movie/script.js"></script>
<script>
 $(document).ready(function() {
    // 初始化 DataTable
    var table1 = $('#movieTimeTable').DataTable({
        scrollY:        "800px",
        scrollX:        true,
        scrollCollapse: true,
        paging:         true,
        pageLength:     10, 
        lengthChange:   false,
        fixedColumns:   {
            leftColumns: 1,
            rightColumns: 0
        },
        searching: false,
        info: false,
        ordering: false,
        order: [[1, 'asc']]
    });
    
    var table2 = $('#movieTable').DataTable({
        scrollY:        "800px",
        scrollX:        true,
        scrollCollapse: true,
        paging:         true,
        pageLength:     5, 
        lengthChange:   false,
        fixedColumns:   {
            leftColumns: 1,
            rightColumns: 0
        },
        searching: false,
        info: false,
        ordering: false,
        order: [[1, 'asc']]
    });
});
 
 function openAddModal(movieId) {
     var modal = document.getElementById("addModal");
     var iframe = document.getElementById("addModalIframe");
     iframe.src = "<%=request.getContextPath()%>/back-end/movie/movie.do?action=getOne_For_add&movieId=" + movieId;
     modal.style.display = "block";
 }

 function closeAddModal() {
     var modal = document.getElementById("addModal");
     modal.style.display = "none";
 }
 
 function openEditModal(showTimesId) {
     var modal = document.getElementById("editModal");
     var iframe = document.getElementById("editModalIframe");
     iframe.src = "<%=request.getContextPath()%>/back-end/movietime/movietime.do?action=getOne_For_Update&showTimesId=" + showTimesId;
     modal.style.display = "block";
 }

 function closeEditModal() {
     var modal = document.getElementById("editModal");
     modal.style.display = "none";
 }
</script>

</body>
</html>
