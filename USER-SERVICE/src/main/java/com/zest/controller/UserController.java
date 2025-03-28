package com.zest.controller;

import com.zest.customeExceptions.ResourceNotFoundException;
import com.zest.entity.Users;
import com.zest.entityClients.Product;
import com.zest.request.userRequest;
import com.zest.response.OrderResponse;
import com.zest.response.OrderedProductResponse;
import com.zest.response.ResponseStructure;
import com.zest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

      @Autowired
      private UserService userService;

      @PostMapping
      protected ResponseEntity<ResponseStructure<Users>> registerUser(@RequestBody Users user) {
            Users response = userService.registerUser(user);
            ResponseStructure<Users> responseStructure = new ResponseStructure<>();
            responseStructure.setMessage("USER ADDED SUCCESSFULLY");
            responseStructure.setHttpStatusCode(HttpStatus.CREATED.value());
            responseStructure.setData(response);
            return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
      }

      @PostMapping(path = "/login")
      protected ResponseEntity<ResponseStructure<Users>> loginUser(@RequestBody userRequest userRequest) {
            Users user = userService.loginUser(userRequest);
            ResponseStructure<Users> responseStructure = new ResponseStructure<>();
            responseStructure.setMessage("WELCOME '" + userRequest.getUsername().toUpperCase() + "',  YOU ARE LOGIN SUCCESSFULLY");
            responseStructure.setHttpStatusCode(HttpStatus.FOUND.value());
            responseStructure.setData(user);
            return new ResponseEntity<>(responseStructure, HttpStatus.FOUND);
      }

      @GetMapping(path = "/byname/{username}")
      protected ResponseEntity<ResponseStructure<Users>> getUserByUsername(@PathVariable String username) {
            Users response = userService.findByUserName(username);
            ResponseStructure<Users> responseStructure = new ResponseStructure<>();
            responseStructure.setMessage("USER FOUND WITH NAME : " + username);
            responseStructure.setHttpStatusCode(HttpStatus.FOUND.value());
            responseStructure.setData(response);
            return new ResponseEntity<>(responseStructure, HttpStatus.FOUND);
      }

      @GetMapping(path = "/{userId}")
      protected ResponseEntity<ResponseStructure<Users>> getUserById(@PathVariable String userId) {
            Users response = userService.getUserById(userId);
            ResponseStructure<Users> responseStructure = new ResponseStructure<>();
            responseStructure.setMessage("USER FOUND WITH ID : " + userId);
            responseStructure.setHttpStatusCode(HttpStatus.FOUND.value());
            responseStructure.setData(response);
            return new ResponseEntity<>(responseStructure, HttpStatus.FOUND);
      }

      @GetMapping(path = "/all")
      protected ResponseEntity<ResponseStructure<List<Users>>> getAllUsers() {
            List<Users> response = userService.getAllUsers();

            ResponseStructure<List<Users>> responseStructure = new ResponseStructure<>();
            if (response != null) {
                  responseStructure.setMessage("USERS FOUND");
                  responseStructure.setHttpStatusCode(HttpStatus.OK.value());
                  responseStructure.setData(response);
            }
            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
      }

      @PutMapping
      protected ResponseEntity<ResponseStructure<Users>> updateUser(@RequestBody Users user) {
            Users response = userService.updateUser(user);
            ResponseStructure<Users> responseStructure = new ResponseStructure<>();
            responseStructure.setMessage("USER UPDATED SUCCESSFULLY");
            responseStructure.setHttpStatusCode(HttpStatus.OK.value());
            responseStructure.setData(response);
            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
      }

      @DeleteMapping(path = "/{userId}")
      protected ResponseEntity<String> deleteUserByUserId(@PathVariable String userId) {
            Users userById = userService.getUserById(userId);
            if (userById != null) {
                  boolean res = userService.deleteUserById(userId);
                  if (res) {
                        return new ResponseEntity<>("USER DELETED SUCCESSFULLY!", HttpStatus.OK);
                  }
            }
            throw new ResourceNotFoundException("USER NOT FOUND WITH  ID : " + userById.getUserId());
      }

//      @GetMapping(path = "/{userId}/orders")
//      public List<OrderResponse> getUserOrders(@PathVariable String userId) {
//            return userService.getUserOrders(userId);
//      }

      @GetMapping(path = "/product/{productId}")
      protected Product getProductById(@PathVariable String productId) {
            return userService.getProductsById(productId);
      }

}
