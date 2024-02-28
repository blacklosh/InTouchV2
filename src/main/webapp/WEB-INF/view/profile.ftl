<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Профиль</title>
    <link href="resources/css/style_main.css" rel="stylesheet">
</head>
<body>

<#include "menu.ftl">

<div class="content">
    <#if user.avatarId??>
        <img src="download/${user.avatarId}" class="avatar">
    <#else>
        <img src="resources/avatar.png" class="avatar">
    </#if>
    <p>${user.name}</p>
    <p>${user.email}</p>

    <a href="edit">Редактировать</a>
    <a href="logout">Выход</a>
</div>
</body>
</html>