<%@page import="ujs.mlearn.entity.Teacher"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body
	style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
	<form
		action="${pageContext.request.contextPath}/AdminFunServlet?operation=modTea"
		method="post">
		<%Teacher teacher=(Teacher)request.getAttribute("teacher"); %>
		<table>
			<tr>
				<td>邮箱：</td>
				<td><input type="text" name="email"
					value="${teacher.email}" /></td>
			</tr>
			<tr>
				<td>姓名：</td>
				<td><input type="text" name="teacherName"
					value="${teacher.teacherName}" /></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input type="text" name="password"
					value="${teacher.password}" /></td>
			</tr>
			<tr>
				<td>性别：</td>
				<td><select name="sex">
				<%if (teacher.getSex().equals("男")) {%>
						<option selected="selected">男</option>
						<option>女</option>
						<%}else { %>
						<option >男</option>
						<option selected="selected">女</option>
						<%} %>
				</select></td>
			</tr>
			<tr>
				<td>签名：</td>
				<td><input type="text" name="signature"
					value="${teacher.signature}" /></td>
			</tr>
			<tr>
				<td>上一次登陆时间：</td>
				<td>"${teacher.logintime}"</td>
			</tr>
			<tr>
				<td colspan="3" align="center"><input type="submit" value="修改" />
					<input type="reset" /></td>

			</tr>

		</table>
		<input type="hidden" name="teacherID" value="${teacher.teacherID}">
	</form>
</body>
</html>