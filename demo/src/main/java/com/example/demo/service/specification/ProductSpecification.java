package com.example.demo.service.specification;

import java.util.Arrays;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.domain.Product;
import com.example.demo.domain.Product_;

public class ProductSpecification {
     public static Specification<Product> nameLike(String name) {
          return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
     }

     public static Specification<Product> factoryIn(List<String> factory) {
          // return (root, query, criteriaBuilder) -> root.get(Product_.FACTORY).in(fac);
          System.out.println(">>>>???>>>>>> >>>>" + factory.toString());
          return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.FACTORY)).value(factory);
     }

     public static Specification<Product> targetIn(List<String> target) {
          return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.TARGET)).value(target);
     }

     public static Specification<Product> MinPrice(long price) {
          return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get(Product_.PRICE),
                    price);
     }

     public static Specification<Product> MaxPrice(long price) {
          return (root, query, criteriaBuilder) -> criteriaBuilder.le(root.get(Product_.PRICE), price);
     }

     public static Specification<Product> PriceBetween(long min, long max) {
          return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Product_.PRICE), min, max);
     }

     public static Specification<Product> PriceBetWeen1(List<String> price) {
          return (root, query, criteriaBuilder) -> {
               Predicate finalPrice = criteriaBuilder.disjunction(); // Set up for first time running
               for (int i = 0; i < price.size(); ++i) {
                    String[] arr = price.get(i).split("-");
                    finalPrice = criteriaBuilder.or(finalPrice,
                              criteriaBuilder.between(root.get(Product_.PRICE), Long.parseLong(arr[0]),
                                        Long.parseLong(arr[1])));
               }
               return finalPrice;
          };
     }
}
