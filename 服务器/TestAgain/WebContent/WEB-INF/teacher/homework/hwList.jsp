<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<script type="text/javascript">
		var i = 1;
	</script>
	<table>
		<tr>
			<td>作业标题</td>
			<td>布置时间</td>
			<td>操作</td>
		</tr>
		<c:forEach items="${hList}" var="homework">
			<tr>
				<td><script type="text/javascript">
					document.write(i + ".  ");
					i++
					</script>
					<input style="width:500px" value="${homework.hwTitle}" disabled="disabled"/>
					</td>
				<td>${homework.publishTime}</td>
				<td><a
					href="${pageContext.request.contextPath }/WebHomeworkServlet?action=findHwDetail&hwID=${homework.hwID}">编辑作业</a></td>
				<td><a
					href="${pageContext.request.contextPath }/WebHomeworkServlet?action=findStuHw&hwID=${homework.hwID}">&nbsp&nbsp&nbsp查看学生作业</a></td>
			</tr>
		</c:forEach>
	</table>


</body>
</html>