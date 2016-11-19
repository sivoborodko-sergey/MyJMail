<html>
<body>
<div class="content" align="center">
    <div class="box01">
        <form action="/" method="post" onsubmit="return    validateLogin('login','password','Ви не вказали логін або пароль!','Помилка!');">
            <p class="title">Вхід до системи</p>
            <dl>
                <dt><b>Логін:</b></dt><dd><input type="text" value="" name="login" id="login" maxlength="30"></dd>
                <dt><b>Пароль:</b></dt><dd><input type="password" value="" name="password" id="password" maxlength="30"></dd>
            </dl>
            <input name="submit" type="hidden" value="login">
            <input class="button02" name="submit" type="submit" value="Увійти"><br>
        </form>
    </div>
    <div class="box01_short">
        <p class="title">Ви ще не зареєстровані?</p>
        <p style="margin:0px;padding:0px;height:2px;font-size:1px">&nbsp;</p>
        <br>
        <a href="/register/">Попередня реєстрація</a><p></p>
    </div>
</div>
</body>
</html>