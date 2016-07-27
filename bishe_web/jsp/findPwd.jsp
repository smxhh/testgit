<%--
  Created by IntelliJ IDEA.
  User: wangxingjie
  Date: 2016/6/5
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form name="input" action="changePwd.jsp" method="get">
    身份证号： <input type="text" name="cerdNo" id="cerdNo"/><br/>
    手机号: <input type="text" name="mobile" id="mobile"/>
    <button type="button" id="getcodes">获取验证码</button>
    <br/>
    验证码: <input type="text" name="codes" id="codes"/><br/>
    <button type="button" id="getpwd">确定</button>
</form>
</body>
</html>
