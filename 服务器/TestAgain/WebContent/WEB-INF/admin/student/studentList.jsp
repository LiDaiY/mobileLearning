<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<base>
<style type="text/css">
a {
	text-decoration: NONE
}
</style>

</head>
<body
	style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
	<script type="text/javascript">
		var i = 1;
	</script>
	<table>
		<tr>
			<td>学生姓名</td>
			<td>密码</td>
			<td>邮箱</td>
			<td>签名</td>
			<td>上一次登陆时间</td>
			<td>&nbsp&nbsp&nbsp&nbsp&nbsp操作</td>
		</tr>

		<c:forEach items="${sList}" var="student">
			<tr>
				<td><script type="text/javascript">
					document.write(i + ".  ");
					i++
				</script>${student.username}</td>
				<td>${student.password}</td>
				<td>${student.email}</td>
				<td>${student.signature}</td>
				<td>${student.logintime}</td>
				<td>&nbsp&nbsp&nbsp&nbsp&nbsp<a
					href="${pageContext.request.contextPath }/AdminFunServlet?operation=editStu&studentID=${student.userId}">编辑</a></td>
				
				<td>&nbsp&nbsp&nbsp&nbsp&nbsp<a
					href="${pageContext.request.contextPath }/AdminFunServlet?operation=delStu&studentID=${student.userId}">删除</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>