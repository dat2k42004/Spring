package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Cart;
import com.example.demo.domain.CartDetails;
import com.example.demo.domain.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetails, Long> {
     boolean existsByCartAndProduct(Cart cart, Product product);

     CartDetails findByCartAndProduct(Cart cart, Product product);

}
