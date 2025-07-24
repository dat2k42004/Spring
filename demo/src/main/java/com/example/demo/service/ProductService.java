package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Cart;
import com.example.demo.domain.CartDetails;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.domain.dto.ProductCriteriaDTO;
import com.example.demo.repository.CartDetailRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.specification.ProductSpecification;

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

     public Page<Product> handleGetAllProduct(Pageable pageable) {
          return this.productRepository.findAll(pageable);
     }

     public Page<Product> handleGetAllProductFilter(Pageable pageable, ProductCriteriaDTO productCriteriaDTO) {
          // return this.productRepository.findAll(ProductSpecification.nameLike(name),
          // pageable);
          if (productCriteriaDTO.getTarget() == null && productCriteriaDTO.getFactory() == null
                    && productCriteriaDTO.getMul_price() == null) {
               return this.productRepository.findAll(pageable);
          }
          Specification<Product> combinedSpec = Specification.where(null);

          if (productCriteriaDTO.getTarget() != null && productCriteriaDTO.getTarget().isPresent()) {
               Specification<Product> currentSpec = ProductSpecification.targetIn(productCriteriaDTO.getTarget().get());
               combinedSpec = combinedSpec.and(currentSpec);
          }

          if (productCriteriaDTO.getFactory() != null && productCriteriaDTO.getFactory().isPresent()) {
               System.out.println("hello world");
               Specification<Product> currentSpec = ProductSpecification
                         .factoryIn(productCriteriaDTO.getFactory().get());
               combinedSpec = combinedSpec.and(currentSpec);
          }

          if (productCriteriaDTO.getMul_price() != null && productCriteriaDTO.getMul_price().isPresent()) {
               Specification<Product> currentSpec = ProductSpecification
                         .PriceBetWeen1(productCriteriaDTO.getMul_price().get());
               combinedSpec = combinedSpec.and(currentSpec);
          }

          return this.productRepository.findAll(combinedSpec, pageable);
     }

     public Page<Product> handleGetAllProductLikeFactory(Pageable pageable, List<String> factory) {

          return this.productRepository.findAll(ProductSpecification.factoryIn(factory), pageable);
     }

     public Page<Product> handleGetAllProductGreaterOrEqualPrice(Pageable pageable, long price) {

          return this.productRepository.findAll(ProductSpecification.MinPrice(price), pageable);
     }

     public Page<Product> handleGetAllProductLessOrEqualPrice(Pageable pageable, long price) {
          return this.productRepository.findAll(ProductSpecification.MaxPrice(price), pageable);
     }

     public Page<Product> handleGetAllProductBetweenPrice(Pageable pageable, long min, long max) {
          return this.productRepository.findAll(ProductSpecification.PriceBetween(min, max), pageable);
     }

     public Page<Product> handleGetALlProductMulPrice(Pageable pageable, List<String> arr) {
          return this.productRepository.findAll(ProductSpecification.PriceBetWeen1(arr), pageable);
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
