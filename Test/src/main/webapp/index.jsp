<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = path + "/pages";
%>
<html>
<head>
<c:set var="path" value="<%=path%>"></c:set>
</head>
<body>
<h2>Hello World!</h2>
    <ul>
      <li>
        <input type="hidden" name="RCaptchaKey" id="RCaptchaKey" value="${RCaptchaKey}"/>
		<input type="text" placeholder="请输入图形验证码" data-role="none" name="captcha" id="captcha" style="width: 50% ;padding-left: 0.65rem;">
		<img id="imgCap" style="  width: 4.5rem; height: 2rem;float: right;margin-right: -0.9rem;" src="<%=path %>/captcha.svl?RCaptchaKey=${RCaptchaKey}" 
		onclick="this.src='<%=request.getContextPath() %>/captcha.svl?RCaptchaKey=${RCaptchaKey}&d='+new Date()*1" valign="bottom" alt="点击更新" title="点击更新" />				
      </li>
    </ul>
</body>
</html>
