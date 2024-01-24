<http>
    <head>
        <meta charset="utf-8">
        <title>Регистрация</title>
        <link rel="stylesheet" href="resources/css/sign-in.css">
    </head>
    <body>

    <div style="color: red">
        ${err!''}
    </div>
    <form method="post" action="signup">
        <input name="name" type="text" placeholder="Name">
        <input name="email" type="email" placeholder="Email">
        <input name="password" type="password" placeholder="Password">
        <input type="submit" value="knopka">

    </form>
    </body>
</http>