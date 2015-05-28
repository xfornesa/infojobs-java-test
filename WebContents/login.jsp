<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<%@ include file="menu.jsp" %>
<h1>Login page!!</h1>
<div>
    <p>Login with user1 with roles PAG_1</p>
    <form action="login" method=POST id="user1">
        <input type="hidden" name="user" value="user1withRolePAG_1">
        <input type="submit" value="Login">
    </form>
</div>

<div>
    <p>Login with user2 with roles PAG_2</p>
    <form action="login" method=POST id="user2">
        <input type="hidden" name="user" value="user2withRolePAG_2">
        <input type="submit" value="Login">
    </form>
</div>

<div>
    <p>Login with user3 with roles PAG_3</p>
    <form action="login" method=POST id="user3">
        <input type="hidden" name="user" value="user3withRolePAG_3">
        <input type="submit" value="Login">
    </form>
</div>

<div>
    <p>Login with a user which has roles PAG_2 and PAG_3</p>
    <form action="login" method=POST id="user4">
        <input type="hidden" name="user" value="userwithRolesPAG_2_PAG_3">
        <input type="submit" value="Login">
    </form>
</div>
</body>
</html>