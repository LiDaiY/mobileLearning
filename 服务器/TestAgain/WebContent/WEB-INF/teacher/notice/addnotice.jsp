<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

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
<body style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg">
<% 
CourseDao cou = new CourseDaoImpl();
Teacher teacher=(Teacher) request.getSession().getAttribute("teacher");
List<Course> courses = cou.findMyCourse(teacher.getTeacherID());
%>
	<form
		action="${pageContext.request.contextPath}/WebNoticeServlet?action=addNotice"
		method="post">
		<table>
			<tr>
				<td>通知公告标题：</td>
				<td><input type="text" name="noticeTitle" /></td>
				<td><span>*</span></td>
			</tr>
			<tr>
				<td>课程名称：</td>
				<td><select name="courseName">
						<%for(int i=0;i<courses.size();i++){%>
						<option><%=courses.get(i).getCourseName()%></option>
						<%}%>
				</select></td>
			</tr>
			<tr>
				<td>通知公告内容：</td>
				<td><textarea rows="10" cols="40" name="noticeContent"
						id="editor1"></textarea>
				<td><span>*</span></td>
			</tr>

			<tr>
				<td><input type="hidden" name="teacherID"
					value="${teacher.teacherID}"></td>
			</tr>
			<tr>
				<td colspan="3" align="center"><input type="submit" value="提交" />
					<input type="reset" /></td>

			</tr>

		</table>
	</form>
</body>
</html>