package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Cart;
import com.example.demo.domain.CartDetails;
import com.example.demo.repository.CartDetailRepository;
import com.example.demo.repository.CartRepository;
import jakarta.servlet.http.HttpSession;

@Service
public class CartDetailService {

     private final CartRepository cartRepository;

     final private CartDetailRepository cartDetailRepository;

     public CartDetailService(CartDetailRepository cartDetailRepository, CartRepository cartRepository) {
          this.cartDetailRepository = cartDetailRepository;
          this.cartRepository = cartRepository;
     }

     public void handleDeleteCartDetail(long id, HttpSession session) {
          Optional<CartDetails> cartDetailOptional = this.cartDetailRepository.findById(id);
          if (cartDetailOptional.isPresent()) {
               CartDetails cartDetail = cartDetailOptional.get();
               Cart cart = cartDetail.getCart();

               this.cartDetailRepository.deleteById(id);

               if (cart.getSum() > 1) {
                    long sum = cart.getSum() - 1;
                    cart.setSum(sum);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", sum);
               } else {
                    this.cartRepository.deleteById(cart.getId());
                    session.setAttribute("sum", 0);
               }
          }
     }
}
