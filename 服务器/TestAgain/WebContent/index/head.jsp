<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>移动学习平台教师版</title>
<style>
a {
	text-decoration: NONE
}
</style>
<style>
ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
}

li {
	display: inline;
	float: left;
	width: 120px;
	text-align: center;
}

a {
	text-decoration: none;
	font-size: 15px;
	display: block;
	padding: 8px;
	color: #000000;
}

a:hover {
	background-color: #ffffff;
	color: orange;
}
</style>

</head>
<body style="background-image: url(thead.jpg);">
	<div
		style="height: 50px; font-size: 40px; width: 400px; margin-left: 400px; color: #888888;">移动学习平台教师端</div>
	<div style="height: 30px; margin: 0px auto 0 1px;">
		<ul>
			<li><a href="left.jsp?op=course" target="left">我的课程</a></li>
			<li><a href="left.jsp?op=notice" target="left">通知管理</a></li>
			<li><a href="left.jsp?op=homework" target="left">作业管理</a></li>
			<li><a href="left.jsp?op=test" target="left">试题管理</a></li>
			<li><a href="left.jsp?op=forum" target="left">师生交流</a></li>
			<li><a href="left.jsp?op=material" target="left">课程资源</a></li>
			<li style="margin-left: 50px;"><a
				href="${pageContext.request.contextPath}/WebUserServlet?operation=logout"
				target="_parent">退出</a></li>
		</ul>

	</div>

</body>
</html>