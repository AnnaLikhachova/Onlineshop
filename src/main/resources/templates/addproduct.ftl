<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Add product Page</title>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light mt-5 d-flex flex-row justify-content-around">
        <h3 class="nav-item p-2"><a href="/admin" class="nav-link text-warning">Admin Page</a></h3>
        <h3 class="nav-item p-2"><a href="/addproduct" class="nav-link text-dark">Add Product</a></h3>
        <div>
            <form class="mb-0" action="/logout" method="GET">
                <input class="text-warning" type="submit" value="Logout"/>
            </form>
        </div>
    </nav>
</div>
<div class="container mt-5">
    <form action="/products/add" method="POST">
        <div class="d-flex flex-column align-items-center">
            <div class="row w-100 p-2 d-flex flex-row">
                <h5 class="col-4">Product name:</h5>
                <input class="col-4" type="text" name="name"/>
            </div>
            <div class="row w-100 p-2 d-flex flex-row">
                <h5 class="col-4">Product price:</h5>
                <input class="col-4" type="number" name="price"/>
            </div>
            <div class="row w-100 p-2 d-flex flex-row">
                <h5 class="col-4">Product description: </h5>
                <input class="col-4" type="text" name="description"/>
            </div>
            <div class="row w-100 p-2 d-flex flex-row">
                <h5 class="col-4">Date: </h5>
                <input class="col-4" type="date" name="date"/>
            </div>
            <div class="row w-100 p-2 d-flex flex-row justify-content-center">
                <input class="col-2 bg-danger m-4" type="submit" value="ADD">
            </div>
        </div>
    </form>
</div>
</body>
</html>