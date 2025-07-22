package com.example.demo.controller.admin;

import java.util.List;

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
          List<User> user = this.userService.handleGetAllUsers();
          List<Product> product = this.productService.handleGetAllProduct();
          List<Order> order = this.orderService.handleGetAllOrder();
          model.addAttribute("numberOfUsers", user.size());
          model.addAttribute("numberOfProducts", product.size());
          model.addAttribute("numberOfOrders", order.size());
          return "admin/dashboard/view";
     }
}
