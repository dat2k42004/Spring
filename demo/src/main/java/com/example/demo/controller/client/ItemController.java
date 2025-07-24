package com.example.demo.controller.client;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Product;
import com.example.demo.domain.Product_;
import com.example.demo.domain.dto.ProductCriteriaDTO;
import com.example.demo.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
public class ItemController {

     final private ProductService productService;

     public ItemController(ProductService productService) {
          this.productService = productService;
     }

     @GetMapping("/product/{id}")
     public String getMethodName(Model model, @PathVariable long id) {
          // List<Product> product = this.productService.handleGetAllProduct();
          Product product = this.productService.handleGetProductById(id);
          model.addAttribute("item", product);
          return "/client/product/detail";
     }

     @PostMapping("/add-product-to-cart/{id}")
     public String postAddProductToCart(Model model, @PathVariable long id, HttpServletRequest request) {
          HttpSession session = request.getSession(false);

          long productId = id;

          String email = (String) session.getAttribute("email");
          this.productService.handleAddProductToCart(email, productId, session, 1);
          return "redirect:/cart";
     }

     @PostMapping("/add-to-cart/{id}")
     public String postAddToCart(Model model, @PathVariable long id, HttpServletRequest request,
               @RequestParam("quantity") Long quantity) {
          HttpSession session = request.getSession(false);

          long productId = id;
          String email = (String) session.getAttribute("email");
          this.productService.handleAddProductToCart(email, productId, session, quantity);
          return "redirect:/cart";
     }

     @GetMapping("/product")
     public String getProductsPage(Model model, ProductCriteriaDTO productCriteriaDTO, HttpServletRequest request) {
          // int page = 1;
          // long min_price = min_priceOptional.isPresent() ?
          // Long.parseLong(min_priceOptional.get()) : 0;
          // long max_price = max_priceOptional.isPresent() ?
          // Long.parseLong(max_priceOptional.get()) : Long.MAX_VALUE;
          // String[] price = priceOptional.isPresent() ? priceOptional.get().split("-")
          // : new String[] { "0", Long.toString(Long.MAX_VALUE) };

          // String[] res = mul_priceOptional.isPresent() ?
          // mul_priceOptional.get().split(",")
          // : new String[] { "0",
          // Long.toString(Long.MAX_VALUE) };
          // long[] mul_price;
          // if (!mul_priceOptional.isPresent()) {
          // mul_price = new long[] { 0, Long.MAX_VALUE };
          // } else {
          // mul_price = new long[res.length * 2];
          // for (int i = 0; i < res.length; ++i) {
          // String[] ans = res[i].split("-");
          // mul_price[i] = Long.parseLong(ans[0]);
          // mul_price[i + 1] = Long.parseLong(ans[1]);
          // System.out.println(i + " " + mul_price[i] + " " + mul_price[i + 1]);
          // }
          // System.out.println(">>>>>> Mul Price >>>>>" + mul_price);
          // }
          // try {
          // if (pageOptional.isPresent()) {
          // page = Integer.parseInt(pageOptional.get());
          // } else {

          // }
          // } catch (Exception e) {
          // // TODO: handle exception
          // }

          // String name = "";
          // try {
          // if (nameOptional.isPresent()) {
          // name = nameOptional.get();
          // }
          // } catch (Exception e) {
          // // TODO: handle exception
          // }
          // String factory = factoryOptional.isPresent() ? factoryOptional.get() : "";

          // Pageable pageable = PageRequest.of(page - 1, 12);
          // // Page<Product> products =
          // // this.productService.handleGetAllProductLikeName(pageable, name);
          // // Page<Product> products =
          // // this.productService.handleGetAllProductLikeFactory(pageable, factory);
          // // Page<Product> products =
          // // this.productService.handleGetAllProductGreaterOrEqualPrice(pageable,
          // // min_price);
          // // Page<Product> products =
          // // this.productService.handleGetAllProductLessOrEqualPrice(pageable,
          // max_price);
          // // Page<Product> products =
          // // this.productService.handleGetAllProductBetweenPrice(pageable,
          // // Long.parseLong(price[0]), Long.parseLong(price[1]));

          // Page<Product> products =
          // this.productService.handleGetALlProductMulPrice(pageable, mul_price);

          // listProducts.forEach(e -> System.out.println(e));

          int page = 1;
          try {
               if (productCriteriaDTO.getPage().isPresent()) {
                    page = Integer.parseInt(productCriteriaDTO.getPage().get());
               }
          } catch (Exception e) {

          }

          Pageable pageable = null;
          if (productCriteriaDTO.getSort() != null && productCriteriaDTO.getSort().isPresent()) {
               String sort = productCriteriaDTO.getSort().get();
               if (sort.equals("inc")) {
                    pageable = PageRequest.of(page - 1, 5, Sort.by(Product_.PRICE).ascending());
               } else if (sort.equals("dec")) {
                    pageable = PageRequest.of(page - 1, 5, Sort.by(Product_.PRICE).descending());
               } else {
                    pageable = PageRequest.of(page - 1, 5);
               }
          } else {
               pageable = PageRequest.of(page - 1, 5);
          }

          String res = request.getQueryString();
          if (res != null && !res.isBlank()) {
               res = res.replace("page=" + page, "");
          }
          Page<Product> products = this.productService.handleGetAllProductFilter(pageable, productCriteriaDTO);
          List<Product> listProducts = products.getContent();
          model.addAttribute("product", listProducts);
          model.addAttribute("currentPage", page);
          model.addAttribute("totalPages", products.getTotalPages());
          model.addAttribute("queryString", res);
          return "/client/product/items";
     }
}
