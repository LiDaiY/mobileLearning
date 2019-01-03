<%@page import="ujs.mlearn.entity.StudentCourse"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Insert title here</title>
</head>
<body style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
<%HashMap<Integer,String> name=(HashMap<Integer,String>)request.getAttribute("name");
  List<StudentCourse> sCourses=(List<StudentCourse>)request.getAttribute("studentcourse"); %>
		<table>
			<tr>
				<td>学生姓名</td>
				<td>学生分数</td>
				<td>学生答案</td>
			</tr>
				<%for(StudentCourse sCourse:sCourses){ %>
					<tr>
						<td><%out.print(name.get(sCourse.getStudentID())); %></td>
						<td><%out.print(sCourse.getStudentGrade()); %></td>
						<td><%out.print(sCourse.getStudentAnswer()); %></td>
					</tr>
					<% }%>
		</table>


</body>
</html>