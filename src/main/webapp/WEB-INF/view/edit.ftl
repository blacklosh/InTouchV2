<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Редактирование профиля</title>
    <link rel="stylesheet" href="edit.css">
    <link rel="stylesheet" href="style_main.css">
</head>
<body>
<h1>Профиль</h1>
<div class="sidenav">
<a href="profile.ftl">Профиль</a>
<a href="menu.ftl">Главная</a>
    Ваш пол: <input type="radio" name="man"> Мужской <input type="radio" name="man" checked> Женский
    <br>
    Месяц и год рождения:
    <br>
    <select>
        <option> Январь </option>
    </select>
    <select>
        <option>1990</option>
    </select>
    <br>
    Знакомые технологии:
    <br>
    <select size="4">
        <option>HTML</option>
        <option selected>CSS</option>
        <option>JavaScript</option>
        <option>Node.js</option>
    </select>
    <br>
    Фото: <input type="file">
    <br>
    <input type="submit" value="Сохранить">
</div>
</body>
</html>