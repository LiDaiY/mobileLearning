<%@page import="ujs.mlearn.entity.Teacher"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<style>
li {
	float: left !important;
	float: none;
	width: auto;
	padding-top: 6px;
	padding-bottom: 6px;
	border-bottom: 1px solid #AAA;
}

a {
	text-decoration: NONE
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
<body style="background-image: url(tleft.jpg)">
	<%
		String op = request.getParameter("op");
		Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
		if (op.equals("notice")) {
	%>
	<ul style="list-style-type: none">
		<li><a href="${pageContext.request.contextPath }/WebNoticeServlet?action=goShowPage" target="body">发布通知</a></li>
		<li><a
			href="${pageContext.request.contextPath }/WebNoticeServlet?action=findMyNotice"
			target="body">编辑通知</a></li>
	</ul>
	<%
		} else if (op.equals("homework")) {
	%>
	<ul style="list-style-type: none">
		<li><a href="${pageContext.request.contextPath }/WebHomeworkServlet?action=goShowPage" target="body">发布作业信息</a></li>
		<li><a
			href="${pageContext.request.contextPath }/WebHomeworkServlet?action=findMyCourse"
			target="body">编辑作业</a></li>
	</ul>
	<%
		} else if (op.equals("test")) {
	%>
	<ul style="list-style-type: none">
		<li><a href="${pageContext.request.contextPath }/WebTestServlet?action=goShowPage" target="body">添加试题</a></li>
		<li><a
			href="${pageContext.request.contextPath }/WebTestServlet?action=findMyCourse"
			target="body">编辑试题</a></li>

	</ul>

	<%
		} else if (op.equals("forum")) {
	%>
	<ul style="list-style-type: none">
		<li><a href="${pageContext.request.contextPath }/WebBbsServlet?action=findQuestion" target="body">查看问题</a></li>
		<li><a
			href="${pageContext.request.contextPath }/WebBbsServlet?action=showReplyList"
			target="body">我的回答</a></li>
	</ul>

	<%
		} else if (op.equals("course")) {
	%>
	<ul style="list-style-type: none">
		<li><a href="${pageContext.request.contextPath }/WebCourseServlet?action=goShowPage" target="body">增加课程</a></li>
		<li><a
			href="${pageContext.request.contextPath }/WebCourseServlet?action=findMyCourse"
			target="body">编辑课程</a></li>
	</ul>
	<%
		} else {
	%>
	<ul style="list-style-type: none">
		<li><a href="${pageContext.request.contextPath }/WebMaterialServlet?action=goShowPage" target="body">上传课程资料</a></li>
		<li><a
			href="${pageContext.request.contextPath }/WebMaterialServlet?action=findCourseList"
			target="body">我的课程资料</a></li>
	</ul>
	<%} %>
</body>
</html>