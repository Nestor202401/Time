<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.movie.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    MovieService movieSvc = new MovieService();
    List<MovieVO> list = movieSvc.getAll();
    if(request.getAttribute("movieListData")==null) pageContext.setAttribute("movieListData",list);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>電影查詢與編輯</title>
    <link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/fixedcolumns/5.0.0/css/fixedColumns.dataTables.min.css">
    <link rel="stylesheet" href="style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            padding: 20px;
            background-color: #fff;
            margin: 20px auto;
            max-width: 1200px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            border-radius: 8px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            border: 1px solid #ddd;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
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
        div.table-container {
            min-width: auto;
            min-height: auto;
            overflow: auto;
        }
        .inline-form {
            display: inline-block;
            margin-right: 10px;
        }
        .inline-button {
            display: inline-block;
            height: 30px;
            margin-left: 10px;
            background-color: #68a16a;
            color: white;
            border: none;
            border-radius: 4px;
            padding: 5px 10px;
            cursor: pointer;
            font-size: 14px;
        }
        .inline-button:hover {
            background-color: #45a049;
        }
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 60%;
            max-width: 800px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }
    </style>
</head>
<body>

<div class="container">
    <form id="dateForm" ACTION="<%=request.getContextPath()%>/back-end/movie/movie.do" name="form1">
        <div style="display: flex; align-items: center;">
            <span style="margin: 10px;">搜尋</span>
            <input type="text" name="movieName" placeholder="電影名稱" value="" style="height: 20px;width: 120px;">
            <span style="margin: 10px;">選擇</span>
            <select id="movieRating" name="movieRating" style="height: 25px;">
                <option value="">請選擇</option>
                <option value="1">普遍級</option>
                <option value="2">保護級</option>
                <option value="3">輔導級</option>
                <option value="4">限制級</option>
            </select>
            <select name="searchBy" style="height: 25px;margin-left: 10px;">
                <option value="">請選擇</option>
                <option value="releaseDate">上映中</option>
                <option value="endDate">非上映</option>
            </select>
            <button type="submit" class="inline-button">查詢</button>
            <input type="hidden" name="action" value="listMovie_ByCompositeQuery">
        </div>
    </form>

    <div style="display: flex; align-items: center; margin-top: 10px; margin-bottom: 10px;">
        <span style="margin: 0;">快速搜尋</span>
        <div>
            <form id="dateForm1" ACTION="<%=request.getContextPath()%>/back-end/movie/movie.do" name="form1" class="inline-form">
                <button type="submit" class="inline-button">查詢全部</button>
                <input type="hidden" name="action" value="QuickSearch">
                <input type="hidden" name="searchType" value="all">
            </form>
            <form id="dateForm2" ACTION="<%=request.getContextPath()%>/back-end/movie/movie.do" name="form2" class="inline-form">
                <button type="submit" class="inline-button">熱映電影</button>
                <input type="hidden" name="action" value="QuickSearch">
                <input type="hidden" name="searchType" value="nowShowing">
            </form>
            <form id="dateForm3" ACTION="<%=request.getContextPath()%>/back-end/movie/movie.do" name="form3" class="inline-form">
                <button type="submit" class="inline-button">下映電影</button>
                <input type="hidden" name="action" value="QuickSearch">
                <input type="hidden" name="searchType" value="ended">
            </form>
            <form id="dateForm4" ACTION="<%=request.getContextPath()%>/back-end/movie/movie.do" name="form4" class="inline-form">
                <button type="submit" class="inline-button">即將上映</button>
                <input type="hidden" name="action" value="QuickSearch">
                <input type="hidden" name="searchType" value="comingSoon">
            </form>
            <button type="button" id="modalBtn" class="inline-button" style="margin-left: 130px;">新增電影</button>
        </div>
    </div>

    <div id="modal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>新增電影</h2>
            <iframe id="modalIframe" src="./listAllMovie.jsp" style="width: 100%; height: 500px; border: none;"></iframe>
        </div>
    </div>

    <div id="editModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeEditModal()">&times;</span>
            <h2>Movie Details</h2>
            <iframe id="editModalIframe" src="" style="width: 100%; height: 500px; border: none;"></iframe>
        </div>
    </div>

    <div class="table-container" style="height: 20px;"></div>
    <div class="table-container">
        <table id="example" class="display nowrap" style="width:100%">
            <thead>
                <tr>
                    <th class="fixed-column" data-orderable="false"></th>
                    <th class="main-column">ID</th>
                    <th class="main-column">電影名稱</th>
                    <th class="main-column">電影分級</th>
                    <th class="main-column">導演</th>
                    <th class="main-column">演員</th>
                    <th class="main-column">上映日期</th>
                    <th class="main-column">下映日期</th>
                    <th class="main-column">片長</th>
                    <th class="main-column" style="text-align: left;">簡介</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="movieVO" items="${movieListData}">
                    <tr>
                        <td class="fixed-column">
                            <button onclick="openEditModal('${movieVO.movieId}')" class="inline-button">Edit</button>
                        </td>
                        <td class="main-column">${movieVO.movieId}</td>
                        <td class="main-column">${movieVO.movieName}</td>
                        <td class="main-column">
                            <c:choose>
                                <c:when test="${movieVO.movieRating eq '1'}">普遍級</c:when>
                                <c:when test="${movieVO.movieRating eq '2'}">保護級</c:when>
                                <c:when test="${movieVO.movieRating eq '3'}">輔導級</c:when>
                                <c:when test="${movieVO.movieRating eq '4'}">限制級</c:when>
                                <c:otherwise>未知</c:otherwise>
                            </c:choose>
                        </td>
                        <td class="main-column">${movieVO.director}</td>
                        <td class="main-column">${movieVO.actor}</td>
                        <td class="main-column"><fmt:formatDate value="${movieVO.releaseDate}" pattern="yyyy-MM-dd" /></td>
                        <td class="main-column"><fmt:formatDate value="${movieVO.endDate}" pattern="yyyy-MM-dd" /></td>
                        <td class="main-column">${movieVO.runtime}</td>
                        <td class="main-column" style="text-align: left;">${movieVO.introduction}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script src="//apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/fixedcolumns/5.0.0/js/dataTables.fixedColumns.min.js"></script>
