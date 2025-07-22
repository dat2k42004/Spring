package com.example.demo.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Cart;
import com.example.demo.domain.CartDetails;
import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.domain.dto.RegisterDTO;
import com.example.demo.service.CartDetailService;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class HomePageController {
     final private ProductService productService;
     final private UserService userService;
     final private PasswordEncoder passwordEncoder;
     final private CartService cartService;
     final private CartDetailService cartDetailService;

     final private OrderService orderService;

     public HomePageController(ProductService productService, UserService userService,
               PasswordEncoder passwordEncoder, CartService cartService, CartDetailService cartDetailService,
               OrderService orderService) {
          this.productService = productService;
          this.userService = userService;
          this.passwordEncoder = passwordEncoder;
          this.cartService = cartService;
          this.cartDetailService = cartDetailService;
          this.orderService = orderService;
     }

     @GetMapping("/")
     public String getHomePage(Model model, HttpServletRequest request) {
          // HttpSession session = request.getSession(false);
          List<Product> product = this.productService.handleGetAllProduct();
          model.addAttribute("product", product);
          // model.addAttribute("fullName");
          return "/client/homepage/view";
     }

     @GetMapping("/register")
     public String getRegisterPage(Model model) {
          model.addAttribute("registerUser", new RegisterDTO());
          return "/client/auth/register";
     }

     @GetMapping("/login")
     public String getLoginPage(Model model) {
          return "/client/auth/login";
     }

     @PostMapping("/register")
     public String postRegister(Model model, @ModelAttribute("registerUser") @Valid RegisterDTO registerUser,
               BindingResult bindingResult) {

          if (bindingResult.hasErrors()) {
               return "/client/auth/register";
          }
          User user = this.userService.registerDTOToUser(registerUser);
          user.setPassword(this.passwordEncoder.encode(user.getPassword()));
          user.setRole(this.userService.getRoleByName("USER"));
          this.userService.handleSaveUser(user);
          return "redirect:/login";
     }

     @GetMapping("/access-deny")
     public String get404Page() {
          return "/client/auth/404";
     }

     @GetMapping("/cart")
     public String getCartPage(Model model, HttpServletRequest request) {
          HttpSession session = request.getSession(false);
          User user = this.userService.handleGetOneUserByEmail((String) session.getAttribute("email"));
          Cart cart = this.cartService.getCartByUser(user);
          if (cart == null) {
               cart = new Cart();
               cart.setCartDetails(new ArrayList<CartDetails>());
          }
          long totalPrice = 0;
          for (CartDetails item : cart.getCartDetails()) {
               totalPrice += item.getPrice() * item.getQuantity();
          }
          model.addAttribute("cart", cart);
          model.addAttribute("cartDetails", cart.getCartDetails());
          model.addAttribute("totalPrice", totalPrice);
          return "/client/cart/view";
     }

     @PostMapping("/cart/delete/{id}")
     public String postDeleteCartDetail(@PathVariable long id, HttpServletRequest request) {
          HttpSession session = request.getSession(false);
          this.cartDetailService.handleDeleteCartDetail(id, session);
          return "redirect:/cart";
     }

     @PostMapping("confirm-checkout")
     public String postConfirmCheckout(@ModelAttribute("cart") Cart cart) {
          List<CartDetails> cartDetails = cart == null ? new ArrayList<CartDetails>() : cart.getCartDetails();
          this.cartService.handleUpdateCartBeforeCheckout(cartDetails);
          return "redirect:/checkout";
     }

     @GetMapping("/checkout")
     public String getCheckoutPage(Model model, HttpServletRequest request) {
          HttpSession session = request.getSession(false);
          User user = this.userService.handleGetOneUserByEmail((String) session.getAttribute("email"));
          Cart cart = this.cartService.getCartByUser(user);
          if (cart == null) {
               cart = new Cart();
               cart.setCartDetails(new ArrayList<CartDetails>());
          }
          long totalPrice = 0;
          for (CartDetails item : cart.getCartDetails()) {
               totalPrice += item.getPrice() * item.getQuantity();
          }
          model.addAttribute("cart", cart);
          model.addAttribute("cartDetails", cart.getCartDetails());
          model.addAttribute("totalPrice", totalPrice);
          return "/client/cart/checkout";
     }

     @PostMapping("/place-order")
     public String postPlaceOrder(HttpServletRequest request,
               @RequestParam("receiverName") String receiverName,
               @RequestParam("receiverAddress") String receiverAddress,
               @RequestParam("receiverPhone") String receiverPhone) {
          HttpSession session = request.getSession(false);
          User user = this.userService.handleGetOneUserByEmail((String) session.getAttribute("email"));
          this.orderService.handlePlaceOrder(user, session, receiverName, receiverAddress, receiverPhone);
          System.out.println(">>>> ..... >>>>>" + receiverName);
          return "redirect:/thank";
     }

     @GetMapping("/thank")
     public String getThankPage() {
          return "/client/cart/thank";
     }

     @GetMapping("/history-order")
     public String getHistoryOrderPage(Model model, HttpServletRequest request) {
          HttpSession session = request.getSession(false);
          User user = this.userService.handleGetOneUserByEmail((String) session.getAttribute("email"));
          List<Order> orders = this.orderService.handleGetOrderByUser(user);
          model.addAttribute("orders", orders);
          return "/client/cart/history";
     }
}
