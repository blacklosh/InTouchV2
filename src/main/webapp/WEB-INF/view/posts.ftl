<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Новости</title>
    <link href="resources/css/style_main.css" rel="stylesheet">
    <link href="resources/css/post.css" rel="stylesheet">
</head>
<body>

<div class="sidenav">
    <a href="menu">Главная</a>
    <a href="edit">Редактировать</a>
    <a href="logout">Выход</a>
    <a href="delete">Удалить профиль</a>
</div>
<h1>${user.name}</h1>
<div class="content">
    <form method="post">
        <label>
            <textarea type="text" name="text" placeholder="Напишите сообщение..."></textarea>
        </label>
            <input type="submit" value="Отправить"/>
    </form>
    <#list posts as post>
        <b>${post.author.name} ${post.creationDate?datetime.iso?string["yyyy-MM-dd HH:mm"]}</b>
        <p>${post.text}</p>
    </#list>
</div>
</body>
</html>