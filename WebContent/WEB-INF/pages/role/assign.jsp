<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>角色管理 - 分配权限</title>
	<script type="text/javascript">
		$(function(){
			//选取所有父节点对应的子节点
			$(".parentAuthority").each(function(){
				var id = this.id;
				//验证当前父权限的子权限的个数和所有子权限的个数是否相等
				//如果相等，则勾选父节点，否者不勾选
				var flag = $("."+id+":checked").length==$("."+id).length;
				$(this).prop("checked",flag);
				
			});
			
			$(".parentAuthority").click(function(){
				var id = this.id;
				var flag = $(this).is(":checked");
				$("."+id).prop("checked",flag);
			});
			
			$(":checkbox[name='authorities2']").click(function(){
				$(".parentAuthority").each(function(){
					var id = this.id;
					var flag = $("."+id+":checked").length==$("."+id).length;
					$(this).prop("checked",flag);
				});
				
			});
			
		});
	</script>
		
</head>

<body class="main">
 	<form:form action="${ctp}/role/${role.id}" method="POST" modelAttribute="role">
 		<input type="hidden" value="PUT" name="_method">
		<div class="page_title">
			角色管理 &gt; 分配权限
		</div>
		
		<div class="button_bar">
			<button class="common_button" onclick="javascript:history.back(-1);">
				返回
			</button>
			<button class="common_button" onclick="document.forms[1].submit();">
				保存
			</button>
		</div>

		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th class="input_title" width="10%">
					角色名
				</th>
				<td class="input_content" width="20%">
					${role.name }
				</td>
				<th class="input_title" width="10%">
					角色描述
				</th>
				<td class="input_content" width="20%">
					${role.description }
				</td>
				<th class="input_title" width="10%">
					状态
				</th>
				<td class="input_content" width="20%">
					${role.enabled ? '有效':'无效' }
				</td>
			</tr>
			<tr>
				<th class="input_title">
					权限
				</th>
				<td class="input_content" colspan="5" valign="top">
					<c:forEach items="${parentAuthorities }" var="pa">
						<input id="${pa.id }" class="parentAuthority" type="checkbox" />${pa.displayName }:
						<br>
						
						&nbsp;&nbsp;&nbsp;
						<span>
							<form:checkboxes cssClass="${pa.id }" items="${pa.subAuthorities }" path="authorities2"
								itemLabel="displayName" itemValue="id" delimiter="<br>&nbsp;&nbsp;&nbsp;&nbsp;"/>
						</span>
						<br><br>
					</c:forEach>
				</td>
			</tr>
		</table>
 	</form:form>
	
</body>
</html>
