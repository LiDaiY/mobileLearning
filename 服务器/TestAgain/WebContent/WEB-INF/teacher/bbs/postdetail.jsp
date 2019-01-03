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
<%BbsTheme bbsTheme=(BbsTheme)request.getAttribute("bbsTheme"); %>
	<form action="${pageContext.request.contextPath}/WebBbsServlet?action=sendReply"
		method="post">
		<table>
			<tr>
				<td>问题标题：</td>
				<td>${bbsTheme.postTitle}</td>
	
			</tr>
			<tr>
				<td>问题内容：</td>
				<td>${bbsTheme.postContent}
			</tr>
			<tr>
	
				<td><input type="hidden" name="postID" value="${bbsTheme.postID}"></td>
				
			</tr>
			<tr>
				<td>提问者：</td>
				<td>${bbsTheme.studentName}</td>
	
			</tr>
			<tr>
				<td>问题提出时间:</td>
				<td>${bbsTheme.postTime}</td>
			</tr>
			<%if(bbsTheme.getState()==0){ %>
			<tr>
			
				<td>编写回答</td>
				<td><textarea rows="20" cols="80" name="reply"></textarea></td>
			</tr>
			<tr>
				<td> </td>
				<td><input type="submit" value="提交" style="margin-left:460px;width:120px"/> 
				</td>
			</tr>
			<%}else if(bbsTheme.getState()==1){ %>
			<tr>
				<td> </td>
				<td>此问题已回答，请前往我的回答查看</td>
			</tr>
			<%} %>
		</table>
	</form>
	
</body>
</html>