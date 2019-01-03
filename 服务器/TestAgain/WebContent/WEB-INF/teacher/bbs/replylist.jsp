<%@page import="ujs.mlearn.entity.ReplyPost"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
a {
	text-decoration: NONE
}
</style>
</head>
<body
	style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
	<script type="text/javascript">
		var i = 1
	</script>

	<table >
		<tr>
			<td>帖子标题</td>
			<td>操作</td>
		</tr>

		<c:forEach items="${bbsThemes}" var="bbs">
			<tr>
				<td><script type="text/javascript">
					document.write(i + ".  ");
					i++
				</script>${bbs.postTitle}</td>
				<td><a
					href="${pageContext.request.contextPath }/WebBbsServlet?action=findReplyDetail&postID=${bbs.postID}">查看回答</a></td>
				
			</tr>
		</c:forEach>
	</table>
</body>
</html>