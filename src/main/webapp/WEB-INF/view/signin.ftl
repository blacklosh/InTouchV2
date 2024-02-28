<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
    <link rel="stylesheet" href="${contextPath}/resources/css/sign-in.css">
</head>
<body>
<div style="color: red">${err!''}</div>
<div class="container">

    <form class="form-center-content" method="post" action="${contextPath}/signin">
        <fieldset>
            <legend>Вход</legend>
            <label>
                <input class="form-control" name="email" type="email" placeholder="Email"><br/>
            </label>
            <label>
                <input class="form-control" name="password" type="password" placeholder="Пароль"><br/>
            </label>
            <#if errorMessage??>
                <div class="error_message">${errorMessage}</div>
            </#if>
            <input class="login-button" type="submit">
        </fieldset>
        <a href="${contextPath}/signup">Регистрация</a>
    </form>
</div>
</body>
</html>