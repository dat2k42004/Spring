package com.example.demo.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Order;
import com.example.demo.service.OrderService;

import org.springframework.ui.Model;

@Controller
public class OrderController {

     final OrderService orderService;

     public OrderController(OrderService orderService) {
          this.orderService = orderService;
     }

     @GetMapping("/admin/order")
     public String getOrderPage(Model model, @RequestParam("page") Optional<String> pageOptional) {
          int page = 1;

          try {
               if (pageOptional.isPresent()) {
                    page = Integer.parseInt(pageOptional.get());
               } else {

               }
          } catch (Exception e) {
               // TODO: handle exception
          }
          Pageable pageable = PageRequest.of(page - 1, 2);
          Page<Order> orders = this.orderService.handleGetAllOrder(pageable);
          model.addAttribute("orders", orders.getContent());
          model.addAttribute("currentPage", page);
          model.addAttribute("totalPages", orders.getTotalPages());
          return "/admin/order/view";
     }

     @GetMapping("/admin/order/view/{id}")
     public String getOrderDetailPage(Model model, @PathVariable long id) {
          Order order = this.orderService.handleGetOrderById(id);
          model.addAttribute("order", order);
          return "/admin/order/detail";
     }

     @GetMapping("/admin/order/update/{id}")
     public String getUpdateOrderPage(Model model, @PathVariable long id) {
          Order order = this.orderService.handleGetOrderById(id);
          model.addAttribute("order", order);
          return "/admin/order/update";
     }

     @PostMapping("/admin/order/update")
     public String postUpdateOrder(Model model, @ModelAttribute("order") Order order) {

          Order currentOrder = this.orderService.handleGetOrderById(order.getId());

          if (currentOrder != null) {
               currentOrder.setStatus(order.getStatus());
               this.orderService.handleSaveOrder(currentOrder);
          }
          return "redirect:/admin/order";
     }

     @GetMapping("/admin/order/delete/{id}")
     public String getDeleteOrderPage(Model model, @PathVariable long id) {
          Order order = this.orderService.handleGetOrderById(id);
          model.addAttribute("order", order);
          return "/admin/order/delete";
     }

     @PostMapping("/admin/order/delete")
     public String postDeleteOrder(Model model, @ModelAttribute("order") Order order) {

          this.orderService.handleDeleteOrder(order);
          return "redirect:/admin/order";
     }
}
