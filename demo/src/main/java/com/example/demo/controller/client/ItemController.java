package com.example.demo.controller.client;

import java.util.List;

import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Product;
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
}
