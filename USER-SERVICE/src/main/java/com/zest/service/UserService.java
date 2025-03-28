package com.zest.service;

import com.zest.client.OrderClient;
import com.zest.client.ProductClient;
import com.zest.customeExceptions.EntityCreationException;
import com.zest.customeExceptions.ResourceNotFoundException;
import com.zest.entity.Users;
import com.zest.entityClients.Product;
import com.zest.repository.UserRepository;
import com.zest.request.userRequest;
import com.zest.response.OrderResponse;
import com.zest.response.OrderedProductResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

      @Autowired
      private UserRepository userRepository;

      @Autowired
      private PasswordEncoder passwordEncoder;

      @Autowired
      private OrderClient orderClient;

      @Autowired
      private ProductClient productClient;

      public Users registerUser(Users user) {
            user.setUserId(UUID.randomUUID().toString().substring(0, 8));
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            Users savedUser = userRepository.save(user);
            if (savedUser != null) {
                  return savedUser;
            }
            throw new EntityCreationException("User not created, Something went wrong");
      }

      public Users findByUserName(String username) {
            Users byUserName = userRepository.findByUsername(username);
            if (byUserName != null) return byUserName;
            throw new ResourceNotFoundException("USER NOT FOUND WITH  NAME : " + username);
      }

      public Users getUserById(String userId) {
            Optional<Users> userById = userRepository.findById(userId);
            if (userById.isPresent()) {
                  Users user = userById.get();
                  if (user != null) return user;
            }
            throw new ResourceNotFoundException("USER NOT FOUND WITH ID  : " + userId);
      }

      public List<Users> getAllUsers() {
            List<Users> allUsers = userRepository.findAll();
            if (allUsers.isEmpty()) {
                  throw new ResourceNotFoundException("USERS NOT FOUND");
            }
            return allUsers;
      }

      public Users loginUser(userRequest userRequest) {
            Users user = userRepository.findByUsername(userRequest.getUsername());

            if (Objects.nonNull(user) && passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
                  return user;
            }
            throw new ResourceNotFoundException("INVALID USERNAME AND PASSWORD, PLEASE PROVIDE VALID CREDENTIALS...");

      }

      public Users updateUser(Users user) {
            Optional<Users> existingUser = userRepository.findById(user.getUserId());

            if (existingUser.isPresent()) {
                  Users updatedUser = existingUser.get();

                  updatedUser.setUsername(user.getUsername());

                  if (user.getPassword() != null && !passwordEncoder.matches(user.getPassword(), updatedUser.getPassword())) {
                        updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
                  }

                  updatedUser.setRole(user.getRole());

                  Users savedUser = userRepository.save(updatedUser);
                  return new ResponseEntity<>(savedUser, HttpStatus.OK).getBody();
            } else {
                  throw new ResourceNotFoundException("INVALID USER DETAILS WITH USERNAME : " + user.getUsername());
            }
      }

      public boolean deleteUserById(String userId) {
            try {
                  userRepository.deleteById(userId);
                  return true;
            } catch (Exception e) {
                  e.printStackTrace();
            }
            return false;
      }

      //---------------------------------------------------------------------------------------------------------------
      public List<OrderResponse> getUserOrders(String userId) {
            Optional<Users> user = userRepository.findById(userId);

            if (user.isEmpty()) {
                  throw new ResourceNotFoundException("USER WITH ID '" + userId + "' NOT FOUND");
            }

            List<OrderResponse> response = orderClient.getOrderByUserId(userId);

            if (response == null || response.isEmpty()) {
                  throw new ResourceNotFoundException("NO ORDERS FOUND FOR USER ID : " + userId);
            }

            // Create a single OrderResponse object
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setUserId(user.get().getUserId());
            orderResponse.setUsername(user.get().getUsername());
            orderResponse.setRole(user.get().getRole().toString());
            orderResponse.setOrderDate(response.get(0).getOrderDate()); // Take date from first order

            // Create ProductResponse list
            List<OrderedProductResponse> productResponses = new ArrayList<>();
            for (OrderResponse order : response) {
                  for (OrderedProductResponse product : order.getOrderedProductResponseList()) {
                        OrderedProductResponse productResponse = new OrderedProductResponse();
                        productResponse.setProductId(product.getProductId());
                        productResponse.setProductName(product.getProductName());
                        productResponse.setPrice(product.getPrice());
                        productResponse.setQuantity(product.getQuantity());
                        productResponse.setTotalPrice(product.getPrice() * product.getQuantity());
                        productResponses.add(productResponse);
                  }
            }
            orderResponse.setOrderedProductResponseList(productResponses);

            return List.of(orderResponse);
      }

      //--------------------------------------------------------------------------------------------------------------
      public Product getProductsById(String productId) {
            Product response = productClient.getProductById(productId);

            if (response == null)
                  throw new ResourceNotFoundException("PRODUCT NOT FOUND WITH ID : " + productId);

            Product product = new Product();

            product.setProductId(response.getProductId());
            product.setProductName(response.getProductName());
            product.setPrice(response.getPrice());
            product.setStockQuantity(response.getStockQuantity());
            product.setCategory(response.getCategory());

            return product;
      }

}