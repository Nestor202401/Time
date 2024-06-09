<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tictypes.model.*"%>

<%
    TicketTypesService ticTypesSvc = new TicketTypesService();
    List<TicketTypesVO> list = ticTypesSvc.getAll();
    pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <title>���ت��</title>
    <!-- DataTables CSS -->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.3/css/jquery.dataTables.css">
    <!-- Bootstrap CSS for Modal -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- jQuery -->
    <script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <!-- DataTables JS -->
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.js"></script>
    <!-- Bootstrap JS for Modal -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 20px;
        }
        table.dataTable {
            width: 100%;
            border-collapse: collapse;
        }
        table.dataTable thead {
            background-color: #343a40;
            color: #ffffff;
        }
        table.dataTable thead th {
            padding: 10px;
            text-align: center;
        }
        table.dataTable tbody tr:nth-child(odd) {
            background-color: #ffffff;
        }
        table.dataTable tbody tr:nth-child(even) {
            background-color: #f1f1f1;
        }
        table.dataTable tbody td {
            padding: 10px;
            text-align: center;
        }
        .edit-button {
            background-color: #007bff;
            color: #ffffff;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .edit-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h2>���ت��</h2>
    <table id="ticketTable" class="display">
        <thead>
            <tr>
                <th>����ID</th>
                <th>���ئW��</th>
                <th>����</th>
                <th><button class="edit-button" data-toggle="modal" data-target="#editModal" onclick="prepareEditModal('�s�W')">�s�W</button></th>
            </tr>
        </thead>
        <tbody>
            <!-- �o�̬O�ܨҼƾڡA�i�H�ʺA�ͦ� -->
            <c:forEach var="ticTypesVO" items="${list}">
            <tr>
                <td>${ticTypesVO.ticketTypesId}</td>
                <td>${ticTypesVO.ticketTypeName}</td>
                <td>${ticTypesVO.ticketPrice}</td>
                <td>
                    <button class="edit-button" data-toggle="modal" data-target="#editModal" onclick="prepareEditModal('�ק�', ${ticTypesVO.ticketTypesId})">�ק�</button>
                </td>
            </tr>
            </c:forEach>
            <!-- �ʺA�ͦ����ƾڵ��� -->
        </tbody>
    </table>

    <!-- Modal -->
    <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editModalLabel">�s�貼��</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <iframe id="modalIframe" src="" frameborder="0" style="width: 100%; height: 400px;"></iframe>
                </div>
            </div>
        </div>
    </div>

    <script>
        $(document).ready(function() {
            $('#ticketTable').DataTable({
            	"pageLength": 6,
                "ordering": false,
                "searching": false,
                "lengthChange": false,
                "pagingType": "simple_numbers",
                "language": {
                    "zeroRecords": "�S�����ŦX�����",
                    "info": "��ܲ� _PAGE_ ���A�@ _PAGES_ ��",
                    "infoEmpty": "�S���ŦX�����",
                    "infoFiltered": "(�q _MAX_ ����ƹL�o)",
                    "paginate": {
                        "previous": "�W�@��",
                        "next": "�U�@��"
                    }
                }
            });
            $('#editModal').on('hidden.bs.modal', function () {
                // �����ҺA�ث��s����
                location.reload();
            });
        });

        function prepareEditModal(action, ticketTypesId) {
            var iframeSrc;
            if (action === '�s�W') {
                iframeSrc = 'add.jsp'; // �s�W���ت����� URL
            } else {
                iframeSrc = "<%=request.getContextPath()%>/back-end/tictypes/tictypes.do?action=getOne_For_Update&ticketTypesId=" + ticketTypesId;
            }
            $('#modalIframe').attr('src', iframeSrc);
            $('#editModal').modal('show');
        }
    </script>
</body>
</html>