<script src="script.js"></script>
<script>
    $(document).ready(function() {
        var table = $('#example').DataTable({
            scrollY: "500px",
            scrollX: true,
            scrollCollapse: true,
            paging: true,
            pageLength: 10, 
            lengthChange: false,
            fixedColumns: {
                leftColumns: 1,
                rightColumns: 0
            },
            searching: false,
            info: false,
            ordering: true,
            order: [[1, 'asc']]
        });
    });

    $("#datepicker").datepicker({
        showAnim: "slideDown",
        dateFormat: "yy-mm-dd",
        onSelect: function(selectedDate) {
            $("#datepicker2").datepicker("option", "minDate", selectedDate);
        }
    });

    $("#datepicker2").datepicker({
        showAnim: "slideDown",
        dateFormat: "yy-mm-dd",
        onSelect: function(selectedDate) {
            $("#datepicker").datepicker("option", "maxDate", selectedDate);
        }
    });

    $("#datepicker").on("input", function() {
        if ($(this).val() === "") {
            $("#datepicker2").datepicker("option", "minDate", null);
        }
    });

    $("#datepicker2").on("input", function() {
        if ($(this).val() === "") {
            $("#datepicker").datepicker("option", "maxDate", null);
        }
    });

    $("#dateForm").submit(function(event) {
        localStorage.setItem("start_date", $("#datepicker").val());
        localStorage.setItem("end_date", $("#datepicker2").val());
        localStorage.setItem("search", $("#search").val());
    });

    function openEditModal(movieId) {
        var modal = document.getElementById("editModal");
        var iframe = document.getElementById("editModalIframe");
        iframe.src = "<%=request.getContextPath()%>/back-end/movie/movie.do?action=getOne_For_Update&movieId=" + movieId;
        modal.style.display = "block";
    }

    function closeEditModal() {
        var modal = document.getElementById("editModal");
        modal.style.display = "none";
    }

    $('#modalBtn').on('click', function() {
        $('#modalIframe').attr('src', '../movieimg/add.jsp');
        $('#modal').fadeIn(300);
    });

    $('#modal').on('hidden.bs.modal', function (e) {
        $('#modalIframe').attr('src', '');
    });
</script>
</body>
</html>
