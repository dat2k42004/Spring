<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
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
    <link href="/css/styles.css" rel="stylesheet" />
    <script
      src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
      crossorigin="anonymous"
    ></script>
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
            <h1 class="mt-4">Product Management</h1>
            <ol class="breadcrumb mb-4">
              <li class="breadcrump-item"><a href="/admin">DashBoard</a></li>
              /
              <li class="breadcrump-item active">
                <a href="/admin/order">Order</a>
              </li>
            </ol>
            <div class="mt-5">
              <div class="row">
                <div class="col-12 mx-auto">
                  <div class="d-flex justify-content-between">
                    <h3>Table Orders</h3>
                  </div>
                  <hr />
                  <table class="table table-hover table-striped">
                    <thead>
                      <tr>
                        <th scope="col">ID</th>
                        <th scope="col">User</th>
                        <th scope="col">Total Price</th>
                        <th scope="col">Status</th>
                        <th scope="col">Action</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${orders}" var="order">
                        <tr>
                          <td>${order.id}</td>
                          <td>${order.user.email}</td>
                          <td>$${order.totalPrice}</td>
                          <td>${order.status}</td>
                          <td>
                            <div class="d-flex">
                              <div class="col">
                                <a
                                  class="btn btn-success w-50"
                                  href="/admin/order/view/${order.id}"
                                  >View</a
                                >
                              </div>
                              <div class="col">
                                <a
                                  class="btn btn-warning w-50"
                                  href="/admin/order/update/${order.id}"
                                  >Update</a
                                >
                              </div>
                              <div class="col">
                                <a
                                  class="btn btn-danger w-50"
                                  href="/admin/order/delete/${order.id}"
                                  >Delete</a
                                >
                              </div>
                            </div>
                          </td>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                  <nav aria-label="Page navigation example">
                    <ul class="pagination justify-content-center">
                      <li class="page-item">
                        <a
                          class="page-link ${currentPage == 1 ? 'disabled' : ''}"
                          href="/admin/order?page=${currentPage - 1}"
                          aria-label="Previous"
                        >
                          <span aria-hidden="true">&laquo;</span>
                        </a>
                      </li>
                      <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                        <li class="page-item">
                          <a
                            class="page-link ${loop.index == currentPage ? 'active' : ''}"
                            href="/admin/order?page=${loop.index}"
                            >${loop.index}</a
                          >
                        </li>
                      </c:forEach>

                      <li
                        class="page-item ${currentPage == totalPages ? 'disabled' : ''}"
                      >
                        <a
                          class="page-link"
                          href="/admin/order?page=${currentPage + 1}"
                          aria-label="Next"
                        >
                          <span aria-hidden="true">&raquo;</span>
                        </a>
                      </li>
                    </ul>
                  </nav>
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
