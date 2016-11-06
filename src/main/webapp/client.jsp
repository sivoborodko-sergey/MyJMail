<!DOCTYPE html>
<html>
<head>
    <meta charset="US-ASCII">
    <title>Login Page</title>
</head>
<body>
    ID: <%=request.getAttribute("id")%>
    Username: <%=request.getAttribute("login")%>
    <br>
    <a href="/SendingServlet">Sending message</a><p></p>
    <a href="/ReceivedMessageServlet">Received</a><p></p>
    <a href="/SendMessageServlet">Send</a><p></p>

</body>
</html>