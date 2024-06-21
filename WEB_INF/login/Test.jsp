<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% int x=5; %>
<%! int x=7; %>
<%! int getX(){return x;}%>
<%=x%><br/>
<%=getX()%>
</body>
</html>
