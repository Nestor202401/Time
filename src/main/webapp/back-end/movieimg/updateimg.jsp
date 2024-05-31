<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.movieimg.model.*"%>

<% // 見 com.emp.controller.EmpServlet.java 第 238 行存入 req 的 empVO 物件 (此為輸入格式有錯誤時的 empVO 物件)
   MovieImgVO movieImgVO = (MovieImgVO) request.getAttribute("movieImgVO");
%>

<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>修改電影圖片</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .form-row {
            display: flex;
            flex-wrap: wrap;
            margin-bottom: 15px;
        }

        .form-group {
            flex: 1;
            min-width: 45%; /* 两个表单项并排显示 */
            margin-bottom: 15px;
            padding: 0 10px;
        }

        .form-group.label-only {
            flex: 1;
            min-width: 100%;
        }

        .form-group img {
            max-height: 100px; /* 设置最大高度为 100px */
            width: auto; /* 宽度自动调整 */
            display: block;
            margin-top: 10px;
        }

        .form-group-full {
            flex: 1;
            min-width: 100%; /* 单行显示 */
            margin-bottom: 15px;
            padding: 0 10px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
        }

        .form-group input[type="text"],
        .form-group input[type="file"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
            box-sizing: border-box;
        }

        .form-group button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            float: right; /* 右侧对齐 */
            margin-top: 10px;
        }

        .form-group button:disabled {
            background-color: #aaa;
            cursor: not-allowed;
        }

        .clearfix::after {
            content: "";
            clear: both;
            display: table;
        }
    </style>
</head>
<body>
    <div class="container">
        <form id="updateForm" action="movieimg.do" method="post" enctype="multipart/form-data">
    <div class="form-row">
        <div class="form-group">
            <label for="movieImgId">圖片ID:</label>
            <input type="text" id="movieImgId" name="movieImgId" value="<%= movieImgVO.getMovieImgId() %>" readonly>
        </div>
        <div class="form-group">
            <label for="movieId">電影ID:</label>
            <input type="text" id="movieId" name="movieId" value="<%= movieImgVO.getMovieId() %>">
        </div>
    </div>
    <div class="form-row">
        <div class="form-group">
            <label for="movieImgName">圖片名稱:</label>
            <input type="text" id="movieImgName" name="movieImgName" value="<%= movieImgVO.getMovieImgName() %>">
        </div>
    </div>
    <div class="form-group-full">
        <label for="movieImgFile">上傳新縮圖:</label>
        <input type="file" id="movieImgFileInput" name="movieImgFile" onchange="previewImage(event)">
    </div>
    <div class="form-group-full label-only">
        <label>目前縮圖:</label>
        <img id="currentThumbnail" src="<%= movieImgVO.getMovieImgFile() %>" alt="目前縮圖" style="height: 100px; width: auto;">
        <input type="hidden" id="currentImgFile" name="currentImgFile" value="<%= movieImgVO.getMovieImgFile() %>">
    </div>
    <div class="form-group clearfix">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="movieImgId" value="<%= movieImgVO.getMovieImgId() %>">
        <button type="submit">更新</button>
    </div>
</form>

    </div>

    <script>
        function previewImage(event) {
            var fileInput = event.target;
            if (fileInput.files && fileInput.files[0]) {
                var reader = new FileReader();
                reader.onload = function(){
                    var output = document.getElementById('currentThumbnail');
                    output.src = reader.result;
                };
                reader.readAsDataURL(fileInput.files[0]);
            } else {
                // 如果没有选择文件，保留原本的图片
                var currentImgFile = document.getElementById('currentImgFile').value;
                document.getElementById('currentThumbnail').src = currentImgFile;
            }
        }
    </script>
</body>
</html>
