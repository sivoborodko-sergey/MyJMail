<!DOCTYPE html>
<html>
<head>
    <meta charset="US-ASCII">
    <title>Login Page</title>
</head>
<body>
<form action="SendingServlet" method="post">
    ID: <%=request.getAttribute("id")%>
    <br>
    Username: <input type="text" name="login">
    <br>
    Message: <input type="text" name="message">
    <br>
    <input type="submit" value="Send">
</form>
</body>
</html>