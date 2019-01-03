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
			<td>学生姓名</td>
			<td>作业标题</td>
			<td>操作</td>
		</tr>

		<c:forEach items="${sHomeworks}" var="sWork">
			<tr>
				<td><script type="text/javascript">
					document.write(i + ".  ");
					i++
				</script>${sWork.username}</td>
				<td>${sWork.stuWorkTitle}</td>
				<td><a
					href="${pageContext.request.contextPath }/WebHomeworkServlet?action=downloadWork&shwID=${sWork.shwID}">&nbsp&nbsp&nbsp&nbsp下载</a></td>
			
			</tr>
		</c:forEach>
	</table>
</body>
</html>