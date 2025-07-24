package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.domain.dto.RegisterDTO;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
     final private UserRepository userRepository;
     final private RoleRepository roleRepository;

     public UserService(UserRepository userRepository, RoleRepository roleRepository) {
          this.userRepository = userRepository;
          this.roleRepository = roleRepository;
     }

     public String handleHello() {
          return "Hello from UserService!";
     }

     public Page<User> handleGetAllUsers(Pageable pageable) {
          return this.userRepository.findAll(pageable);
     }

     public List<User> handleGetUserByEmail(String email) {
          return this.userRepository.findByEmail(email);
     }

     public User handleSaveUser(User user) {
          return this.userRepository.save(user);
     }

     public User handleGetUserById(long id) {
          return this.userRepository.findOneById(id);
     }

     public void handleDeleteUser(long id) {
          this.userRepository.deleteById(id);
     }

     public Role getRoleByName(String name) {
          return this.roleRepository.findByName(name);
     }

     public User registerDTOToUser(RegisterDTO registerDTO) {
          User user = new User();
          user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
          user.setEmail(registerDTO.getEmail());
          user.setPassword(registerDTO.getPassword());

          return user;
     }

     public boolean checkEmailExit(String email) {
          return this.userRepository.existsByEmail(email);
     }

     public User handleGetOneUserByEmail(String email) {
          return this.userRepository.findOneByEmail(email);
     }
}
