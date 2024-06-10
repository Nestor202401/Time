<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>會員歷史文章</title>

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
  <link href="https://fontawesome.com/icons/right-to-bracket?f=classic&s=solid" rel='stylesheet'>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      font-family: "Poppins", sans-serif;
    }

    body {
      font-family: Arial, sans-serif;
      background-color: #333;
      color: #fff;
      margin: 0;
      padding: 0;
    }

    .container {
      width: 80%;
      margin: 0 auto;
      padding: 20px;
      background-color: #444;
      border-radius: 10px;
      margin-left: 230px;
    }

    .side-bar {
      background: linear-gradient(to bottom, #87619d, #1c1c1c);
      backdrop-filter: blur(15px);
      width: 220px;
      height: 100vh;
      position: fixed;
      top: 0;
      left: 0;
      overflow-y: auto;
      transition: 0.6s ease;
      transition-property: left;
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
      padding: 5px 25px;
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
/* -------------------------------------------------------------------------------------------------*/
    table {
      width: 100%;
      border-collapse: collapse;
    }

    thead {
      background-color: #990000;
    }

    thead th {
      padding: 10px;
      text-align: center;
      height: 40px;
      /* 固定表頭高度 */
      line-height: 20px;
      /* 表頭內部行高，讓文字居中 */
      overflow: hidden;
      /* 隱藏超出的內容 */
      white-space: nowrap;
      /* 禁止表頭內容換行 */
    }

    tbody tr {
      border-bottom: 3px solid #555;
    }

    tbody td {
      padding: 20px;
      text-align: center;
      max-width: 300px;
      word-wrap: break-word;
   
    }
    tbody tr:hover {
  background-color: #555; /* 设置鼠标悬停时的背景颜色 */
}
    
  </style>
</head>

<body>
  <div class="side-bar">
    <div class="menu">
      <div class="item"><a href="${pageContext.request.contextPath}"><i class="fas fa-home"></i>回首頁</a></div>
           <div class="item"><a href="${pageContext.request.contextPath}/front-end/member/memberProfile.jsp"><i class="fas fa-user"></i>會員中心</a></div>
      <div class="item"><a href="${pageContext.request.contextPath}/mem?action=comment"><i class="fas fa-comments"></i>討論區</a></div>
      <div class="item">
        <a class="sub-btn"><i class="fas fa-cart-arrow-down"></i>查看個人訂單<i class="fas fa-angle-right dropdown"></i></a>
        <div class="sub-menu">
          <a href="${pageContext.request.contextPath}/mem?action=ticket" class="sub-item">電影票查詢</a>
          <a href="${pageContext.request.contextPath}/mem?action=product" class="sub-item">個人周邊商品</a>
        </div>
      </div>
    </div>
  </div>

  <div class="container">
    <table>
      <thead>
        <tr>
          <th>文章編號</th>
          <th>文章名稱</th>
          <th>評論內容</th>
          <th>狀態</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="article" items="${articles}">
        <tr>
          <td><c:out value="${article.articleId}"/></td>
          <td><c:out value="${article.theme}"/></td>
          <td><c:out value="${article.articleContent}"/></td>
          <td>一般留言</td>
        </tr>
     </c:forEach>
      </tbody>
    </table>
  </div>

  <script type="text/javascript">
    $(document).ready(function() {
      // 側邊攔的下拉功能
      $('.sub-btn').click(function() {
        $(this).next('.sub-menu').slideToggle();
        $(this).find('.dropdown').toggleClass('rotate');
      });
    });
  </script>
</body>

</html>
