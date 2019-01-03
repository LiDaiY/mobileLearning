<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
  <script type="text/javascript">
  	function fun1(){
  		//1 创建出想要添加的行
  		var tr = document.createElement("tr");
  		tr.innerHTML = "<td></td><td><input type='file' name='photo' /></td><td><input type='button' value='删除' onclick='fun2(this)'  /></td>";
  		//2 找到表格
  		var table = document.getElementById("one");
  		//3 找到表格最后一行
  		var lastRow = table.rows[table.rows.length-1-1];
  		//4 insertBefore
  		lastRow.parentNode.insertBefore(tr, lastRow);
  	}
  	//参数: 要删除行中的删除按钮对象
  function fun2(obj){
	  obj.parentNode.parentNode.parentNode.removeChild(obj.parentNode.parentNode);
  }
  </script>
<body style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg">
	<form
		action="${pageContext.request.contextPath}/WebMaterialServlet?action=addVideo"
		method="post" enctype="multipart/form-data">
		<table id="one">

			<tr>
				<td>课程名称：</td>
				<td><select name="courseName">
					<c:forEach items="${courses}" var="course">
						<option>${course.courseName}</option>
					</c:forEach>
				</select></td>
			</tr>


			<tr>
				<td>上传视频：</td>
				<td><input type="file" name="videoUrl"></td>
				<td><input type="button" value="添加" onclick="fun1()"  /></td>
			</tr>
			<tr>
				<td colspan="3" align="center"><input type="submit" value="提交" />
					<input type="reset" /></td>
			</tr>
			<tr>
				<td></td>
				<td><font color="red" >${requestScope.msg}</font></td>
			</tr>
		</table>
	</form>
</body>
</html>