<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<p> <h3> Registration page </h3> </p>
<form action="users/create" method="post">
    <p> First Name <input name="firstName" type="text"/></p>
    <p> Last Name <input name="lastName" type="text"/></p>
    <p> Middle Name <input name="middleName" type="text"/></p>
    <p> Login <input name="login" type="text"/></p>
    <p> Password <input name="password" type="password"></p>
    <input value="Sing Up" type="submit">
</form>
</body>
</html>
