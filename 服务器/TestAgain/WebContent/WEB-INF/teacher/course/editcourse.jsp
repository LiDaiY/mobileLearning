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
		action="${pageContext.request.contextPath}/WebCourseServlet?action=editCourse"
		method="post" >
		<table>
			<tr>
				<td>课程名称：</td>
				<td><input type="text" name="courseName" value="${course.courseName}"/></td>
			</tr>
			<tr>
				<td>老师姓名：</td>
				<td><input type="text" name="teacherName" value="${course.teacherName}"/></td>
			</tr>
			<tr>
				<td>课程简介：</td>
				<td><input type="text" name="courseAbstract" value="${course.courseAbstract}"/></td>
			</tr>
			<tr>
				<td>详细信息：</td>
				<td><textarea rows="20" cols="80" name="detailInfo" >${course.detailInfo}</textarea></td>
			</tr>

			<tr>
				<td><input type="hidden" name="courseID" value="${course.courseID}"></td>
				<td><input type="submit" value="编辑" /> <input type="reset" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>