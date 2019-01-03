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
	<form action="${pageContext.request.contextPath}/WebTestServlet?action=upTest"
		method="post">
		<table>
			<tr>
				<td>试题题目：</td>
				<td><input name="testContent" value="${test.testContent }" style="width:500px"></td>
			</tr>
			<tr>
				<td>试题类型：</td>
				<td><select name="testtype">
						<option value="1">单选</option>
						<option value="2">多选</option>
				</select></td>

			</tr>
			<tr>
				<td>试题选项：</td>
				<td>A:<input type="text" name="testOptionA" value=${testOptionA }> 
					B:<input type="text" name="testOptionB" value=${testOptionB }> 
					C:<input type="text" name="testOptionC" value=${testOptionC }> 
					D:<input type="text" name="testOptionD" value=${testOptionD }>
				</td>
			</tr>
			<tr>
				<td>试题答案：</td>
				<td><input type="text" name="testAnswer" value=${testAnswer }></td>
			</tr>
			<tr>
				<td><input type="hidden" name="testID" value="${test.testID }"></td>
				<td><input type="submit" value="修改"></td>
				<td><input type="reset" value="重置"></td>
			</tr>

		</table>
	</form>
</body>
</html>