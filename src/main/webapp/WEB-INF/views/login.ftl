<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Login Page</title>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light mt-5 d-flex flex-row justify-content-around">
        <h3 class="nav-item p-2"><a href="/main" class="nav-link text-warning">Main Page</a></h3>
        <h3 class="nav-item p-2"><a href="/login" class="nav-link text-dark">Login</a></h3>
    </nav>
</div>
<div class="container mt-5">
    <#if message??>
        <h2 class="m-3">${message}</h2>
    </#if>
    <form action="/login" method="POST">
        <div class="d-flex flex-column align-items-center">
            <div class="row w-100 p-2 d-flex flex-row">
                <h5 class="col-4">Login:</h5>
                <input class="col-4" type="text" name="login"/>
            </div>
            <div class="row w-100 p-2 d-flex flex-row">
                <h5 class="col-4">Password:</h5>
                <input class="col-4" type="password" name="password"/>
            </div>
            <div class="row w-100 p-2 d-flex flex-row justify-content-center">
                <input class="col-2 bg-danger m-4" type="submit" value="Login">
            </div>
        </div>
    </form>
</div>
</body>
</html>