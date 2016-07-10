<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>制定计划</title>
	<script type="text/javascript">
		$(function(){
			// ajax的添加 待写
			$("#save").click(function() {
				
				var date = $("#date").val();
				var todo = $("#todo").val();
				
				
				var url = "${ctp}/plan/make/${salesChance.id }";
				var data = {"date":date,"todo":todo,"time":new Date()};
				
				
				$.post(url,data,function(mes){
					var reg = /^\d+\$/g;
					if(reg.test(mes)) {
						
						var $tr = $("<tr id='plan-" + mes + "'></tr>")
						$tr.append("<td class='list_data_text'>" + date + "</td>")
						   .append("<td class='list_data_ltext'><input type='text' size='50' value=" + todo + " id='todo-" + mes + "'/><button class='common_button' id='save-" + mes + "'>保存</button> <button class='common_button' id='delete-" + mes + "'>删除</button></td>");
						$("#table1").append($tr);
						
						$tr.find("#delete-" + mes).click(function(){
							deleteFun(this);
							return false;
						});
						
						$tr.find("#save-" + mes).click(function(){
							saveFun(this);
							return false;
						});
						
					}
				});
				
				return false; 
			}); 
			
			// 通过ajax删除计划
			$("button[id ^= 'delete-']").click(function(){deleteFun(this);return false});
			
			function deleteFun(button){
				var planId = button.id.split("-")[1];
				var url = "${ctp}/plan/make/"+planId;
			
				$.ajax({
					  type: "DELETE",
					  url: url,
					  success: function(message) {
						  if(message == '1') {
							  $("#plan-"+planId).remove();
							  alert("删除成功!");
						  } else{
							  alert("删除失败!")
						  }
					  }
					});	
				
			}
			
			// 通过ajax保存计划
			$("button[id ^= 'save-']").click(function(){saveFun(this); return false});
			
			function saveFun(button){
				
				var planId = button.id.split("-")[1];
				var todo = $("#todo-"+planId).val();
				
				var url = "${ctp}/plan/make/"+planId +"?todo="+todo;
				
				$.ajax({
					  type: "PUT",
					  url: url,
					  success: function(message) {
						  if(message == '1') {
							  alert("保存成功!");
						  } else{
							  alert("保存失败!")
						  }
					  }
					});	
				
				return false;
			};
			
			$("#execute").click(function() {
				var url = "${ctp}/plan/execute/${salesChance.id}";
				window.location.href=url;
				return false;
			});
			
		})
	</script>
</head>

<body class="main">
	<span class="page_title">制定计划</span>
	<div class="button_bar">
		<button class="common_button" id="execute">
			执行计划
		</button>
		<button class="common_button" onclick="javascript:history.go(-1);">
			返回
		</button>
	</div>
	
		<form action="${ctp}/plan/make/${salesChance.id }" method="post">
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th>
					编号
				</th>

				<td>
					${salesChance.id }
				</td>
				<th>
					机会来源
				</th>

				<td>
					${salesChance.source }
				</td>
			</tr>
			<tr>
				<th>
					客户名称
				</th>
				<td>
					${salesChance.custName }
				</td>
				<th>
					成功机率（%）
				</th>

				<td>
					${salesChance.rate }
				</td>
			</tr>
			<tr>
				<th>
					概要
				</th>
				<td colspan="3">
					${salesChance.title }
				</td>
			</tr>
			<tr>
				<th>
					联系人
				</th>

				<td>
					${salesChance.contact }
				</td>
				<th>
					联系人电话
				</th>

				<td>
					${salesChance.contactTel }
				</td>
			</tr>
			<tr>
				<th>
					机会描述
				</th>
				<td colspan="3">
					${salesChance.description }
				</td>
			</tr>
			<tr>
				<th>
					创建人
				</th>
				<td>
					${salesChance.createBy.name }
				</td>
				<th>
					创建时间
				</th>
				<td>
					<fmt:formatDate value="${salesChance.createDate }" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<th>
					指派给
				</th>
				<td>
					${salesChance.designee.name }
				</td>

			</tr>
		</table>

		<br />
		
		<table id="table1" class="data_list_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th width="200px">
					日期
				</th>
				<th>
					计划项
				</th>
			</tr>
			<c:if test="${!empty salesChance.salesPlans }">
				<c:forEach items="${salesChance.salesPlans }" var="salesPlan">
					<tr id="plan-${salesPlan.id}">
						<td class="list_data_text">
							<fmt:formatDate value="${salesPlan.date }" pattern="yyyy-MM-dd"/>
							&nbsp;
						</td>
						<td class="list_data_ltext">
							<c:if test="${salesPlan.result == null }">
								<input type="text" size="50"
									value="${salesPlan.todo}" id="todo-${salesPlan.id}"/>
								<button class="common_button" id="save-${salesPlan.id}">
									保存
								</button>
								<button class="common_button" id="delete-${salesPlan.id}">
									删除
								</button>
							</c:if>
							<c:if test="${salesPlan.result != null }">
								<input type="text" size="50"
									value="${salesPlan.todo}" readonly="readonly"/>
								<input type="text" size="50"
									value="${salesPlan.result}" readonly="readonly"/>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		<div class="button_bar">
			<button class="common_button" id="save">
				新建
			</button>
		</div>
		<input type="hidden" name="chance.id" value="${salesChance.id }" />
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th>
					日期
					<br />
					(格式: yyyy-mm-dd)
				</th>
				<td>
					<input type="text" name="date" id="date" />
					&nbsp;
				</td>
				<th>
					计划项
				</th>
				<td>
					<input type="text" name="todo" size="50" id="todo" />
					&nbsp;
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
