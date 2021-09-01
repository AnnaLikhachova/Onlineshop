<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>All Products page</title>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light mt-5 d-flex flex-row justify-content-around">
        <h3 class="nav-item p-2"><a href="/allproducts" class="nav-link text-dark">All Products</a></h3>
        <h3 class="nav-item p-2"><a href="/cart" class="nav-link text-warning">Cart</a></h3>
        <div>
            <form class="mb-0" action="/logout" method="GET">
                <input class="text-warning" type="submit" value="Logout"/>
            </form>
        </div>
        <div>
            <form class="mb-0" action="/user/allproducts/search" method="GET">
                <input type="text" name="search"/>
                <input class="text-warning" type="submit" value="Search"/>
            </form>
        </div>
    </nav>
<div class="container mt-5">
    <div class="container-fluid p-0 mt-2">
        <div class="row">
              <#if message??>
                    <h2 class="m-3">${message}</h2>
              </#if>
            <div class="col-xl-12">
                <div class="card">
                    <div class="card-header pb-0">
                        <h5 class="card-title m-2">All products</h5>
                    </div>
                    <div class="card-body">
                        <table class="table table-striped" style="width:100%">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Description</th>
                                <th>Date</th>
                                <th>Cart</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#if products??>
                               <#list products as product>
                               <tr>
                                   <td>${product.id}</td>
                                   <td>${product.name}</td>
                                   <td>${product.price}</td>
                                   <td>${product.description}</td>
                                   <td>${product.date}</td>
                                   <td>
                                       <span class="badge bg-warning p-1"><a href="/user/allproducts/addtocart/${product.id}"
                                                                             class="text-decoration-none text-reset m-1">Add to cart</a></span>
                                   </td>
                               </tr>
                               </#list>
                                </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>