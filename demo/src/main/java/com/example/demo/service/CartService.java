package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Cart;
import com.example.demo.domain.CartDetails;
import com.example.demo.domain.User;
import com.example.demo.repository.CartDetailRepository;
import com.example.demo.repository.CartRepository;

@Service
public class CartService {
     final private CartRepository cartRepository;
     final private CartDetailRepository cartDetailRepository;
     public CartService(CartRepository cartRepository, CartDetailRepository cartDetailRepository) {
          this.cartRepository = cartRepository;
          this.cartDetailRepository = cartDetailRepository;
     }

     public Cart getCartByUser(User user) {
          return this.cartRepository.findByUser(user);
     }


     public void handleUpdateCartBeforeCheckout(List<CartDetails> cartDetails) {
          for (CartDetails cartDetail : cartDetails) {
               Optional<CartDetails> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
               if (cdOptional.isPresent()) {
                    CartDetails currentCartDetail = cdOptional.get();
                    currentCartDetail.setQuantity(cartDetail.getQuantity());
                    this.cartDetailRepository.save(currentCartDetail);
               }
          }
     }
}
