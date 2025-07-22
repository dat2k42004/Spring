<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Dashboard - SB Admin</title>
    <script
      src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
      crossorigin="anonymous"
    ></script>
    <link href="/css/styles.css" rel="stylesheet" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link rel="stylesheet" href="/css/demo.css" />
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
      $(document).ready(() => {
        const avatarFile = $("#avatarFile");
        avatarFile.change(function (e) {
          const imgURL = URL.createObjectURL(e.target.files[0]);
          $("#avatarPreview").attr("src", imgURL);
        });
      });
    </script>
  </head>
  <body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
      <jsp:include page="../layout/sidebar.jsp" />
      <div id="layoutSidenav_content">
        <main>
          <div class="container-fluid px-4">
            <h1 class="mt-4">User Management</h1>
            <ol class="breadcrumb mb-4">
              <li class="breadcrump-item"><a href="/admin">DashBoard</a></li>
              /
              <li class="breadcrump-item active">
                <a href="/admin/product">Product</a>
              </li>
            </ol>
            <div class="container mt-5">
              <div class="row">
                <div class="col-md-6 col-12 mx-auto">
                  <h3>Update Product</h3>

                  <hr />
                  <form:form
                    method="post"
                    action="/admin/product/update"
                    modelAttribute="product"
                    class="row"
                    enctype="multipart/form-data"
                  >
                    <div class="mb-3" style="display: none">
                      <label class="form-label">Id: </label>
                      <form:input type="text" class="form-control" path="id" />
                    </div>
                    <div class="mb-3 col-12 col-md-6">
                      <label class="form-label">Name: </label>
                      <form:input
                        type="text"
                        class="form-control"
                        path="name"
                      />
                    </div>
                    <div class="mb-3 col-12 col-md-6">
                      <label class="form-label">Price: </label>
                      <form:input
                        type="number"
                        class="form-control"
                        path="price"
                      />
                    </div>
                    <div class="mb-3 col-12">
                      <label class="form-label">Detail Desc: </label>
                      <form:input
                        type="text"
                        class="form-control"
                        path="detailDesc"
                      />
                    </div>

                    <div class="mb-3 col-12 col-md-6">
                      <label class="form-label">Short Desc: </label>
                      <form:input
                        type="text"
                        class="form-control"
                        path="shortDesc"
                      />
                    </div>
                    <div class="mb-3 col-12 col-md-6">
                      <label class="form-label">Quantity: </label>
                      <form:input
                        type="number"
                        class="form-control"
                        path="quantity"
                      />
                    </div>
                    <div class="mb-3 col-12 col-md-6">
                      <label class="form-label">Image: </label>
                      <input
                        class="form-control"
                        type="file"
                        name="inputFile"
                        id="avatarFile"
                        accept=".jpg, .png, .jpeg"
                      />
                    </div>
                    <div class="mb-3 col-12 col-md-6">
                      <label class="form-label">Target: </label>
                      <form:select path="target" class="form-select">
                        <form:option value="Gaming">Gaming</form:option>
                        <form:option value="Office">Office</form:option>
                        <form:option value="Design">Design</form:option>
                        <form:option value="Thin">Thin</form:option>
                        <form:option value="Business">Business</form:option>
                      </form:select>
                    </div>
                    <div class="mb-3 col-12 col-md-6">
                      <label class="form-label">Factory: </label>
                      <form:select path="factory" class="form-select">
                        <form:option value="Apple">Apple</form:option>
                        <form:option value="Dell">Dell</form:option>
                        <form:option value="Msi">Msi</form:option>
                        <form:option value="Acer">Acer</form:option>
                        <form:option value="Asus">Asus</form:option>
                        <form:option value="Hp">Hp</form:option>
                        <form:option value="Surface">Surface</form:option>
                      </form:select>
                    </div>
                    <div class="col-12 mb-3">
                      <img
                        style="max-height: 250px"
                        src="/images/product/${product.image}"
                        alt="avatar preview"
                        id="avatarPreview"
                      />
                    </div>
                    <button type="submit" class="btn btn-primary">Save</button>
                  </form:form>
                </div>
              </div>
            </div>
          </div>
        </main>
        <jsp:include page="../layout/footer.jsp" />
      </div>
    </div>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
      crossorigin="anonymous"
    ></script>
    <script src="/js/scripts.js"></script>
  </body>
</html>
