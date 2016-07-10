<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>

<%@ include file="/commons/common.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>编辑交往记录</title>
   <script type="text/javascript">
   		$(function(){
   			$("#saveBtn").click(function(){
   				var id = $("#id").val();
   				if(id == null || id == ""){
   					$("form:eq(1)").submit();
   				}else{
   					$("form:eq(1)").attr("action","${ctp }/activity/update?pageNo=${param.pageNo}");
   					$("form:eq(1)").submit();
   				}
   			})
   			
   		})
   </script>
  </head>

  <body class="main">
  
 	<span class="page_title">编辑交往记录</span>
	<div class="button_bar">
		<button class="common_button" onclick="javascript:history.go(-1);">返回</button>
		<button class="common_button" id="saveBtn">保存</button>
	</div>
  
 	 <form:form action="${ctp }/activity/addActivity" method="POST" modelAttribute="activity">
 	 	<c:if test="${id != null }">
  			<input type="hidden" name="_method" value="PUT"/>
  		</c:if>
  		
  		<form:hidden path="id"/>
		  		
  		<c:if test="${activity.customer.id != null}">
			<form:hidden path="customer.id"/>
  		</c:if>
  		<c:if test="${activity.customer.id == null}">
	  		<input type="hidden" name="customer.id" value="${param.customerid }"/>
  		</c:if>
  		
		<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>
				<th>时间</th>
				<td><form:input path="date"/><span class="red_star">*</span></td>
				<th>地点</th>
				<td><form:input path="place"/><span class="red_star">*</span></td>
			</tr>
			<tr>
				<th>概要</th>
				<td colspan="3"><form:input path="title"/><span class="red_star">*</span></td>
			</tr>
			<tr>		
				<th>详细信息</th>
				<td colspan="3"><form:textarea path="description"/></td>
			</tr>
		</table>
  	</form:form>
  </body>
</html>
