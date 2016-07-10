<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ include file="/commons/common.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="atguigu" tagdir="/WEB-INF/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>客户开发计划</title>
<script type="text/javascript">
	$(function() {
		$("img[id^='put-']").click(function(){
			var label = $(this).next(":hidden").val();
			var flag = confirm("确定" + label + "成功?");
			if(!flag){
				return;
			}
			
			var id = $(this).attr("id").split("-")[1];

			$("#hiddenForm").attr("action", "${ctp}/chance/finish/" + id + "?pageNo=${page.pageNo}");
			$("#_method").val("PUT");
			$("#hiddenForm").submit();
		});
	});
</script>
</head>

<body class="main">
	<form action="${ctp }/plan/list" method="post">
		<div class="page_title">客户开发计划</div>
		<div class="button_bar">
			<button class="common_button" onclick="document.forms[1].submit();">
				查询</button>
		</div>
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th class="input_title">客户名称</th>
				<td class="input_content"><input type="text"
					name="search_LIKES_custName" /></td>
				<th class="input_title">概要</th>
				<td class="input_content"><input type="text"
					name="search_LIKES_title" /></td>
				<th class="input_title">联系人</th>
				<td class="input_content"><input type="text"
					name="search_LIKES_contact" /></td>
			</tr>
		</table>
		<br />

		<c:if test="${!empty page.content }">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th>编号</th>
					<th>客户名称</th>
					<th>概要</th>
					<th>联系人</th>
					<th>联系人电话</th>
					<th>创建时间</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				<c:forEach var="chance" items="${page.content }">
					<tr>
						<td class="list_data_number">${chance.id}</td>
						<td class="list_data_text">${chance.custName}</td>
						<td class="list_data_text">${chance.title}</td>
						<td class="list_data_text">${chance.contact}</td>
						<td class="list_data_text">${chance.contactTel}</td>
						<td class="list_data_text"><fmt:formatDate
								value="${chance.createDate }" pattern="yyyy-MM-dd" /></td>
						<td class="list_data_text"><script type="text/javascript">
							switch ('${chance.status}') {
							case '2':
								document.write('开发中');
								break;
							case '3':
								document.write('开发成功');
								break;
							case '4':
								document.write('开发失败');
								break;
							}
						</script></td>
						<td class="list_data_op"><c:if test='${chance.status==2}'>
								<img
									onclick="window.location.href='${ctp }/plan/make/${chance.id}'"
									title="制定计划" src="${ctp }/static/images/bt_plan.gif"
									class="op_button" />
								<img
									onclick="window.location.href='${ctp }/plan/execute/${chance.id}'"
									title="执行计划" src="${ctp }/static/images/bt_feedback.gif"
									class="op_button" />
								<img id="put-${chance.id }"
									title="开发成功" src="${ctp }/static/images/bt_yes.gif"
									class="op_button" />
								<input type="hidden" value="${chance.custName }">
							</c:if> <c:if test='${chance.status!="2"}'>
								<img
									onclick="window.location.href='${ctp }/chance/detail/${chance.id}'"
									title="查看" src="${ctp }/static/images/bt_detail.gif"
									class="op_button" />
							</c:if></td>
					</tr>
				</c:forEach>
			</table>

			<atguigu:page page="${page}"></atguigu:page>

		</c:if>
		<c:if test="${empty page.content }">
			没有任何数据
		</c:if>

	</form>
</body>
</html>
