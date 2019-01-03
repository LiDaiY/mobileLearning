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
<body style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
	<script type="text/javascript">
		var i = 1;
	</script>
	<table>
		<tr>
			<td>通知公告标题：</td>
			<td>课程名称</td>
			<td>操作</td>
		</tr>

		<c:forEach items="${notices}" var="notice">
			<tr>
				<td><script type="text/javascript">
					document.write(i + ".  ");
					i++
				</script>${notice.noticeTitle}</td>
				<td>${notice.courseName}</td>
				<td><a
					href="${pageContext.request.contextPath }/WebNoticeServlet?action=findNotice&noticeID=${notice.noticeID}">编辑</a></td>
				<td><a
					href="${pageContext.request.contextPath }/WebNoticeServlet?action=delNotice&noticeID=${notice.noticeID}&teacherID=${teacherID}">删除</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>