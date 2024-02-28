<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Регистрация</title>
        <link rel="stylesheet" href="${contextPath}/resources/css/sign-in.css">
    </head>
    <body>

    <div style="color: red">
        ${err!''}
    </div>
    <form method="post" action="${contextPath}/signup">
        <fieldset>
            <legend>Регистрация</legend>
                <input name="name" type="text" placeholder="Имя" required> <br />
                <input name="email" type="email" placeholder="Email" required><br />
                <input name="password" type="password" placeholder="Пароль" required><br />
                <input type="submit" value="Зарегистрироваться">
        </fieldset>
        <a href="${contextPath}/signin">Вход</a>
    </form>
    </body>
</html>