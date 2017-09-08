<%@page import="cn.bestsec.dcms.platform.api.exception.ErrorCode"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
List<ErrorCode> errorCodes = Arrays.asList(ErrorCode.values());
pageContext.setAttribute("errorCodes", errorCodes);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>DCMS后台API接口错误码文档</title>
	<meta name="description" content="">
	<meta name="author" content="xuzewei">
</head>
<body>
	<div class="box-body">
		<h4>DCMS后台API接口错误码文档</h4>
		<h5>
			<ul>
				<c:forEach var="errorCode" items="${errorCodes}">
					<li>${errorCode.code} ${errorCode.description} ${errorCode.reason}</li>
				</c:forEach>
			</ul>
		</h5>
	</div>
</body>
</html>