<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.text.*"%>
<%@page import="ujs.mlearn.entity.Course"%>
<%@page import="ujs.mlearn.entity.Teacher"%>
<%@page import="java.util.List"%>
<%@page import="ujs.mlearn.dao.impl.CourseDaoImpl"%>
<%@page import="ujs.mlearn.dao.CourseDao"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
<% 
CourseDao cou = new CourseDaoImpl();
Teacher teacher=(Teacher)request.getSession().getAttribute("teacher");
List<Course> courses = cou.findMyCourse(teacher.getTeacherID());
%>

<form 
action="${pageContext.request.contextPath}/WebHomeworkServlet?action=addHomework"
		method="post">
		<table>
			<tr>
				<td>课程名称：</td>
				<td><select name="courseName" >
					<%for(int i=0;i<courses.size();i++){%>  
					<option><%=courses.get(i).getCourseName()%></option>  
					<%}%>  
				</select></td>
			</tr>
			<tr>
				<td>作业标题：</td>
				<td><input name="hwTitle" style="width:500px"></td>
			</tr>
			<tr>
				<td>作业要求：</td>
				<td><textarea rows="20" cols="80" name="hwContent"></textarea></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="布置作业" /> 
				</td>
			</tr>
		</table>
</form>
</body>
</html>