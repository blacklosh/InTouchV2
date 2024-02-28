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

<#include "menu.ftl">

<h1>${user.name}</h1>
<div class="content">
    <div class="message">
        <form method="post">
            <label>
                <textarea type="text" name="text" placeholder="Напишите сообщение..."></textarea>
            </label>
            <input type="submit" value="Отправить"/>
        </form>
    </div>
    <#list posts as post>
        <div class="post">
            <div class="userInfo">
                <div class="avatar">
                    <#if post.author.avatarId??>
                        <img src="download/${post.author.avatarId}" class="avatar_small">
                    <#else>
                        <img src="resources/avatar.png" class="avatar_small">
                    </#if>
                    <div class="info">
                        <p>${post.author.name}<p/>
                        <p>${post.creationDate?datetime.iso?string["yyyy-MM-dd HH:mm"]}</p>
                    </div>
                </div>

            </div>
            <div class="text">
                <p>${post.text}</p>
            </div>
        </div>
    </#list>
</div>
</body>
</html>