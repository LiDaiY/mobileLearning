<%@page import="ujs.mlearn.entity.BbsTheme"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body
style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
	<form action="${pageContext.request.contextPath}/WebBbsServlet?action=modReply"
		method="post">
		<table>
			<tr>
				<td>问题标题：</td>
				<td>${bbsTheme.postTitle}</td>
			</tr>
			<tr>
				<td>问题内容：</td>
				<td width="500px">${bbsTheme.postContent}</td>
			</tr>
			<tr>
				<td>提问时间：</td>
				<td>${bbsTheme.postTime}</td>
			</tr>
			<tr>
				<td>我的回答：</td>
				<td><textarea rows="20" cols="80" name="reply">${replyPost.replyContent}</textarea></td>
			</tr>
			<tr>
				<td><input type="hidden" name="replyID" value="${replyPost.replyID}"></td>
				<td></td>
				<td><input type="submit" value="修改"></td>
				<td><input type="reset" value="重置"></td>
			</tr>

		</table>
	</form>
	
</body>
</html>