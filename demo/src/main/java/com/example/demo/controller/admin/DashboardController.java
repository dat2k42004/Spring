package com.example.demo.controller.admin;

import java.util.List;

import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import com.example.demo.service.ProductService;
// import ch.qos.logback.core.model.Model;

@Controller
public class DashboardController {

     final private UserService userService;
     final private ProductService productService;
     final private OrderService orderService;

     public DashboardController(UserService userService, ProductService productService, OrderService orderService) {
          this.userService = userService;
          this.productService = productService;
          this.orderService = orderService;
     }

     @GetMapping("/admin")
     public String getDashBoard(Model model) {
          Pageable pageable = PageRequest.of(0, 8);
          List<User> user = this.userService.handleGetAllUsers(pageable).getContent();
          List<Product> product = this.productService.handleGetAllProduct(pageable).getContent();
          List<Order> order = this.orderService.handleGetAllOrder(pageable).getContent();
          model.addAttribute("numberOfUsers", user.size());
          model.addAttribute("numberOfProducts", product.size());
          model.addAttribute("numberOfOrders", order.size());
          return "admin/dashboard/view";
     }
}
