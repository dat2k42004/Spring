package com.example.demo.domain;

import java.util.List;

import com.example.demo.service.validator.StrongPassword;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private long id;

     @NotNull
     @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
     private String email;

     @NotNull
     // @Min(value = 2, message = "Password must have at least 8")
     @StrongPassword(message = "Please enter another password, Password must have at least 8 char, have A - Z, a - z, 0 - 9, @#>...")
     private String password;

     @NotBlank(message = "full name not empty")
     private String fullName;

     private String address;

     private String phone;

     private String avatar;

     @ManyToOne
     @JoinColumn(name = "role_id")
     private Role role;

     public List<Order> getOrders() {
          return orders;
     }

     public void setOrders(List<Order> orders) {
          this.orders = orders;
     }

     @OneToMany(mappedBy = "user")
     private List<Order> orders;

     @OneToOne(mappedBy = "user")
     private Cart cart;

     public long getId() {
          return id;
     }

     public void setId(long id) {
          this.id = id;
     }

     public String getEmail() {
          return email;
     }

     public void setEmail(String email) {
          this.email = email;
     }

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public String getFullName() {
          return fullName;
     }

     public void setFullName(String fullName) {
          this.fullName = fullName;
     }

     public Cart getCart() {
          return cart;
     }

     public void setCart(Cart cart) {
          this.cart = cart;
     }

     public String getAddress() {
          return address;
     }

     public void setAddress(String address) {
          this.address = address;
     }

     public String getPhone() {
          return phone;
     }

     public void setPhone(String phone) {
          this.phone = phone;
     }

     public String getAvatar() {
          return avatar;
     }

     public void setAvatar(String avatar) {
          this.avatar = avatar;
     }

     @Override
     public String toString() {
          return "User [id=" + id + ", email=" + email + ", password=" + password + ", fullName=" + fullName
                    + ", address=" + address + ", phone=" + phone + ", avatar=" + avatar + "]";
     }

     public Role getRole() {
          return role;
     }

     public void setRole(Role role) {
          this.role = role;
     }

}
