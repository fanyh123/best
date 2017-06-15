<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test</title>
</head>
<body>
    <h4 id="${as}">"test ${as}"</h4>
    <input value="${zs}">
    <form action="../d/file" method="post" enctype="multipart/form-data">
      <input type="file" name="file">
      <input type="submit" name="上传">
    </form>    
</body>
</html>