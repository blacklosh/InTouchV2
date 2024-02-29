<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Профиль</title>
    <link href="${contextPath}/resources/css/style_main.css" rel="stylesheet">
</head>
<body>
<div class="content0">
    <#include "menu.ftl">
    <div class="content1">
        <#if user.avatarId??>
            <img src="${contextPath}/download/${user.avatarId}" class="avatar">
        <#else>
            <img src="${contextPath}/resources/avatar.png" class="avatar">
        </#if>
        <p class="text">${user.name}</p>
        <p class="text">${user.email}</p>
        <a class="link" href="${contextPath}/edit">Редактировать</a>
        <a class="link" href="${contextPath}/logout">Выход</a>
    </div>
</div>
</body>
</html>