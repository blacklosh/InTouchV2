<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Новости</title>
    <link href="${contextPath}/resources/css/style_main.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/post.css" rel="stylesheet">
</head>
<body>

<#include "menu.ftl">

<div class="content0">
    <div class="content1">
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
                    <#if post.author.avatarId??>
                        <img src="${contextPath}/download/${post.author.avatarId}" class="avatar_small">
                    <#else>
                        <img src="${contextPath}/resources/avatar.png" class="avatar_small">
                    </#if>
                    <div class="info">
                        <div class="name">${post.author.name}</div>
                        <div class="date">${post.creationDate?datetime.iso?string["yyyy-MM-dd HH:mm"]}</div>
                    </div>
                </div>
                <a class="post-text" href="${contextPath}/post/${post.id}">${post.text}</a>
            </div>
        </#list>
    </div>
</body>
</html>