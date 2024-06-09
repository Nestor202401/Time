<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>個人周邊商品訂單</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
  <link href="https://fontawesome.com/icons/right-to-bracket?f=classic&s=solid" rel="stylesheet">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script>
  <style >
  * {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Poppins", sans-serif;
}

body {
  background: white;
  color: black;
  background-size: cover;
  background-position: center;
}

.side-bar {
  background: linear-gradient(to bottom, #87619d, #1c1c1c);
  backdrop-filter: blur(15px);
  width: 250px;
  height: 100vh;
  position: fixed; /* 修正為固定位置 */
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

.main-content {
  margin-left: 250px;
  padding: 40px;
}
.main-content th{
  padding-bottom: 15px;
  padding-top: 14px;
}
.main-content td{
  padding-bottom: 20px;
  padding-top: 18px;
}

table {
  width: 800px;
  background-color: white;
  margin-bottom: 5px;
  border-collapse: collapse;
  table-layout: auto;
}
#tr_1 th{
  background-color: hwb(0 38% 60%);
  color: #fff;
  border-left: 1.7px solid rgba(0, 0, 0, 0.5); /* 设置列边框 */
  border-right: 1.6px solid rgba(0, 0, 0, 0.5); /* 设置列边框 */
  border-bottom: 1.6px solid rgba(0, 0, 0, 0.3);
}
#tr_2 th{
  background-color: hwb(50 68% 0%);
}
table td {
  border-left: 1.4px solid rgba(0, 0, 0, 0.4); /* 设置列边框 */
  border-right: 1.6px solid rgba(0, 0, 0, 0.4); /* 设置列边框 */
  border-bottom: 1.6px solid rgba(0, 0, 0, 0.4);
  text-align: center;
}

.popupContainer {
  display: none;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0,0,0);
  background-color: rgba(0,0,0,0.4);
  padding-left: 90px;
}

.closeButton {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.closeButton:hover,
.closeButton:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
.popupContent {
  background-color: #fefefe;
  margin: 4% auto;
  padding: 20px;
  width: 80%;
}

.popupContent table {
  margin-left: auto; /* 將表格置於右側 */
  margin-right: auto; /* 將表格置於左側 */
  width:90%; 
  margin-top: 30px;
  
}

.popupContent h2 {
  border-bottom: 2px solid rgba(0, 0, 0, 0.2); /* 添加透明分隔线 */
  padding-bottom: 10px; /* 可以调整下方间距 */
}
.popupContent th,
.popupContent td {
  border: 0; /* 清除单元格边框 */
  border-left: 2px solid rgba(0, 0, 0, 0.2); /* 设置列边框 */
  border-right: 2px solid rgba(0, 0, 0, 0.2); /* 设置列边框 */
  border-top: 2px solid rgba(0, 0, 0, 0.2);
  border-bottom: 2px solid rgba(0, 0, 0, 0.2);
}
.popupContent th {
  padding-bottom: 20px; /* 增加下方空间 */
  padding-top: 19px;
  /* border: none; */
}
.popupContent td {
  padding-bottom: 60px; /* 增加下方空间 */
  padding-top: 50px;

  /* border: none; */
}


  
  </style>
</head>

<body>
  <div class="side-bar">
    <div class="menu">
       <div class="item"><a href="#"><i class="fas fa-home"></i>回首頁</a></div>
      <div class="item"><a href="${pageContext.request.contextPath}/front-end/member/memberProfile.jsp"><i class="fas fa-user"></i>會員中心</a></div>
     <div class="item"><a href="${pageContext.request.contextPath}/mem?action=comment"><i class="fas fa-comments"></i>討論區</a></div>
      <div class="item">
        <a class="sub-btn"><i class="fas fa-cart-arrow-down"></i>查看個人訂單<i class="fas fa-angle-right dropdown"></i></a>
        <div class="sub-menu">
          <a href="${pageContext.request.contextPath}/mem?action=ticket" class="sub-item">電影票查詢</a>
          <a href="#" class="sub-item">個人周邊商品</a>
        </div>
      </div>
    </div>
  </div>

  <div class="main-content">
    <table>
      <thead>
        <tr id="tr_1">
          <th>訂單編號</th>
          <th>訂購時間</th>
          <th>訂單金額</th>
          <th>狀態</th>
          <th>明細</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="order" items="${orders}">
          <tr>
            <td><c:out value="${order.prodOrdId}"/></td>
            <td><c:out value="${order.estTime}"/></td>
            <td>$<c:out value="${order.total}"/></td>
            <td>
              <span>
                <c:choose>
                  <c:when test="${order.ordStatus == 0}">未完成</c:when>
                  <c:when test="${order.ordStatus == 1}">已完成</c:when>
                </c:choose>
              </span>
            </td>
            <td>
              <button class="popupButton" data-order-id="${order.prodOrdId}">明細</button>
              <div class="popupContainer" id="popup_${order.prodOrdId}" style="display: none;">
                <div class="popupContent">
                  <span class="closeButton">&times;</span>
                  <h2>產品明細</h2>
                  <table>
                    <thead>
                      <tr id="tr_2">
                        <th>產品名稱</th>
                        <th>商品照片</th>
                        <th>數量</th>
                        <th>單價</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach var="detail" items="${order.prodDetails}">
                        <tr>
                          <td><c:out value="${detail.prodVO.prodName}"/></td>
                          <td><c:out value="${detail.prodDetailId}"/></td>
                          <td><c:out value="${detail.prodCount}"/></td>
                          <td>$<c:out value="${detail.unitPrice}"/></td>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>

  <script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function () {
      document.querySelectorAll('.popupButton').forEach(function (button) {
        button.addEventListener('click', function () {
          var orderId = this.getAttribute('data-order-id');
          document.getElementById('popup_' + orderId).style.display = 'block';
        });
      });

      document.querySelectorAll('.closeButton').forEach(function (button) {
        button.addEventListener('click', function () {
          this.closest('.popupContainer').style.display = 'none';
        });
      });

      // 側邊欄
      $('.sub-btn').click(function () {
        $(this).next('.sub-menu').slideToggle();
        $(this).find('.dropdown').toggleClass('rotate');
      });
    });
  </script>
</body>

</html>
