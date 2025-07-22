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
                <a href="/admin/order">Order</a>
              </li>
            </ol>
            <div class="container mt-5">
              <div class="row">
                <div class="col-md-6 col-12 mx-auto">
                  <h3>Update Order</h3>
                  <div>
                    <p>Order ID: ${order.id}</p>
                    <p>Total Price: $${order.totalPrice}</p>
                  </div>
                  <hr />
                  <form:form
                    method="post"
                    action="/admin/order/update"
                    modelAttribute="order"
                    class="row"
                    enctype="multipart/form-data"
                  >
                    <div class="mb-3" style="display: none">
                      <label class="form-label">Id: </label>
                      <form:input type="text" class="form-control" path="id" />
                    </div>

                    <div class="mb-3 col-12 col-md-6">
                      <label class="form-label">User: </label>
                      <form:input
                        type="text"
                        class="form-control"
                        path="user.fullName"
                        disabled="true"
                      />
                    </div>

                    <div class="mb-3 col-12 col-md-6">
                      <label class="form-label">Status: </label>
                      <form:select path="status" class="form-select">
                        <form:option value="PENDING">PENDING</form:option>
                        <form:option value="SHIPPING">SHIPPING</form:option>
                        <form:option value="COMPLETE">COMPLETE</form:option>
                        <form:option value="CANCEL">CANCEL</form:option>
                      </form:select>
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
