<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
a {
	text-decoration: NONE
}
</style>
<title>Insert title here</title>

</head>
<body
	style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
	<form 
action="${pageContext.request.contextPath}/WebHomeworkServlet?action=modHomework"
		method="post">
	<script type="text/javascript">
		var i = 1;
	</script>
	<table>
				<tr>
				<td>作业标题：</td>
				<td><input name="hwTitle" style="width:500px" value="${homework.hwTitle}"></td>
			</tr>
		<tr>
			<td>详细要求</td>
			<td><textarea rows="20" cols="80" name="hwContent">${homework.hwContent}</textarea></td>
		</tr>
		<tr>
			<td><input type="hidden" name="hwID" value="${homework.hwID}"></td>
			<td><input type="submit" value="重新提交" /> </td>
			<td><input type="hidden" name="courseID" value="${homework.courseID}"></td>
		</tr>
	</table>
</form>

</body>
</html>