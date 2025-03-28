package com.zest.service;

import com.zest.client.ProductClient;
import com.zest.client.UserClient;
import com.zest.clientEntity.Role;
import com.zest.clientEntity.Users;
import com.zest.customeException.ResourceNotFoundException;
import com.zest.entity.Orders;
import com.zest.repository.OrderRepository;
import com.zest.request.OrderRequest;
import com.zest.request.Product;
import com.zest.response.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

      @Autowired
      private OrderRepository orderRepository;

      @Autowired
      private UserClient userClient;

      @Autowired
      private ProductClient productClient;

      public OrderResponse placeOrders(OrderRequest orderRequest) {
            ResponseEntity<ResponseStructure<List<Users>>> allUsers = userClient.getAllUser();
            List<Users> users = allUsers.getBody().getData();

            boolean userExists = users.stream()
                                         .anyMatch(user -> user.getUserId().equals(orderRequest.getUserId()));

            if (!userExists) {
                  throw new ResourceNotFoundException("NO USER FOUND WITH ID : " + orderRequest.getUserId());
            }

            List<ProductResponse> productResponses = new ArrayList<>();

            for (Product product : orderRequest.getProducts()) {
                  // Generate orderId for each product
                  String orderId = UUID.randomUUID().toString().substring(0, 8);

                  // Save order to database
                  Orders order = new Orders();
                  order.setOrderId(orderId);
                  order.setUserId(orderRequest.getUserId());
                  order.setProductId(product.getProductId());
                  order.setQuantity(product.getQuantity());
                  order.setOrderDate(orderRequest.getOrderdate());

                  orderRepository.save(order);

                  // Build ProductResponse using actual saved order ID
                  ProductResponse productResponse = new ProductResponse();
                  productResponse.setOrderId(orderId);
                  productResponse.setProductId(product.getProductId());
                  productResponse.setQuantity(product.getQuantity());

                  productResponses.add(productResponse);
            }

            return new OrderResponse(
                    orderRequest.getUserId(),
                    orderRequest.getOrderdate(),
                    productResponses
            );
      }

      //----------------------------------------------------------------------------------------------------------

      public OrderResponse getOrderByOrderId(String orderId) {
            Optional<Orders> optionalOrder = orderRepository.findById(orderId);

            if (!optionalOrder.isPresent()) {
                  throw new ResourceNotFoundException("ORDER NOT FOUND WITH ID : " + orderId);
            }

            Orders order = optionalOrder.get();

            // Create ProductResponse object
            ProductResponse productResponse = new ProductResponse();
            productResponse.setOrderId(orderId);
            productResponse.setProductId(order.getProductId());
            productResponse.setQuantity(order.getQuantity());


            // Create OrderResponse object
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setUserId(order.getUserId());
            orderResponse.setOrderDate(order.getOrderDate());
            orderResponse.setProductResponseList(List.of(productResponse));

            return orderResponse;
      }

      //---------------------------------------------------------------------------------------------------------------

      public List<ProductDetails> getOrderByUserId(String userId) {
            List<Orders> ordersByUserId = orderRepository.findOrderByUserId(userId);

            if (ordersByUserId == null || ordersByUserId.isEmpty()) {
                  throw new ResourceNotFoundException("ORDERS NOT FOUND FOR USER ID : " + userId);
            }

            ResponseEntity<ResponseStructure<List<Users>>> allUsers = userClient.getAllUser();
            List<Users> users = allUsers.getBody().getData();

            ProductDetails orderResponse = new ProductDetails();
            orderResponse.setUserId(ordersByUserId.get(0).getUserId());
            orderResponse.setOrderDate(ordersByUserId.get(0).getOrderDate());

            boolean userExists = false;
            for (Users user : users) {
                  if (user.getUserId().equals(userId)) {
                        orderResponse.setUsername(user.getUsername());
                        orderResponse.setRole(user.getRole());
                        userExists = true;
                        break;
                  }
            }
            if (!userExists)
                  throw new ResourceNotFoundException("NO USER FOUND WITH ID : " + userId);

            List<ProductDetails> orderResponseList = new ArrayList<>();

            // Create OrderResponse object
            for (Orders order : ordersByUserId) {

                  // Create OrderedProductResponse object
                  OrderedProductResponse productById = productClient.getProductById(order.getProductId());

                  OrderedProductResponse productResponse = new OrderedProductResponse();
                  productResponse.setProductId(productById.getProductId());
                  productResponse.setProductName(productById.getProductName());
                  productResponse.setPrice(productById.getPrice());
//                  productResponse.setQuantity(productById.getQuantity());
//                  productResponse.setTotalPrice(productById.getTotalPrice());

                  productResponse.setQuantity(productById.getQuantity() != 0 ? productById.getQuantity() : order.getQuantity());
                  productResponse.setTotalPrice(productById.getTotalPrice() != 0 ? productById.getTotalPrice() : productResponse.getPrice() * productResponse.getQuantity());

                  orderResponse.setOrderedProductResponseList(List.of(productResponse));

                  orderResponseList.add(orderResponse);
            }
            return orderResponseList;
      }

      //----------------------------------------------------------------------------------------------------------------
      public boolean deleteOrder(String orderId) {
            Optional<Orders> byId = orderRepository.findById(orderId);
            if (byId.isPresent()) {
                  orderRepository.deleteById(orderId);
                  return true;
            }
            return false;
      }

      //-------------------------------------------------------------------------------------------------------------
      public boolean deleteOrderByUserId(String userId) {
            List<Orders> byId = orderRepository.findOrderByUserId(userId);
            if (byId != null) {
                  orderRepository.deleteByUserId(userId);
                  return true;
            }
            return false;
      }

      //--------------------------------------------------------------------------------------------------------------------
      @Transactional
      public List<Orders> updateOrder(List<OrderRequest> orderRequests) {
            List<Orders> updatedOrders = new ArrayList<>();

            for (OrderRequest orderRequest : orderRequests) {
                  Optional<Orders> optionalOrder = orderRepository.findById(orderRequest.getUserId());
                  if (optionalOrder.isPresent()) {
                        Orders order = optionalOrder.get();
                        // Map order items if necessary
                        updatedOrders.add(orderRepository.save(order));
                  }
            }
            return updatedOrders;
      }

      //--------------------------------------------------------------------------------------------------------
      public List<Users> getAllUsers() {
            ResponseStructure<List<Users>> response = userClient.getAllUser().getBody();

            List<Users> allUsers = response.getData();

            if (allUsers == null || allUsers.isEmpty()) {
                  throw new ResourceNotFoundException("USERS NOT FOUND");
            }
            return allUsers;
      }

      //----------------------------------------------------------------------------------------------------
      public List<UserResponse> getAllOrders() {
            List<Orders> orders = orderRepository.findAll();

            if (orders == null || orders.isEmpty()) {
                  throw new ResourceNotFoundException("ORDER(S) NOT FOUND : ");
            }

            // ✅ Fetch all users
            ResponseEntity<ResponseStructure<List<Users>>> allUsers = userClient.getAllUser();
            List<Users> users = allUsers.getBody().getData();

            List<UserResponse> orderResponseList = new ArrayList<>();
            for (Orders order : orders) {
                  // Create ProductResponse object
                  ProductResponse productResponse = new ProductResponse();
                  productResponse.setOrderId(order.getOrderId());
                  productResponse.setProductId(order.getProductId());
                  productResponse.setQuantity(order.getQuantity());

                  // Create OrderResponse object
                  UserResponse orderResponse = new UserResponse();
                  orderResponse.setUserId(order.getUserId());
                  orderResponse.setOrderDate(order.getOrderDate());
                  orderResponse.setProductResponseList(List.of(productResponse));

                  for (Users user : users) {
                        if (user.getUserId().equals(order.getUserId())) {
                              orderResponse.setUsername(user.getUsername());
                              orderResponse.setRole(user.getRole());
                              break;
                        }
                  }

                  orderResponseList.add(orderResponse);
            }

            return orderResponseList;
      }



      //----------------------------------------------------------------------------------------------------------
      public OrderedProductResponse getProductById(String productId) {
            OrderedProductResponse product = productClient.getProductById(productId);
            OrderedProductResponse response = new OrderedProductResponse();
            if (product != null) {
                  response.setProductId(product.getProductId());
                  response.setProductName(product.getProductName());
                  response.setPrice(product.getPrice());
                  response.setQuantity(product.getQuantity());
                  return response;
            }
            throw new ResourceNotFoundException("NO PRODUCT FOUND FOR ID : " + productId);
      }

}
