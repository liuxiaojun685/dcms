<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cn.bestsec.dcms.platform.utils.apiGenerator.ModelMeta.FieldMeta"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.bestsec.dcms.platform.utils.apiGenerator.ApiMeta"%>
<%@page import="cn.bestsec.dcms.platform.utils.apiGenerator.ModelMeta"%>
<%@page import="cn.bestsec.dcms.platform.utils.apiGenerator.ApiParser"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/api/v1/";
%>
<%
ApiParser apiParser = new ApiParser();
apiParser.setRamlLocation(request.getServletContext().getRealPath("raml/api.raml"));
apiParser.setRootPackageName("cn.bestsec.dcms.platform.api");
apiParser.init();
apiParser.parseModels();
apiParser.parseApis();
pageContext.setAttribute("apiParser", apiParser);
%>
<%!
public ModelMeta getModelMeta(String type, ApiParser apiParser) {
    for (ModelMeta modelMeta : apiParser.getModelMetas()) {
        if (modelMeta.getClassName().equals(type)) {
            return modelMeta;
        }
    }
	return null;
}
public boolean alreadyExistInTypeTree(String type, Map<String, Integer> parentTypes, int level) {
    if ("String".equals(type) || "Integer".equals(type) || "Long".equals(type)) {
        return false;
    }
    if (type.startsWith("List<")) {
        String eType = type.substring(5, type.length() - 1);
        if (parentTypes.containsKey(eType) && level > parentTypes.get(eType).intValue()) {
            return true;
        }
        return false;
    }
    if (parentTypes.containsKey(type) && level > parentTypes.get(type).intValue()) {
        return true;
    }
	return false;
}
public void appendTypeTree(String type, Map<String, Integer> parentTypes, int level) {
    if ("String".equals(type) || "Integer".equals(type) || "Long".equals(type) || type.startsWith("List<")) {
        return;
    }
    if (parentTypes.containsKey(type)) {
        return;
    }
    parentTypes.put(type, level);
}
public String genRootObjectHtml(String javaObjectType, ApiParser apiParser) {
    Map<String, Integer> parentTypes = new HashMap<>();
    return genObjectHtml(0, "", null, javaObjectType, "", apiParser, parentTypes);
}
public String genObjectHtml(int level, String name, Boolean required, String javaObjectType, String description, ApiParser apiParser, Map<String, Integer> parentTypes) {
    if ("String".equals(javaObjectType) || "Integer".equals(javaObjectType) || "Long".equals(javaObjectType)) {
        return genRowHtml(level, name, required, javaObjectType, description);
    }
    if (javaObjectType.startsWith("List<")) {
        String eType = javaObjectType.substring(5, javaObjectType.length() - 1);
        if ("String".equals(eType) || "Integer".equals(eType) || "Long".equals(eType)) {
            return genRowHtml(level, name, required, "array[" + eType + "]", description);
        }
        appendTypeTree(eType, parentTypes, level);
        StringBuffer sb = new StringBuffer();
        sb.append(genRowHtml(level, name + " [", required, "array[" + eType + "]", description));
        sb.append(genObjectHtml(level + 1, "", null, eType, "", apiParser, parentTypes));
        sb.append(genRowHtml(level, "]", null, "", ""));
        return sb.toString();
    }
    StringBuffer sb = new StringBuffer();
    sb.append(genRowHtml(level, name + "{", null, "", ""));
    ModelMeta modelMeta = getModelMeta(javaObjectType, apiParser);
    appendTypeTree(javaObjectType, parentTypes, level);
    if (modelMeta != null && modelMeta.getFieldMetas() != null) {
	    for (FieldMeta fieldMeta : modelMeta.getFieldMetas()) {
	        String eType = fieldMeta.getFieldType();
	        if (alreadyExistInTypeTree(eType, parentTypes, level)) {
	            sb.append(genRowHtml(level + 1, fieldMeta.getFieldName(), fieldMeta.getRequired(), eType.replaceAll("<", "&lt;").replaceAll(">", "&gt;"), fieldMeta.getDescription()));
	        } else {
		        sb.append(genObjectHtml(level + 1, fieldMeta.getFieldName(), fieldMeta.getRequired(), fieldMeta.getFieldType(), fieldMeta.getDescription(), apiParser, parentTypes));
	        }
	    }
    }
    sb.append(genRowHtml(level, "}", null, "", ""));
    return sb.toString();
}
public String genRowHtml(int level, String name, Boolean required, String type, String description) {
	StringBuffer sb = new StringBuffer();
	sb.append("<div>");
	for (int i = 0; i < level; i++) {
		sb.append("  <span>&nbsp;&nbsp;</span>");
	}
	String sRequired = "";
	if (required != null && required.booleanValue()) {
	    sRequired = "必须";
	}
	sb.append(String.format("  <span style=\"color:#00f\">%s</span>", name == null ? "" : name));
	sb.append(String.format("  <span style=\"color:green\">%s</span>", type == null ? "" : type));
	sb.append(String.format("  <span style=\"color:red\">%s</span>", sRequired));
	sb.append(String.format("  <span style=\"color:gray\">%s</span>", description == null ? "" : description));
	sb.append("</div>");
	return sb.toString();
}
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>DCMS后台API接口文档</title>
	<meta name="description" content="">
	<meta name="author" content="xuzewei">
	<style type="text/css">
	.alert {
	  padding: 15px;
	  margin-bottom: 20px;
	  border: 1px solid transparent;
	  border-radius: 4px;
	}
	.alert h4 {
	  margin-top: 0;
	  color: inherit;
	}
	.alert .alert-link {
	  font-weight: bold;
	}
	.alert > p,
	.alert > ul {
	  margin-bottom: 0;
	}
	.alert > p + p {
	  margin-top: 5px;
	}
	.alert-dismissable {
	  padding-right: 35px;
	}
	.alert-dismissable .close {
	  position: relative;
	  top: -2px;
	  right: -21px;
	  color: inherit;
	}
	.alert-success {
	  background-color: #dff0d8;
	  border-color: #d6e9c6;
	  color: #468847;
	}
	.alert-success hr {
	  border-top-color: #c9e2b3;
	}
	.alert-success .alert-link {
	  color: #356635;
	}
	.alert-info {
	  background-color: #d9edf7;
	  border-color: #bce8f1;
	  color: #3a87ad;
	}
	.alert-info hr {
	  border-top-color: #a6e1ec;
	}
	.alert-info .alert-link {
	  color: #2d6987;
	}
	.alert-warning {
	  background-color: #fcf8e3;
	  border-color: #fbeed5;
	  color: #c09853;
	}
	.alert-warning hr {
	  border-top-color: #f8e5be;
	}
	.alert-warning .alert-link {
	  color: #a47e3c;
	}
	.alert-danger {
	  background-color: #f2dede;
	  border-color: #eed3d7;
	  color: #b94a48;
	}
	.alert-danger hr {
	  border-top-color: #e6c1c7;
	}
	.alert-danger .alert-link {
	  color: #953b39;
	}
	</style>
