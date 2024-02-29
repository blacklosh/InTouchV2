<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${contextPath}/resources/css/style_main.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/post.css" rel="stylesheet">
</head>
<body>

<#include "menu.ftl">

<div class="content">
    <div class="post">
        <div class="userInfo">
            <#if post.author.avatarId??>
                <img src="${contextPath}/download/${post.author.avatarId}" class="avatar_small">
            <#else>
                <img src="${contextPath}/resources/avatar.png" class="avatar_small">
            </#if>
            <div class="info">
                <p class="name">${post.author.name}<p/>
                <p class="date">${post.creationDate?datetime.iso?string["yyyy-MM-dd HH:mm"]}</p>
            </div>
        </div>
        <div class="text">
            <p>${post.text}</p>
        </div>
    </div>
</div>

</body>
</html>