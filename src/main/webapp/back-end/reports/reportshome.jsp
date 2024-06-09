
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.reports.model.*"%>
<%@ page import="java.util.*"%>

<%
    ReportsService reportsSvc = new ReportsService();  
    List<ReportsVO> list = reportsSvc.getAll(); 
    pageContext.setAttribute("list",list);
    // 不需要透過導演,直接在領班 建立新領班方法
%>
<html>
<head>
    <title>檢舉管理</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>
<h1>檢舉管理</h1>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <p class="error"><%= error %></p>
<% } %>

<table>
    <tr>
        <th>檢舉 ID</th>
        <th>會員 ID</th>
        <th>文章 ID</th>
        <th>留言 ID</th>
        <th>檢舉類型</th>
        <th>檢舉原因</th>
        <th>檢舉狀態</th>
        <th>檢舉時間</th>
        
    </tr>
    <c:forEach var="report" items="${list}">
    <tr>
        <td>${report.reportId}</td>
        <td>${report.memberId}</td>
        <td>${report.articleId}</td>
        <td>${report.commentId}</td>
        <td>${report.reportType}</td>
        <td>${report.reportReason}</td>
<%--         <td>${report.reportStatus}</td> --%>
        
        <td>
            <form action="<%=request.getContextPath()%>/front-end/reports/reports.do" method="post">
                <input type="hidden" name="reportId" value="${report.reportId}"/>
                <input type="hidden" name="articleId" value="${report.articleId}"/>
                <select name="reportStatus">
                    <option value= 0 ${report.reportStatus ==  0 ? 'selected' : ''}>待處理</option>
                    <option value= 1 ${report.reportStatus ==  1 ? 'selected' : ''}>已處理</option>
                </select>

                <input type="hidden" name="action" value="updateReportStatus">
                <input type="submit" value="檢舉修改"/>
            </form>
        </td>
<!--         <td> -->
<%--             <form action="<%=request.getContextPath()%>/front-end/reports/reports.do" method="post"> --%>
<%--                 <input type="hidden" name="reportId" value="${report.reportId}"/> --%>
<%--                 <input type="hidden" name="memberId" value="${report.memberId}"/> --%>
<%--                 <input type="hidden" name="articleId" value="${report.articleId}"/> --%>
<%--                 <input type="hidden" name="commentId" value="${report.commentId}"/> --%>
<%--                 <input type="hidden" name="reportType" value="${report.reportType}"/> --%>
<%--                 <input type="hidden" name="reportReason" value="${report.reportReason}"/> --%>
<%--                 <input type="hidden" name="reportDateTime" value="${report.reportDateTime}"/> --%>
<!--                 <select name="reportStatus"> -->
<%--                     <option value= 0 ${report.reportStatus == 0 ? 'selected' : ''}>待處理</option> --%>
<%--                     <option value= 1 ${report.reportStatus == '1' ? 'selected' : ''}>已處理</option> --%>
<!--                 </select> -->

<!--                 <input type="hidden" name="action" value="update"> -->
<!--                 <input type="submit" value="檢舉修改"/> -->
<!--             </form> -->
<!--         </td> -->
        <td>${report.reportDateTime}</td>
    </tr>
    </c:forEach>
</table>
</body>
</html>