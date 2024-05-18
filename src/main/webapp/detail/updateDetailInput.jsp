<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product_detail.model.*"%>

<% // 見 DetailServlet.java: action is "update" 存入req的empVO物件 
   // (此為從資料庫取出的empVO, 也可以是輸入格式有錯誤時的empVO物件)
	ProductDetail prodDetail = (ProductDetail) request.getAttribute("prodDetail"); // name check ?
	//request.setAttribute("prodDetail", prodDetail); // 同時再存入，為 update 時使用 Loop | try session? 
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%-- // 有需要嗎?
 --%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>明細資料修改 - updateDetailInput.jsp</title>
<style>
	table#table-1 {
		background-color: #CCCCFF;
		border: 2px solid black;
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
	table {
		width: 450px;
		background-color: white;
		margin-top: 1px;
		margin-bottom: 1px;
	}
	table, th, td {
		border: 0px solid #CCCCFF;
	}
	th, td {
		padding: 1px;
	}
</style>
</head>
<body bgcolor='white'>
	<%-- 
	<p>Zoaholic: 1</p>
	 --%>
	<table id="table-1">
		<tr>
			<td>
				<h3>明細資料修改 - updateDetailInput.jsp</h3>
				<p>單純功能展示，明細在正式專案實際不修改</p>
				<h4><a href="selectPage.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
			</td>
		</tr>
	</table>
	<h3>資料修改:</h3>
	<%-- ErrorMsgs list --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<form method="post" action="detail.do" name="form1">
		<table>
			<tr>
				<td>明細ID:<font color=red><b>*</b></font></td>
				<td><%=prodDetail.getProdDetailId()%></td>
			</tr>
			<tr>
				<td>訂單ID:<font color=red><b>*</b></font></td>
				<td><%=prodDetail.getProdOrdId()%></td>
			</tr>
			<tr>
				<td>商品ID:<font color=red><b>*</b></font></td>
				<td><%=prodDetail.getProdId()%></td>
			</tr>
			<tr>
				<td>單價:<font color=red><b>*</b></font></td>
				<td><%=prodDetail.getUnitPrice()%></td>
			</tr>
			<tr>
				<td>數量:</td>
				<td><input type="number" name="prodCount" value="<%=prodDetail.getProdCount()%>"></td>
			</tr>
			<tr>
				<td>小計:</td>
				<%-- 之後想想怎麼自動計算 --%>
				<td><span><%=prodDetail.getProdSum()%> # 無連動</span></td>
			</tr>
			
			<jsp:useBean id="orderSvc" scope="page" class="com.product_order.model.ProductOrderService" />
			<tr>
				<td>訂單:<font color=red><b>*</b></font></td> 
				<td>
					<select size="1" name="prodOrdId">
						<c:forEach var="prodOrd" items="${orderSvc.all}">
							<option value="${prodOrd.prodOrdId}" ${(prodDetail.prodDetailId==prodOrd.prodOrdId)?'selected':'' } >${prodOrd.prodOrdId}
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<br>
									   <%-- value update, how to configure? --%>
		<input type="hidden" name="action" value="update" > 
		<input type="hidden" name="prodDetailId" value="<%=prodDetail.getProdDetailId()%>" >
		<%-- 
		<input type="hidden" name="prodOrdId" value="${prodDetail.prodOrdId}" >
		 --%>
		<input type="hidden" name="prodId" value="${prodDetail.prodId}" >
		<input type="hidden" name="unitPrice" value="${prodDetail.unitPrice}" >
		
		<%-- 
		<input type="hidden" name="prodDetailId" value="${prodDetail.prodDetailId}"/> 
		Check EL work or not? Worked --%>
		<input type="submit" value="送出修改" > 
	</form>
</body>
</html>