<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Профиль</title>
    <link href="resources/css/style_main.css" rel="stylesheet">
</head>
<body>

<div class="sidenav">
    <a href="menu">Главная</a>
    <a href="edit">Редактировать</a>
    <a href="logout">Выход</a>
    <a href="delete">Удалить профиль</a>
</div>
<div class="content">
    <#if user.avatarId??>
        <img src="download/${user.avatarId}" width="400" height="250">
    <#else>
        <img src="resources/kkkkk.jpg" width="400" height="250">
    </#if>
    <p>${user.name}</p>
    <p>${user.email}</p>
</div>
</body>
</html>