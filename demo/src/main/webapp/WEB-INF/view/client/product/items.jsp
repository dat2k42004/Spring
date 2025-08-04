<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<button%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

      <div class="container-fluid fruite py-5">
        <div class="container py-5">
          <h1 class="mb-4">List Products     </h1>
          <div class="row g-4">
            <div class="col-lg-12">
              <div class="row g-4">
                
                <div class="col-6">
                    
                </div>
                
              </div>
              <div class="row g-4">
                <div class="col-lg-3">
                  <div class="row g-4">
                    <div class="col-lg-12">
                         
                      <div class="mb-3" id="factoryFilter">
                        <h4>Factory</h4>
                         <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="Dell" name="factory">
                              <label class="form-check-label" for="checkDefault">
                              Dell
                              </label>
                         </div>
                         <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="Acer" name="factory">
                              <label class="form-check-label" for="checkDefault">
                              Acer
                              </label>
                         </div>
                         <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="Asus" name="factory">
                              <label class="form-check-label" for="checkDefault">
                              Asus
                              </label>
                         </div>
                         <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="Msi" name="factory">
                              <label class="form-check-label" for="checkDefault">
                              Msi
                              </label>
                         </div>
                         <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="Hp" name="factory">
                              <label class="form-check-label" for="checkDefault">
                              Hp
                              </label>
                         </div>
                         <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="Surface" name="factory">
                              <label class="form-check-label" for="checkDefault">
                              Surface
                              </label>
                         </div>
                         <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="Apple" name="factory">
                              <label class="form-check-label" for="checkDefault">
                              Apple
                              </label>
                         </div>
                      </div>
                    </div>
                    <div class="col-lg-12">
                      <div class="mb-3" id="targetFilter">
                        <h4 class="mb-2">Target</h4>
                        <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="Office" name="target">
                              <label class="form-check-label" for="checkDefault">
                              Office
                              </label>
                         </div>
                         <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="Gaming" name="target">
                              <label class="form-check-label" for="checkDefault">
                              Gaming
                              </label>
                         </div>
                         <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="Business" name="target">
                              <label class="form-check-label" for="checkDefault">
                              Business
                              </label>
                         </div>
                         <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="Design" name="target">
                              <label class="form-check-label" for="checkDefault">
                              Design
                              </label>
                         </div>
                         <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="Thin" name="target">
                              <label class="form-check-label" for="checkDefault">
                              Thin
                              </label>
                         </div>
                      </div>
                    </div>
                    <div class="col-lg-12">
                      <div class="mb-3" id="priceFilter">
                        <h4>Price</h4>
                        <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="1-100" name="mul_price">
                              <label class="form-check-label" for="checkDefault">
                              1-100
                              </label>
                         </div>
                         <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="100-200" name="mul_price">
                              <label class="form-check-label" for="checkDefault">
                              100-200
                              </label>
                         </div>
                         <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="200-500" name="mul_price">
                              <label class="form-check-label" for="checkDefault">
                              200-500
                              </label>
                         </div>
                      </div>
                    </div>
                    <div class="col-lg-12">
                      <div class="mb-3">
                        <h4>Sorting</h4>
                         <div class="form-check">
                              <input class="form-check-input" type="radio" name="radioDefault" value="inc">
                              <label class="form-check-label" for="radioDefault1">
                                   Price Increase
                              </label>
                         </div>
                         <div class="form-check">
                              <input class="form-check-input" type="radio" name="radioDefault" value="dec">
                              <label class="form-check-label" for="radioDefault1">
                                   Price Decrease
                              </label>
                         </div>
                         <div class="form-check">
                              <input class="form-check-input" type="radio" name="radioDefault" checked value="not">
                              <label class="form-check-label" for="radioDefault1">
                                   Not Sort
                              </label>
                         </div>
                      </div>
                    </div>
                    <div class="col-lg-12">
                         <button class="btn btn-warning" id="btnFilter">Filter</button>
                    </div>
                  </div>
                </div>
                <div class="col-lg-9">
                    
                    <div class="row g-4 justify-content-center">
                         <c:if test="${totalPages == 0}">
                              <div>Can't find product</div>
                         </c:if>
                    <c:forEach items="${product}" var="item">
                         <div class="col-md-6 col-lg-4 col-xl-4">
                         <div class="rounded position-relative fruite-item justify-content-center">
                              <div class="fruite-img d-flex align-items-center justify-content-center" style="height:220px; overflow:hidden;">
                              <a href="/product/${item.id}">
                              <img
                                   src="/images/product/${item.image}"
                                   class="img-fluid w-100 h-100 object-fit-cover rounded-top"
                                   alt=""
                                   style="height:220px; object-fit:cover;"
                              />
                              </a>
                              </div>
                              <div class="text-white bg-secondary px-3 py-1 rounded position-absolute" style="top: 10px; left: 10px">
                              Laptop
                              </div>
                              <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                              <h4>${item.name}</h4>
                              <p>${item.factory}</p>
                              <div class="d-flex justify-content-between flex-lg-wrap">
                              <p class="text-dark fs-5 fw-bold mb-0">$${item.price}</p>
                              <form action="/add-product-to-cart/${item.id}" method="post">
                                   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                   <button class="btn border border-secondary rounded-pill px-3 text-primary">
                                   <i class="fa fa-shopping-bag me-2 text-primary"></i>
                                   Add to cart
                                   </button>
                              </form>
                              </div>
                              </div>
                         </div>
                         </div>
                    </c:forEach>
                    <c:if test="${totalPages > 0}">
                         <div class="col-12">
                         <div class="pagination d-flex justify-content-center mt-5">
                         <c:if test="${currentPage > 1}">
                              <a
                              class="rounded ${currentPage == 1 ? 'disabled' : ''}"
                              href="/product?page=${currentPage - 1}${queryString}"
                              aria-label="Previous"
                         >
                              <span aria-hidden="true">&laquo;</span>
                         </a>
                         </c:if>
                         <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                              <a
                              class="rounded ${loop.index == currentPage ? 'active' : ''}"
                              href="/product?page=${loop.index}${queryString}"
                              >${loop.index}</a>
                         </c:forEach>
                         <c:if test="${currentPage < totalPages}">
                              <a
                              class="rounded ${currentPage == totalPages ? 'disabled' : ''}"
                              href="/product?page=${currentPage + 1}${queryString}"
                              aria-label="Next"
                         >
                              <span aria-hidden="true">&raquo;</span>
                         </a>
                         </c:if>
                         </div>
                    </div>
                    </c:if>
                    </div>
               </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <jsp:include page="../layout/feature.jsp" />
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
</button%@>