</head>
<body>
	<div class="box-body">
		<h4>DCMS后台API接口文档</h4>
		<h5>
			<ul>
				<li><a href="errorCode.jsp">全局统一错误码定义</a></li>
				<li>小提示：利用浏览器搜索功能Ctrl+F可快速定位API接口，短关键字搜索范围大，长关键字搜索结果精确，接口路径、接口名、接口描述、参数名、参数描述、响应字段名、响应字段描述等都可作为搜索关键字</li>
				<li>每个接口都已定义快速跳转书签，点击超链接即可跳转，跳转后URL末尾会添加路径名做区分，可添加该URL到浏览器书签，以便下次打开时自动定位到该接口</li>
				<li></li>
				<li>所有上传文件接口使用POST请求，请求体编码成JSON字符串放到名称为"body"的URL参数中，附件参数名称为"file"。</li>
				<li>所有下载文件接口使用GET请求，请求体分别放到URL参数中。</li>
			</ul>
		</h5>
		<div class="alert alert-block alert-warning fade in">
			<p>共<%=apiParser.getApiMetas().size()%>个接口，API基础路径<%=basePath%></p>
			<ul>
				<c:forEach var="apiMeta" items="${apiParser.apiMetas}">
					<li style="color:#333"><a href="#${apiMeta.apiGroupName}/${apiMeta.apiName}"> POST ${apiMeta.apiGroupName}/${apiMeta.apiName} ${apiMeta.displayName}</a></li>
				</c:forEach>
			</ul>
		</div>
		<div class="divide-20"></div>
	</div>
	<c:forEach var="apiMeta" items="${apiParser.apiMetas}">
		<div class="alert alert-block alert-info fade in">
			<a name="${apiMeta.apiGroupName}/${apiMeta.apiName}"></a>
			<h4 style="color:#333">POST ${apiMeta.apiGroupName}/${apiMeta.apiName} ${apiMeta.displayName}</h4>
			<h5 style="color:#333">${apiMeta.description}</h5>
			<c:if test="${not empty apiMeta.requestName}">
				<span style="color:#13C20F">请求参数</span>
				<%
				ApiMeta apiMeta = (ApiMeta) pageContext.getAttribute("apiMeta");
				out.print(genRootObjectHtml(apiMeta.getRequestName(), apiParser));
				%>
			</c:if>
			<c:if test="${not empty apiMeta.responseName}">
				<span style="color:#13C20F">响应</span>
				<%
				ApiMeta apiMeta = (ApiMeta) pageContext.getAttribute("apiMeta");
				out.print(genRootObjectHtml(apiMeta.getResponseName(), apiParser));
				%>
			</c:if>
		</div>
	</c:forEach>
</body>
</html>