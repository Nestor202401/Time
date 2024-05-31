<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page</p>

<h3>資料查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='listAllTicTypes.jsp'>查詢所有票種</a><br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="tictypes.do" >
        <b>輸入票種ID (如1520001):</b>
        <input type="text" name="ticketTypesId">
        <input type="hidden" name="action" value="getOne_For_Display">
        <button type="submit" >送出</button>
    </FORM>
  </li>

  <jsp:useBean id="ticTypesSvc" scope="page" class="com.tictypes.model.TicketTypesService" />
   
  <li>
     <FORM METHOD="post" ACTION="tictypes.do" >
       <b>選擇票種ID:</b>
       <select size="1" name="ticketTypesId">
         <c:forEach var="ticTypesVO" items="${ticTypesSvc.all}" > 
          <option value="${ticTypesVO.ticketTypesId}">${ticTypesVO.ticketTypesId}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="tictypes.do" >
       <b>選擇票種名稱:</b>
       <select size="1" name="ticketTypesId">
         <c:forEach var="ticTypesVO" items="${ticTypesSvc.all}" > 
          <option value="${ticTypesVO.ticketTypesId}">${ticTypesVO.ticketTypeName}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>


<h3>票種管理</h3>

<ul>
  <li><a href='addTicTypes.jsp'>新增票種</a></li>
</ul>

</body>
</html>