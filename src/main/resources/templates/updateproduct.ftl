<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Update Product Page</title>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light mt-5 d-flex flex-row justify-content-around">
        <h3 class="nav-item p-2"><a href="/admin" class="nav-link text-warning">All Products</a></h3>
        <h3 class="nav-item p-2"><a href="/products/addproduct" class="nav-link text-warning">Add Product</a></h3>
        <div>
            <form class="mb-0" action="/logout" method="GET">
                <input class="text-warning" type="submit" value="Logout"/>
            </form>
        </div>
    </nav>
</div>
<div class="container mt-5">
    <form action="/products/updateproduct" method="POST">
        <input hidden="text" value=${product.id} name="id"/>
        <div class="d-flex flex-column align-items-center">
            <div class="row w-100 p-2 d-flex flex-row">
                <h5 class="col-4">Product name:</h5>
                <input class="col-4" type="text" value=${product.name} name="name"/>
            </div>
            <div class="row w-100 p-2 d-flex flex-row">
                <h5 class="col-4">Product price:</h5>
                <input class="col-4" type="number" value=${product.price} name="price"/>
            </div>
            <div class="row w-100 p-2 d-flex flex-row">
                <h5 class="col-4">Product description: </h5>
                <input class="col-4" type="text" value=${product.description} name="description"/>
            </div>
            <div class="row w-100 p-2 d-flex flex-row">
                <h5 class="col-4">Date: </h5>
                <input class="col-4" type="date" value=${product.date} name="date"/>
            </div>
            <div class="row w-100 p-2 d-flex flex-row justify-content-center">
                <input class="col-2 bg-danger m-4" type="submit" value="UPDATE">
            </div>
        </div>
    </form>
</div>
</body>
</html>