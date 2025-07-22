<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %> <%@taglib prefix="spring"
uri="http://www.springframework.org/tags" %>

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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <script>
      $(document).ready(() => {
        const avatarFile = $("#avatarFile");
        avatarFile.change(function (e) {
          const imgURL = URL.createObjectURL(e.target.files[0]);
          $("#avatarPreview").attr("src", imgURL);
          $("#avatarPreview").css({ display: "block" });
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
                <a href="/admin/user">User</a>
              </li>
            </ol>
            <div class="mt-5">
              <div class="row">
                <div class="col-md-6 col-12 mx-auto">
                  <h3>Create New User</h3>
                  <hr />
                  <form:form
                    method="post"
                    action="/admin/user/create"
                    modelAttribute="newUser"
                    class="row"
                    enctype="multipart/form-data"
                  >
                    <div class="mb-3 col-12 col-md-6">
                      <label class="form-label">Email: </label>
                      <!-- cach 2 -->
                      <spring:bind path="email">
                        <form:input
                          type="email"
                          class="form-control ${status.error ? 'is-invalid' : ''}"
                          path="email"
                        />
                      </spring:bind>
                      <form:errors path="email" class="invalid-feedback" />
                    </div>
                    <div class="mb-3 col-12 col-md-6">
                      <label class="form-label">Password: </label>
                      <spring:bind path="password">
                        <form:input
                          type="password"
                          class="form-control ${status.error ? 'is-invalid' : ''}"
                          path="password"
                        />
                      </spring:bind>
                      <form:errors
                        path="password"
                        class="invalid-feedback"
                      ></form:errors>
                    </div>
                    <div class="mb-3 col-12 col-md-6">
                      <label class="form-label">Fullname: </label>

                      <!-- cach 1 -->
                      <c:set var="errFullName">
                        <form:errors path="fullName" />
                      </c:set>
                      <form:input
                        type="text"
                        class="form-control ${not empty errFullName? 'is-invalid':''}"
                        path="fullName"
                      />
                      <form:errors
                        path="fullName"
                        class="invalid-feedback"
                      ></form:errors>
                    </div>

                    <div class="mb-3 col-12 col-md-6">
                      <label class="form-label">Phone: </label>
                      <form:input
                        type="text"
                        class="form-control"
                        path="phone"
                      />
                    </div>
                    <div class="mb-3 col-12">
                      <label class="form-label">Address: </label>
                      <form:input
                        type="text"
                        class="form-control"
                        path="address"
                      />
                    </div>
                    <div class="mb-3 col-12 col-md-6">
                      <label class="form-label">Avatar: </label>
                      <input
                        class="form-control"
                        type="file"
                        name="inputFile"
                        id="avatarFile"
                        accept=".jpg, .png, .jpeg"
                      />
                    </div>
                    <div class="mb-3 col-12 col-md-6">
                      <label class="form-label">Role: </label>
                      <form:select path="role.name" class="form-select">
                        <form:option value="USER">User</form:option>
                        <form:option value="ADMIN">Admin</form:option>
                      </form:select>
                    </div>
                    <div class="col-12 mb-3">
                      <img
                        style="max-height: 250px; display: none"
                        alt="avatar preview"
                        id="avatarPreview"
                      />
                    </div>
                    <div class="col-12 mb-5">
                      <button type="submit" class="btn btn-primary">
                        Create
                      </button>
                    </div>
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
