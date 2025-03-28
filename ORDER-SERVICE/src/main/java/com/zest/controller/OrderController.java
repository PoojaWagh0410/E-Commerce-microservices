package com.zest.controller;

import com.zest.clientEntity.Users;
import com.zest.customeException.ResourceNotFoundException;
import com.zest.request.OrderRequest;
import com.zest.response.*;
import com.zest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

      @Autowired
      private OrderService orderService;

      @PostMapping
      public ResponseEntity<ResponseStructure<OrderResponse>> placeOrders(@RequestBody OrderRequest orderRequest) {
            OrderResponse orderResponse = orderService.placeOrders(orderRequest);

            ResponseStructure<OrderResponse> responseStructure = new ResponseStructure<>();
            responseStructure.setMessage("ORDERS PLACED SUCCESSFULLY");
            responseStructure.setHttpStatusCode(HttpStatus.CREATED.value());
            responseStructure.setData(orderResponse);

            return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
      }

      //------------------------------------------------------------------------------------------------------
      @GetMapping(path = "/{orderId}")
      protected ResponseEntity<ResponseStructure<OrderResponse>> getOrderByOrderId(@PathVariable String orderId) {
            OrderResponse order = orderService.getOrderByOrderId(orderId);
            ResponseStructure<OrderResponse> responseStructure = new ResponseStructure<>();

            responseStructure.setMessage("ORDER FOUND FOR ORDER ID : " + orderId);
            responseStructure.setHttpStatusCode(HttpStatus.OK.value());
            responseStructure.setData(order);

            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
      }

//------------------------------------------------------------------------------------------------------------------------------

      @GetMapping(path = "/user/{userId}")
      protected ResponseEntity<ResponseStructure<List<ProductDetails>>> getOrdersByUserId(@PathVariable String userId) {
            List<ProductDetails> order = orderService.getOrderByUserId(userId);
            ResponseStructure<List<ProductDetails>> responseStructure = new ResponseStructure<>();

            responseStructure.setMessage("ORDERS FOUND FOR USER ID : " + userId);
            responseStructure.setHttpStatusCode(HttpStatus.OK.value());
            responseStructure.setData(order);

            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
      }

      //-------------------------------------------------------------
      //-------------------------------------------------------------------------------------------------------------------------------------
      @DeleteMapping(path = "/{orderId}")
      protected ResponseEntity<String> deleteOrder(@PathVariable String orderId) {
            boolean res = orderService.deleteOrder(orderId);
            if (res)
                  return new ResponseEntity<>("ORDER DELETED SUCESSFULLY WITH ORDER ID : " + orderId, HttpStatus.OK);
            throw new ResourceNotFoundException("ORDER NOT FOUND WITH ORDER ID : " + orderId);
      }

      //------------------------------------------------------------------------------------------------------------------------------------
      //      @DeleteMapping(path = "/userId/{userId}")
//      protected ResponseEntity<String> deleteOrderByUserId(@PathVariable String userId) {
//            boolean res = orderService.deleteOrderByUserId(userId);
//            if (res)
//                  return new ResponseEntity<>("ORDER DELETED SUCCESSFULLY FOR USER ID : " + userId, HttpStatus.OK);
//            throw new ResourceNotFoundException("USER NOT FOUND FOR USER ID : " + userId);
//      }

//--------------------------------------------------------------------------------------------------------------------------
//      @PutMapping
//      protected ResponseEntity<ResponseStructure<List<Orders>>> updateOrder(@RequestBody List<OrderRequest> orderRequests) {
//            List<Orders> response = orderService.updateOrder(orderRequests);
//            ResponseStructure<List<Orders>> responseStructure = new ResponseStructure<>();
//
//            if (!response.isEmpty()) {
//                  responseStructure.setMessage("ORDERS UPDATED SUCCESSFULLY");
//                  responseStructure.setHttpStatusCode(HttpStatus.OK.value());
//                  responseStructure.setData(response);
//
//                  return new ResponseEntity<>(responseStructure, HttpStatus.OK);
//            }
//
//            throw new ResourceNotFoundException("ORDERS NOT UPDATED, SOMETHING WENT WRONG !!!");
//      }

      //---------------------------------------------------------------------------------------------------------------
      @GetMapping(path = "/users/all")
      private ResponseEntity<ResponseStructure<List<Users>>> getAllUsers() {
            List<Users> allUsers = orderService.getAllUsers();

            if (allUsers.isEmpty()) {
                  throw new ResourceNotFoundException("USERS NOT FOUND");
            }

            ResponseStructure<List<Users>> responseStructure = new ResponseStructure<>();
            responseStructure.setMessage("USERS FOUND SUCCESSFULLY");
            responseStructure.setHttpStatusCode(HttpStatus.OK.value());
            responseStructure.setData(allUsers);

            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
      }
//-------------------------------------------------------------------------------------------------------
//      @GetMapping(path = "/product/{productId}")
//      protected ResponseEntity<Product> getProductById(@PathVariable String productId) {
//            Product product = orderService.getProductById(productId);
//            if (product != null)
//                  return new ResponseEntity<>(product, HttpStatus.OK);
//            throw new ResourceNotFoundException("NO PRODUCT DETAILS FOUND FOR ID : " + productId);
//      }

      //--------------------------------------------------------------------------------------------------

      @GetMapping(path="/all")
      protected ResponseEntity<ResponseStructure<Object>> getAllOrders() {
            List<UserResponse> allOrders = orderService.getAllOrders();

            ResponseStructure<Object> responseStructure = new ResponseStructure<>();

            responseStructure.setMessage("ALL ORDER(S) FOUND");
            responseStructure.setHttpStatusCode(HttpStatus.OK.value());
            responseStructure.setData(allOrders);

            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
      }

}
