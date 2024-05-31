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

<h3>��Ƭd��:</h3>
	
<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='listAllTicTypes.jsp'>�d�ߩҦ�����</a><br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="tictypes.do" >
        <b>��J����ID (�p1520001):</b>
        <input type="text" name="ticketTypesId">
        <input type="hidden" name="action" value="getOne_For_Display">
        <button type="submit" >�e�X</button>
    </FORM>
  </li>

  <jsp:useBean id="ticTypesSvc" scope="page" class="com.tictypes.model.TicketTypesService" />
   
  <li>
     <FORM METHOD="post" ACTION="tictypes.do" >
       <b>��ܲ���ID:</b>
       <select size="1" name="ticketTypesId">
         <c:forEach var="ticTypesVO" items="${ticTypesSvc.all}" > 
          <option value="${ticTypesVO.ticketTypesId}">${ticTypesVO.ticketTypesId}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="�e�X">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="tictypes.do" >
       <b>��ܲ��ئW��:</b>
       <select size="1" name="ticketTypesId">
         <c:forEach var="ticTypesVO" items="${ticTypesSvc.all}" > 
          <option value="${ticTypesVO.ticketTypesId}">${ticTypesVO.ticketTypeName}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="�e�X">
     </FORM>
  </li>
</ul>


<h3>���غ޲z</h3>

<ul>
  <li><a href='addTicTypes.jsp'>�s�W����</a></li>
</ul>

</body>
</html>