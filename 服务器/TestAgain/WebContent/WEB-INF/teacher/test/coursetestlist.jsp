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
			<td>课程名称</td>
			<td>老师</td>
			<td>简介</td>
			<td>操作</td>
		</tr>

		<c:forEach items="${courses}" var="course">
			<tr>
				<td><script type="text/javascript">
					document.write(i + ".  ");
					i++
				</script>${course.courseName}</td>
				<td>${course.teacherName}</td>
				<td>${course.courseAbstract}</td>
				<td><a
					href="${pageContext.request.contextPath }/WebTestServlet?action=findCourseTest&courseID=${course.courseID}">编辑试题</a></td>
				<td><a
					href="${pageContext.request.contextPath }/WebTestServlet?action=findResult&courseID=${course.courseID}">&nbsp&nbsp&nbsp&nbsp查看答题情况</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>