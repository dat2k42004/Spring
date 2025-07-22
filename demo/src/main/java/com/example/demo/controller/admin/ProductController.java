package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import com.example.demo.service.UploadService;

import jakarta.validation.Valid;

@Controller
public class ProductController {
     final private UploadService uploadService;
     final private ProductService productService;

     public ProductController(UploadService uploadService, ProductService productService) {
          this.uploadService = uploadService;
          this.productService = productService;
     }

     @GetMapping("/admin/product")
     public String getProductPage(Model model) {
          List<Product> products = this.productService.handleGetAllProduct();
          model.addAttribute("products", products);
          return "/admin/product/view";
     }

     @GetMapping("/admin/product/create")
     public String getCreateProductPage(Model model) {
          model.addAttribute("product", new Product());
          return "/admin/product/create";
     }

     @RequestMapping(value = "/admin/product/create", method = RequestMethod.POST)
     public String postCreateProduct(Model model, @ModelAttribute("product") @Valid Product product,
               BindingResult bindingResult,
               @RequestParam("inputFile") MultipartFile file) {

          if (bindingResult.hasErrors()) {
               return "/admin/product/create";
          }

          String image = this.uploadService.handleSaveUploadFile(file, "product");
          product.setImage(image);
          product.setSold(0);
          System.out.println("product: " + product);
          this.productService.handleSaveProduct(product);
          return "redirect:/admin/product";
     }

     @GetMapping("/admin/product/view/{id}")
     public String getDetailProductPage(Model model, @PathVariable long id) {
          Product product = this.productService.handleGetProductById(id);
          model.addAttribute("product", product);
          return "/admin/product/detail";
     }

     @GetMapping("/admin/product/update/{id}")
     public String getUpdateProductPage(Model model, @PathVariable long id) {
          Product product = this.productService.handleGetProductById(id);
          model.addAttribute("product", product);
          return "/admin/product/update";
     }

     @PostMapping("/admin/product/update")
     public String postUpdateProduct(Model model, @ModelAttribute("product") Product product,
               @RequestParam("inputFile") MultipartFile file) {

          Product currentProduct = this.productService.handleGetProductById(product.getId());
          if (currentProduct != null) {
               currentProduct.setName(product.getName());
               currentProduct.setPrice(product.getPrice());
               currentProduct.setDetailDesc(product.getDetailDesc());
               currentProduct.setShortDesc(product.getShortDesc());
               currentProduct.setQuantity(product.getQuantity());
               currentProduct.setFactory(product.getFactory());
               currentProduct.setTarget(product.getTarget());
               if (file != null && !file.isEmpty()) {
                    String avatar = this.uploadService.handleSaveUploadFile(file, "product");
                    product.setImage(avatar);
               }
          }

          System.out.println("product: " + currentProduct);
          this.productService.handleSaveProduct(currentProduct);
          return "redirect:/admin/product";
     }

     @GetMapping("/admin/product/delete/{id}")
     public String getDeleteProductPage(Model model, @PathVariable long id) {
          Product product = this.productService.handleGetProductById(id);
          model.addAttribute("product", product);
          return "/admin/product/delete";
     }

     @PostMapping("/admin/product/delete")
     public String postDeleteProduct(Model model, @ModelAttribute("product") Product product) {
          this.productService.handleDeleteProduct(product.getId());
          return "redirect:/admin/product";
     }
}
