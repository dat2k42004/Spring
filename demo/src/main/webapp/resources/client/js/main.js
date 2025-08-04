(function ($) {
    "use strict";

    // Spinner
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner(0);


    // Fixed Navbar
    $(window).scroll(function () {
        if ($(window).width() < 992) {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow');
            } else {
                $('.fixed-top').removeClass('shadow');
            }
        } else {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow').css('top', 0);
            } else {
                $('.fixed-top').removeClass('shadow').css('top', 0);
            }
        }
    });


    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({ scrollTop: 0 }, 1500, 'easeInOutExpo');
        return false;
    });


    // Testimonial carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 2000,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav: true,
        navText: [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0: {
                items: 1
            },
            576: {
                items: 1
            },
            768: {
                items: 1
            },
            992: {
                items: 2
            },
            1200: {
                items: 2
            }
        }
    });


    // vegetable carousel
    $(".vegetable-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1500,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav: true,
        navText: [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0: {
                items: 1
            },
            576: {
                items: 1
            },
            768: {
                items: 2
            },
            992: {
                items: 3
            },
            1200: {
                items: 4
            }
        }
    });


    // Modal Video
    $(document).ready(function () {
        var $videoSrc;
        $('.btn-play').click(function () {
            $videoSrc = $(this).data("src");
        });
        console.log($videoSrc);

        $('#videoModal').on('shown.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc + "?autoplay=1&amp;modestbranding=1&amp;showinfo=0");
        })

        $('#videoModal').on('hide.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc);
        })

        //add active class to header

        const navElement = $("#navbarCollapse");
        const currentUrl = window.location.pathname;

        navElement.find("a.nav-link").each(function () {
            const link = $(this);
            const href = link.attr('href');

            if (href === currentUrl) {
                link.addClass('active');
            }
            else {
                link.removeClass('active');
            }
        })

        const searchParams = new URLSearchParams(window.location.search);

        // Khôi phục checkbox cho factory
        const factories = searchParams.get("factory")?.split(",") || [];
        factories.forEach(val => {
            $(`#factoryFilter input[type="checkbox"][value="${val}"]`).prop("checked", true);
        });

        // Khôi phục checkbox cho target
        const targets = searchParams.get("target")?.split(",") || [];
        targets.forEach(val => {
            $(`#targetFilter input[type="checkbox"][value="${val}"]`).prop("checked", true);
        });

        // Khôi phục checkbox cho price
        const prices = searchParams.get("mul_price")?.split(",") || [];
        prices.forEach(val => {
            $(`#priceFilter input[type="checkbox"][value="${val}"]`).prop("checked", true);
        });

        // Khôi phục radio button sort
        const sort = searchParams.get("sort");
        if (sort) {
            $(`input[name="radioDefault"][value="${sort}"]`).prop("checked", true);
        }
    });



    // Product Quantity
    $('.quantity button').on('click', function () {
        var button = $(this);
        let change = 0;
        var input = button.parent().parent().find('input'); // Sửa ở đây
        var oldValue = input.val();
        var newVal;
        if (button.hasClass('btn-plus')) {
            newVal = parseFloat(oldValue) + 1;
            change = 1;
        } else {
            if (oldValue > 1) {
                newVal = parseFloat(oldValue) - 1;
                change = -1;
            } else {
                newVal = 1;
            }
        }
        function formatCurrency(val) {
            return val.toLocaleString();
        }
        input.val(newVal);

        const index = input.attr('data-cart-detail-index');
        const el = document.getElementById(`cartDetails${index}.quantity`);
        $(el).val(newVal);

        const price = input.attr('data-cart-detail-price');
        const id = input.attr('data-cart-detail-id');
        const priceElement = $(`p[data-cart-detail-id="${id}"]`);
        if (priceElement.length) {
            const newPrice = +price * newVal;
            priceElement.text(formatCurrency(newPrice) + '$');

        }


        const totalPriceElement = $(`p[data-cart-total-price]`);
        if (totalPriceElement && totalPriceElement.length) {
            const currentTotal = totalPriceElement.first().attr('data-cart-total-price');
            let newTotal = +currentTotal;

            if (change === 0) {
                newTotal = +currentTotal;
            } else {
                newTotal += change * (+price);
            }
            change = 0;
            totalPriceElement?.each(function (index, element) {
                $(totalPriceElement[index]).text(formatCurrency(newTotal) + '$');
                $(totalPriceElement[index]).attr('data-cart-total-price', newTotal);
            })
        }
    });

    $('#btnFilter').click(function (event) {

        event.preventDefault();
        let factoryArr = [];
        let targetArr = [];
        let priceArr = [];

        $('#factoryFilter .form-check-input:checked').each(function () {
            factoryArr.push($(this).val());
        });

        $('#targetFilter .form-check-input:checked').each(function () {
            targetArr.push($(this).val());
        });

        $('#priceFilter .form-check-input:checked').each(function () {
            priceArr.push($(this).val());
        });

        console.log(factoryArr)
        debugger





        let sortValue = $('input[name="radioDefault"]:checked').val();

        const currentUrl = new URL(window.location.href);
        const searchParams = currentUrl.searchParams;

        searchParams.set('page', 1);
        searchParams.set('sort', sortValue);

        searchParams.delete("factory");
        searchParams.delete("target");
        searchParams.delete("mul_price");

        if (factoryArr.length > 0) {
            searchParams.set('factory', factoryArr.join(","));
        }

        if (targetArr.length > 0) {
            searchParams.set("target", targetArr.join(","));
        }

        if (priceArr.length > 0) {
            searchParams.set('mul_price', priceArr.join(','));
        }

        window.location.href = currentUrl.toString();
    });


    $('.btnAddToCartHomePage').click(function (event) {
        event.preventDefault();
        if (!isLogin()) {
            $.toast({
                header: "Error!",
                text: "You need login to continue!",
                position: "top-right",
                icon: "error"
            })
            return;
        }

        const productId = $(this).attr('data-product-id');
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        const quantity = $("#carDetails0\\.quantity").val();

        $.ajax({
            url: `${window.location.origin}/api/add-product-to-cart`,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            type: "POST",
            data: JSON.stringify({ quantity: quantity, productId: productId }),
            contentType: "application/json",

            success: function (response) {
                const sum = +response;

                $('#sumCart').text(sum)
                $.toast({
                    heading: "Cart",
                    text: "Add to cart successfully",
                    position: "top-right",
                })
            },
            error: function (response) {
                alert("Error");
                console.log(response);
            }
        })
    })

    function isLogin() {
        const navElement = $('#navbarCollapse');
        const childLogin = navElement.find('a.a-login');
        if (childLogin.length > 0) {
            return false;
        }
        return true;
    }

})(jQuery);

