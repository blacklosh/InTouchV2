<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Профиль</title>

    <link href="resources/css/style.css" rel="stylesheet">
    <link href="resources/css/menu.css" rel="stylesheet">
    <link href="resources/css/profile.css" rel="stylesheet">

</head>
<body>

<div class="container">

    <#include "menu.ftl">

    <div class="center-content">
        <div class="container">
            <div class="title">Профиль</div>
            <div>Привет, ${user.name}</div>
            <div>Email: ${user.email}</div>
            <a href="logout">Выход</a>
            <a href="delete">Удалить профиль</a>
            <div id="profile" class="white-container">
                <div class="user-info-text">
                    <div class="user-info"></div>
                    <div class="user-info"></div>
                    <div class="user-info"></div>
                    <div class="user-info"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>