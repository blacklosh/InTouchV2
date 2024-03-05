<#-- @ftlvariable name="author" type="intouch.dto.UserDto" -->
<#-- @ftlvariable name="user" type="intouch.dto.UserDto" -->
<#-- @ftlvariable name="posts" type="java.util.List<intouch.dto.PostDto>" -->
<#-- @ftlvariable name="contextPath" type="java.lang.String" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${author.name}</title>
    <link href="${contextPath}/resources/css/style_main.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/post.css" rel="stylesheet">
</head>
<body>
<div class="content0">
    <#include "menu.ftl">
    <div class="content1">
        <#if author.avatarId??>
            <img src="${contextPath}/download/${author.avatarId}" class="avatar" alt="AVATAR">
        <#else>
            <img src="${contextPath}/resources/avatar.png" class="avatar" alt="AVATAR">
        </#if>
        <p class="text">${author.name}</p>
        <p class="text">${author.email}</p>

        <#list posts as post>
            <div class="post">
                <div class="userInfo">
                    <#if post.author.avatarId??>
                        <img src="${contextPath}/download/${post.author.avatarId}" class="avatar_small">
                    <#else>
                        <img src="${contextPath}/resources/avatar.png" class="avatar_small">
                    </#if>
                    <div class="info">
                        <a class="name" href="${contextPath}/profile/${post.author.id}">${post.author.name}</a>
                        <div class="date">${post.creationDate?datetime.iso?string["yyyy-MM-dd HH:mm"]}</div>
                    </div>
                </div>
                <a class="post-text" href="${contextPath}/post/${post.id}">${post.text}</a>
            </div>
        </#list>
    </div>
</div>
</body>
</html>