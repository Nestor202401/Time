<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <title>忘記密碼</title>
	<style >
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
      max-width: 600px;
      height: 280px;
      width: 100%;
      background: #fff;
      padding: 25px 30px;
      border-radius: 5px;
      position: relative; /* 新增的屬性 */
    }

    .container .title {
      font-size: 25px;
      font-weight: 500;
      position: relative;
      text-align: center;
      margin-bottom: 30px;
    }

    .container .title::before {
      content: '';
      position: absolute;
      left: 50%;
      bottom: 0;
      transform: translateX(-50%);
      height: 3px;
      width: 100px;
      background: linear-gradient(135deg, #71b7e6, #9b59b6);
    }

    form {
      text-align: center;
    }

    form .input-box {
      margin-bottom: 20px; /* 調整下邊距 */
      text-align: center; /* 文本置中 */
    }

    form .input-box input[type="text"] {
      width: calc(100% - 60px); /* 調整輸入框寬度 */
      height: 35px; /* 調整輸入框高度 */
      margin-top: 15px;
      padding: 5px 10px;
      font-size: 18px;
    }

    form .input-box .details {
      font-weight: bold; /* 加粗文字 */
      color: #333; /* 使用稍深顏色 */
 
    }

    form .button {
      margin-top: 40px; /* 調整上邊距 */
    }

    form .button input[type="submit"] {
      height: 40px;
      width: 120px; /* 調整按鈕寬度 */
      outline: none;
      color: #fff;
      font-size: 16px;
      font-weight: 500;
      border: none;
      background: linear-gradient(135deg, #71b7e6, #9b59b6);
      cursor: pointer;
      transition: background 0.3s ease;
    }

    form .button input[type="submit"]:hover {
      background: linear-gradient(135deg, #5a8fbb, #7e4585);
    }
    .back-button {
      position: absolute;
      top: 20px;
      right: 10px;
      font-size: 18px;
      color: #333;
      text-decoration: none;
      font-weight: bold;
    }

    /* 滑過時底線效果 */
    .back-button:hover {
      text-decoration: underline;
    }
	
	</style>
</head>
<body>
  <div class="container">
    <div class="title">忘記密碼</div>
    <a href="${pageContext.request.contextPath}/front-end/member/memberLogin.jsp" class="back-button">回登入頁面</a>
    
    <form  id="forgetPasswordForm"   th:action="#" >
      <div class="input-box">
        <span class="details">電子信箱</span>
        <input  id="emailInput"  type="text" placeholder="請輸入您的電子信箱" required>
      </div>
      <div class="button">
        <input type="submit" value="送出">
          <div id="message" style="display: none; color: red;"></div>
      </div>
    </form>
  </div>
  
  
  <script>
  var contextPath = '${pageContext.request.contextPath}';
  var servletUrl = contextPath + '/mem';
    $(document).ready(function() {
        $('#forgetPasswordForm').submit(function(event) {
            event.preventDefault(); // 阻止表單默認提交行為
            
            // 獲取表單數據
            var formData = {
                email: $('#emailInput').val(),
                action: 'forgetPassword'
            };
            
            // 使用AJAX提交表單數據
            $.ajax({
                type: 'POST',
                url: servletUrl,
                data: formData,
                
                success: function(response) {
                	$('input[type="text"]').val('');
                    // 成功時顯示消息
                    $('#message').text(response).show();
                },
                error: function(xhr, status, error) {
                    // 失敗時顯示消息
                    $('#message').text(xhr.responseText).show();
                }
            });
        });
    });
</script>
</body>
</html>
