<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Create Session</title>
</head>
<body>
<h2>Create Session</h2>

<jsp:useBean id="cinemaSvc" scope="page" class="com.cinema.model.CinemaService" />


<form action="cinema.do" method="post">
    <input type="hidden" name="action" value="create_session">
    <label for="sessionId">Session ID:</label>
    <input type="text" id="sessionId" name="sessionId">
    <label for="cinemaName">Cinema Name:</label>
   <select name="cinemaName" id="cinemaName">
            <c:forEach var="cinemaName" items="${cinemaSvc.uniqueCinemaNames}">
                <option value="${cinemaName}">${cinemaName}</option>
            </c:forEach>
        </select>
    <button type="submit">Create Session Seats</button>
</form>
</body>
</html>
