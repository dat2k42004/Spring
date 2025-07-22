package com.example.demo.controller.admin;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.service.UploadService;
import com.example.demo.service.UserService;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/*MVC Model */
@Controller
public class UserController {

     final private UserService userService;
     final private UploadService uploadService;
     final private PasswordEncoder passwordEncoder;

     public UserController(UserService userService, UploadService uploadService,
               PasswordEncoder passwordEncoder) {
          this.userService = userService;
          this.uploadService = uploadService;
          this.passwordEncoder = passwordEncoder;
     }

     /* get home page */
     @RequestMapping("/")
     public String getHomePage(Model model) {
          String test = this.userService.handleHello();
          List<User> arrUsers = this.userService.handleGetUserByEmail("dat@gmail.com");
          System.out.println(arrUsers);
          model.addAttribute("dat2k4", test);
          return "hello";
     }

     /* get create user page */
     @RequestMapping("/admin/user/create")
     public String getCreateUserPage(Model model) {
          String test = this.userService.handleHello();
          model.addAttribute("newUser", new User());
          return "admin/user/create";
     }

     /* get all user for page */
     @RequestMapping("/admin/user")
     public String getUserPage(Model model) {
          List<User> arrUsers = this.userService.handleGetAllUsers();
          model.addAttribute("arrUsers", arrUsers);
          return "admin/user/view";
     }

     /* post create user */
     @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
     public String createUser(Model model, @ModelAttribute("newUser") @Valid User dat2k4, BindingResult bindingResult,
               @RequestParam("inputFile") MultipartFile file) {

          List<FieldError> errors = bindingResult.getFieldErrors();

          for (FieldError error : errors) {
               System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
          }
          System.out.println("error");
          if (bindingResult.hasErrors()) {   
               return "/admin/user/create";
          }
          String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
          dat2k4.setAvatar(avatar);
          String hashPassword = this.passwordEncoder.encode(dat2k4.getPassword());
          dat2k4.setPassword(hashPassword);
          // System.out.println("Run here " + dat2k4);
          Role role = this.userService.getRoleByName(dat2k4.getRole().getName());
          dat2k4.setRole(role);
          this.userService.handleSaveUser(dat2k4);
          return "redirect:/admin/user";
     }

     /* get detail user page */
     @RequestMapping("/admin/user/view/{id}")
     public String getDetailUser(Model model, @PathVariable long id) {
          System.out.println("id >>>" + id);
          User user = this.userService.handleGetUserById(id);
          model.addAttribute("user", user);
          return "/admin/user/detail";
     }

     /* get update user page */
     @RequestMapping("/admin/user/update/{id}")
     public String getUpdateUserPage(Model model, @PathVariable long id) {
          User user = this.userService.handleGetUserById(id);
          model.addAttribute("user", user);
          return "/admin/user/update";
     }

     /* post update user */
     @RequestMapping(value = "/admin/user/update", method = RequestMethod.POST)
     public String updateUser(Model model, @ModelAttribute("user") @Valid User user,
               @RequestParam("inputFile") MultipartFile file) {
          User currentUser = this.userService.handleGetUserById(user.getId());
          if (currentUser != null) {
               currentUser.setAddress(user.getAddress());
               currentUser.setFullName(user.getFullName());
               currentUser.setPhone(user.getPhone());
               System.out.println("file" + file);
               if (file != null && !file.isEmpty()) {
                    String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
                    currentUser.setAvatar(avatar);
               }
               Role role = this.userService.getRoleByName(user.getRole().getName());
               currentUser.setRole(role);
               System.err.println(currentUser);
               this.userService.handleSaveUser(currentUser);
          }
          return "redirect:/admin/user";
     }

     /* get delete user page */
     @RequestMapping("/admin/user/delete/{id}")
     public String getDeteteUserPage(Model model, @PathVariable long id) {
          User user = new User();
          user.setId(id);
          model.addAttribute("id", id);
          model.addAttribute("user", user);
          return "/admin/user/delete";
     }

     /* post delete user */
     @RequestMapping(value = "/admin/user/delete", method = RequestMethod.POST)
     public String deleteUser(Model model, @ModelAttribute("user") User user) {
          this.userService.handleDeleteUser(user.getId());
          System.out.println("user day ne" + user);
          return "redirect:/admin/user";
     }
}

/* RestFull API Model */
// @RestController
// public class UserController {

// final private UserService userService;

// public UserController(UserService userService) {
// this.userService = userService;
// }

// @GetMapping("/")
// public String getHomePage() {
// return this.userService.handleHello();
// }
// }
