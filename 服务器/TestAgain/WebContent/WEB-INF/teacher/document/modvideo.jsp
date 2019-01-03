<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body  style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
<form action="${pageContext.request.contextPath}/phvedio.do?action=udvp" method="post" enctype="multipart/form-data">
<table>
<tr>
     <td>视频图片标题：</td>
     <td><input type="text" name="vediophototitle" value="${vph.vediophototitle }"/></td>
     <td><span>*</span></td>
</tr>
<tr><td>
<input name="vediophotoid" value = "${vph.vediophotoid }" type="hidden">
<input type="radio" <c:if test="${vph.type==2}" >checked="true"</c:if> name="type" value="2">图片
<input type="radio" <c:if test="${vph.type==1}" >checked="true"</c:if> name="type" value="1">视频</td></tr>
<tr>
<td>重新上传：</td>
<td><input type="file" name = "vediophotourl"></td>
 <td><span>*</span></td>
</tr>

<tr>
<td colspan="3" align="center">
<input type="submit" value="修改"/>
<input type="reset" />
</td>
</tr>

</table>
</form>
</body>
</html>