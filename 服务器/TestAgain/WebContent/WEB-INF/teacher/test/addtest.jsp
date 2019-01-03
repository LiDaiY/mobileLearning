<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<%@ page import="java.text.*"%>
<%@page import="ujs.mlearn.entity.Course"%>
<%@page import="ujs.mlearn.entity.Teacher"%>
<%@page import="java.util.List"%>
<%@page import="ujs.mlearn.dao.impl.CourseDaoImpl"%>
<%@page import="ujs.mlearn.dao.CourseDao"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
<% 
CourseDao cou = new CourseDaoImpl();
Teacher teacher=(Teacher)request.getSession().getAttribute("teacher");
List<Course> courses = cou.findMyCourse(teacher.getTeacherID());
%>
	<form
		action="${pageContext.request.contextPath}/WebTestServlet?action=addTest"
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
				<td>试题题目：</td>
				<td><input name="testContent" style="width:500px"></td>
			</tr>
			<tr>
				<td>试题类型：</td>
				<td><select onblur="isSelected(this.value)" name="testtype"
					id="test">
						<option value="1">单选</option>
						<option value="2">多选</option>
				</select></td>
			</tr>
			<tr>
				<td>试题选项：</td>
				<td>A:<input type="text" name="testOptionA"> 
					B:<input type="text" name="testOptionB"> 
					C:<input type="text" name="testOptionC"> 
					D:<input type="text" name="testOptionD">
				</td>
				
			</tr>
			<tr>
				<td>试题答案：</td>
				<td><input type="text" name="testAnswer"></td>
			</tr>
			<tr>
				<td><input type="submit" value="添加"></td>
				<td><input type="reset" value="重置"></td>
			</tr>
		</table>
	</form>
</body>
</html>