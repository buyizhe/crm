<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>查看计划</title>
  </head>

  <body class="main">
	<span class="page_title">查看计划</span>
	<div class="button_bar">
		<button class="common_button" onclick="javascript:history.back(-1);">返回</button>			
	</div>
  	<form action="${ctp}/plan/execute" method="post">
		<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>
				<th>编号</th>
				<td>${salesChance.id }&nbsp;</td>
				
				<th>机会来源</th>
				<td>${salesChance.source }&nbsp;</td>
			</tr>
			<tr>
				<th>客户名称</th>
				<td>${salesChance.custName }&nbsp;</td>
				
				<th>成功机率（%）</th>
				<td>${salesChance.rate }&nbsp;</td>
			</tr>
			
			<tr><th>概要</th>
				<td colspan="3">${salesChance.title }&nbsp;</td>
			</tr>
			
			<tr>
				<th>联系人</th>
				<td>${salesChance.contact }&nbsp;</td>
				<th>联系人电话</th>
				<td>${salesChance.contactTel }&nbsp;</td>
			</tr>
			<tr>
				<th>机会描述</th>
				<td colspan="3">${salesChance.description }&nbsp;</td>
			</tr>
			<tr>
				<th>创建人</th>
				<td>${salesChance.createBy.name }&nbsp;</td>
				<th>创建时间</th>
				<td><fmt:formatDate value="${salesChance.createDate }" pattern="yyyy-MM-dd"/>&nbsp;</td>
			</tr>		
			<tr>					
				<th>指派给</th>
				<td>${salesChance.designee.name }&nbsp;</td>
			</tr>
		</table>
	
	<br />
	
		<table class="data_list_table" border="0" cellPadding="3" cellSpacing="0">
			<c:forEach items="${salesChance.salesPlans }" var="salesPlan">
				<tr>
					<th width="200px">日期</th>
					<th>计划</th>
					<th>执行效果</th>
				</tr>
			
				<tr>
					<td class="list_data_text">
						<fmt:formatDate value="${salesPlan.date }" pattern="yyyy-MM-dd"/>&nbsp;
					</td>
					<td class="list_data_ltext">${salesPlan.todo }&nbsp;</td>
					<td>${salesPlan.result }&nbsp;</td>
				</tr>
			</c:forEach>	
		</table>	
	
  </form>
  </body>
</html>
