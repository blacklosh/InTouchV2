<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Редактирование профиля</title>
    <link rel="stylesheet" href="${contextPath}/resources/css/style_main.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/edit.css">
</head>
<body>

<#include "menu.ftl">

<div class="content">
    <h2>Информация</h2>
    Ваш пол: <input type="radio" name="man"> Мужской </br><input type="radio" name="man"> Женский
    <p>Hey, Misha</p>

    <p>Letter, Misha</p>

    <form action="${contextPath}/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file">
        <input type="submit">
    </form>
</div>
</body>
</html>