<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Новости</title>
    <link href="resources/css/style_main.css" rel="stylesheet">
</head>
<body>

<#--<div class="sidenav">-->
<#--    <a href="menu">Главная</a>-->
<#--    <a href="edit">Редактировать</a>-->
<#--    <a href="logout">Выход</a>-->
<#--    <a href="delete">Удалить профиль</a>-->
<#--</div>-->

<h1>${user.name}</h1>

<form method="post">
    <label>Текст поста:
        <input type="text" name="text"/>
    </label>
    <input type="submit" value="Создать"/>
</form>

<#list posts as post>

    <p>${post.text}</p>

</#list>

</body>
</html>