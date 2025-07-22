package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Cart;
import com.example.demo.domain.CartDetails;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.repository.CartDetailRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class ProductService {
     final private ProductRepository productRepository;

     final private CartRepository cartRepository;

     final private CartDetailRepository cartDetailRepository;

     final private UserService userService;

     public ProductService(ProductRepository productRepository, CartRepository cartRepository,
               CartDetailRepository cartDetailRepository, UserService userService) {
          this.productRepository = productRepository;
          this.cartRepository = cartRepository;
          this.cartDetailRepository = cartDetailRepository;
          this.userService = userService;
     }

     public Product handleSaveProduct(Product product) {
          return this.productRepository.save(product);
     }

     public List<Product> handleGetAllProduct() {
          return this.productRepository.findAll();
     }

     public Product handleGetProductById(long id) {
          return this.productRepository.findOneById(id);
     }

     public void handleDeleteProduct(long id) {
          this.productRepository.deleteById(id);
     }

     public void handleAddProductToCart(String email, long id, HttpSession session, long quantity) {

          User user = this.userService.handleGetOneUserByEmail(email);
          if (user != null) {
               Cart cart = this.cartRepository.findByUser(user);

               if (cart == null) {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setSum(0);
                    cart = this.cartRepository.save(newCart);
               }
               Optional<Product> product = this.productRepository.findById(id);
               if (product.isPresent()) {
                    Product realPro = product.get();
                    CartDetails oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realPro);

                    if (oldDetail == null) {
                         CartDetails cd = new CartDetails();
                         cd.setCart(cart);
                         cd.setProduct(realPro);
                         cd.setPrice(realPro.getPrice());
                         cd.setQuantity(quantity);
                         this.cartDetailRepository.save(cd);
                         long sum = cart.getSum() + 1;
                         cart.setSum(sum);
                         this.cartRepository.save(cart);
                         session.setAttribute("sum", sum);
                    } else {
                         oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
                         this.cartDetailRepository.save(oldDetail);
                    }

               }
          }
     }
}
