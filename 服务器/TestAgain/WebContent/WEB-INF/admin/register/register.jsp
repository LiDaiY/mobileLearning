<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg">

	<form
		action="${pageContext.request.contextPath}/AdminFunServlet?operation=addUser"
		method="post" >
		<table>
			<tr>
				<td>用户类型</td>
				<td><select name="userType">
						<option>学生</option>
						<option>老师</option>
				</select></td>
			</tr>
			<tr>
				<td>用户姓名：</td>
				<td><input type="text" name="userName"/></td>
			</tr>
			<tr>
				<td>用户密码：</td>
				<td><input type="text" name="userPass"/></td>
			</tr>
			<tr>
				<td>用户邮箱</td>
				<td><input type="text" name="userEmail"/></td>
			</tr>

			<tr>
				<td></td>
				<td><input type="submit" value="注册" />
			</tr>
			<tr>
				<td></td>
				<td><font color="red" >${requestScope.message}</font></td>
			</tr>
		</table>
	</form>
</body>
</html>