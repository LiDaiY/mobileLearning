<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body  style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
<form action="${pageContext.request.contextPath}/WebNoticeServlet?action=udNotice" method="post">
<table>
<tr>
     <td>通知公告标题：</td>
     <td><input type="text" name="noticeTitle" value="${notice.noticeTitle}"/></td>
     <td><span>*</span></td>
</tr>
<tr>
<td>通知公告内容：</td>
<td><textarea rows="10" cols="40" name="noticeContent" id="noticeContent">${notice.noticeContent}</textarea>

 <td><span>*</span></td>
</tr>

<tr>
<td colspan="3" align="center">
<input type="submit" value="修改"/>
<input type="reset" />
</td>

</tr>

</table>
<input type="hidden" name="noticeID" value="${notice.noticeID}">
<input type="hidden" name="teacherID" value="${teacher.teacherID}">
</form>
</body>
</html>