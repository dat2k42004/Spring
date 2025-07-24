package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Cart;
import com.example.demo.domain.CartDetails;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderDetail;
import com.example.demo.domain.User;
import com.example.demo.repository.CartDetailRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class OrderService {
     final private OrderRepository orderRepository;
     final private OrderDetailRepository orderDetailRepository;
     final private CartRepository cartRepository;
     final private CartDetailRepository cartDetailRepository;

     public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
               CartRepository cartRepository, CartDetailRepository cartDetailRepository) {
          this.orderRepository = orderRepository;
          this.orderDetailRepository = orderDetailRepository;
          this.cartRepository = cartRepository;
          this.cartDetailRepository = cartDetailRepository;
     }

     public Page<Order> handleGetAllOrder(Pageable pageable) {
          Page<Order> orders = this.orderRepository.findAll(pageable);
          return orders;
     }

     public List<Order> handleGetOrderByUser(User user) {
          return this.orderRepository.findByUser(user);
     }

     public Order handleGetOrderById(long id) {
          return this.orderRepository.findById(id);
     }

     public void handleSaveOrder(Order order) {
          this.orderRepository.save(order);
     }

     public void handleDeleteOrder(Order order) {
          List<OrderDetail> orderDetail = this.orderDetailRepository.findByOrderId(order.getId());
          if (orderDetail != null) {
               for (OrderDetail od : orderDetail) {
                    this.orderDetailRepository.deleteById(od.getId());
               }
          }
          this.orderRepository.deleteById(order.getId());
     }

     public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress,
               String receiverPhone) {

          Cart cart = this.cartRepository.findByUser(user);

          // ...existing code...
          if (cart != null) {
               double totalPrice = 0;
               Order order = new Order();
               order.setUser(user);
               order.setReceiverName(receiverName);
               order.setReceiverAddress(receiverAddress);
               order.setReceiverPhone(receiverPhone);
               order.setStatus("PENDING");
               order = this.orderRepository.save(order);
               List<CartDetails> cartDetails = cart.getCartDetails();
               if (cartDetails != null) {
                    for (CartDetails cartDetail : cartDetails) {
                         totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
                    }
                    order.setTotalPrice(totalPrice);
                    this.orderRepository.save(order);
                    for (CartDetails cartDetail : cartDetails) {
                         OrderDetail orderDetail = new OrderDetail();
                         orderDetail.setOrder(order);
                         orderDetail.setProduct(cartDetail.getProduct());
                         orderDetail.setQuantity(cartDetail.getQuantity());
                         orderDetail.setPrice(cartDetail.getPrice());
                         this.orderDetailRepository.save(orderDetail);
                    }

                    for (CartDetails cd : cartDetails) {
                         this.cartDetailRepository.deleteById(cd.getId());
                    }
               }
               // this.cartRepository.deleteById(cart.getId());
               session.setAttribute("sum", 0);
          }
          // ...existing code...
     }
}
