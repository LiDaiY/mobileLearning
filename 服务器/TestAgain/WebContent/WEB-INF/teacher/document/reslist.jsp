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
			<td>资源名称</td>
			<td>&nbsp&nbsp上传时间</td>
			<td>&nbsp&nbsp&nbsp&nbsp操作</td>
		</tr>

		<c:forEach items="${materials}" var="material">
			<tr>
				<td><script type="text/javascript">
					document.write(i + ".  ");
					i++
				</script>${material.resTitle}</td>
				<td>&nbsp&nbsp${material.publishTime}</td>
				<td><a
					href="${pageContext.request.contextPath }/WebMaterialServlet?action=downloadRes&resID=${material.resID}">&nbsp&nbsp&nbsp&nbsp下载资源</a></td>
				<td><a
					href="${pageContext.request.contextPath }/WebMaterialServlet?action=deleteRes&resID=${material.resID}">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp删除资源</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>