<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <!-- Latest compiled and minified CSS -->

    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
      rel="stylesheet"
    />

    <!-- Icon Font Stylesheet -->
    <link
      rel="stylesheet"
      href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
    />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
      rel="stylesheet"
    />

    <!-- Libraries Stylesheet -->
    <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet" />
    <link
      href="/client/lib/owlcarousel/assets/owl.carousel.min.css"
      rel="stylesheet"
    />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="/client/css/bootstrap.min.css" rel="stylesheet" />

    <!-- Template Stylesheet -->
    <link href="/client/css/style.css" rel="stylesheet" />
  </head>
  <body>
    <!-- Spinner Start -->
    <div
      id="spinner"
      class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center"
    >
      <div class="spinner-grow text-primary" role="status"></div>
    </div>

    <jsp:include page="../layout/header.jsp" />
    <div class="container-fluid py-5">
      <div class="container py-5">
        <div class="table-responsive">
          <table class="table">
            <thead>
              <tr>
                <th scope="col">Products</th>
                <th scope="col">Name</th>
                <th scope="col">Price</th>
                <th scope="col">Quantity</th>
                <th scope="col">Total</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach
                items="${cart.cartDetails}"
                var="item"
                varStatus="status"
              >
                <tr>
                  <th scope="row">
                    <div class="d-flex align-items-center">
                      <img
                        src="/images/product/${item.product.image}"
                        class="img-fluid me-5 rounded-circle"
                        style="width: 80px; height: 80px"
                        alt=""
                      />
                    </div>
                  </th>
                  <td>
                    <a href="/product/${item.product.id}">
                      <p class="mb-0 mt-4">${item.product.name}</p>
                    </a>
                  </td>
                  <td>
                    <p class="mb-0 mt-4">${item.price} $</p>
                  </td>
                  <td>
                    <div class="input-group quantity mt-4" style="width: 100px">
                      <!--  -->
                      <input
                        type="text"
                        class="form-control form-control-sm text-center border-0"
                        value="${item.quantity}"
                        data-cart-detail-id="${item.id}"
                        data-cart-detail-price="${item.price}"
                        data-cart-detail-index="${status.index}"
                      />
                      
                      </div>
                    </div>
                  </td>
                  <td>
                    <p class="mb-0 mt-4" data-cart-detail-id="${item.id}">
                      ${item.price * item.quantity}$
                    </p>
                  </td>
                  
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
        
<form:form
                action="/place-order"
                method="post"
                modelAttribute="cart"
              >
                <input
                  type="hidden"
                  name="${_csrf.parameterName}"
                  value="${_csrf.token}"
                />
                <div class="row mt-5">
  <!-- Thông tin người nhận -->
  <div class="col-md-6">
    <h1 class="display-6 mb-4">
      Billing <span class="fw-normal">Details</span>
    </h1>
    <div class="form-group mb-3">
      <label>Name of Receiver:</label>
      <input type="text" class="form-control" name="receiverName" required />
    </div>
    <div class="form-group mb-3">
      <label>Address of Receiver:</label>
      <input type="text" class="form-control" name="receiverAddress" required />
    </div>
    <div class="form-group mb-3">
      <label>Phone of Receiver:</label>
      <input type="text" class="form-control" name="receiverPhone" required />
    </div>
    <div class="mt-4">
     <i class="fas fa-arrow-left"></i>
     <a href="/cart">Return to Cart</a>
    </div>
  </div>
  <!-- Thông tin thanh toán -->
  <div class="col-md-6">
    <div class="bg-light rounded">
      <div class="p-4">
        <h1 class="display-6 mb-4">
          Checkout <span class="fw-normal">Information</span>
        </h1>
        <div class="d-flex justify-content-between mb-4">
          <h5 class="mb-0 me-4">Type of Payment:</h5>
          <p class="mb-0" data-cart-total-price="${totalPrice}">
            Cast on Delivery
          </p>
        </div>
        <div class="d-flex justify-content-between">
          <h5 class="mb-0 me-4">Shipping Cost:</h5>
          <div class="">
            <p class="mb-0">$0.00</p>
          </div>
        </div>
      </div>
      <div class="py-4 mb-4 border-top border-bottom d-flex justify-content-between">
        <h5 class="mb-0 ps-4 me-4">Total</h5>
        <p class="mb-0 pe-4" data-cart-total-price="${totalPrice}">
          ${totalPrice}$
        </p>
      </div>
      <!-- Nút submit giữ nguyên -->
      <button
                  class="btn border-secondary rounded-pill px-4 py-3 text-primary text-uppercase mb-4 ms-4"
                  type="submit"
                >
                  Proceed Checkout
                </button>
    </div>
  </div>
</div>
                
              </form:form>
    <jsp:include page="../layout/footer.jsp" />

    <!-- JavaScript Libraries -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/client/lib/easing/easing.min.js"></script>
    <script src="/client/lib/waypoints/waypoints.min.js"></script>
    <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
    <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

    <!-- Template Javascript -->
    <script src="/client/js/main.js"></script>
  </body>
</html>
