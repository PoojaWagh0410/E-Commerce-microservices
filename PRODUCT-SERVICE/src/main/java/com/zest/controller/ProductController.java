package com.zest.controller;

import com.zest.customeException.ResourceNotFoundException;
import com.zest.entity.Product;
import com.zest.response.ResponseStructure;
import com.zest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

      @Autowired
      private ProductService productService;

      @PostMapping
      protected ResponseEntity<ResponseStructure<Product>> addProduct(@RequestBody Product product) {
            Product response = productService.addProduct(product);
            ResponseStructure<Product> responseStructure = new ResponseStructure<>();

            responseStructure.setMessage("PRODUCT ADDED SUCCESSFULLY");
            responseStructure.setHttpStatusCode(HttpStatus.CREATED.value());
            responseStructure.setData(response);

            return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
      }

//      @GetMapping(path = "/{productId}")
//      protected ResponseEntity<ResponseStructure<Product>> getProductById(@PathVariable String productId) {
//            Product response = productService.getProductById(productId);
//            ResponseStructure<Product> responseStructure = new ResponseStructure<>();
//
//            if (response != null) {
//                  responseStructure.setMessage("PRODUCT FOUND WITH ID : " + productId);
//                  responseStructure.setHttpStatusCode(HttpStatus.OK.value());
//                  responseStructure.setData(response);
//            }
//            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
//      }

      @GetMapping(path = "/{productId}")
      protected Product getProductById(@PathVariable String productId) {
            Product response = productService.getProductById(productId);
            return response;
      }

      @GetMapping(path = "/byname/{productName}")
      protected ResponseEntity<ResponseStructure<List<Product>>> getProductByName(@PathVariable String productName) {
            List<Product> response = productService.getProductsByName(productName);
            ResponseStructure<List<Product>> responseStructure = new ResponseStructure<>();

            if (!response.isEmpty()) {
                  responseStructure.setMessage("PRODUCT(S) FOUND WITH NAME: " + productName);
                  responseStructure.setHttpStatusCode(HttpStatus.OK.value());
                  responseStructure.setData(response);

                  return new ResponseEntity<>(responseStructure, HttpStatus.OK);
            }
            throw new ResourceNotFoundException("NO PRODUCT FOUND WITH NAME : " + productName);
      }

      @GetMapping(path = "/all")
      protected ResponseEntity<ResponseStructure<List<Product>>> getAllProducts() {
            List<Product> response = productService.getAllProducts();
            ResponseStructure<List<Product>> responseStructure = new ResponseStructure<>();

            if (!response.isEmpty()) {
                  responseStructure.setMessage("ALL PRODUCT(S) FOUND SUCCESSFULLY: ");
                  responseStructure.setHttpStatusCode(HttpStatus.OK.value());
                  responseStructure.setData(response);

                  return new ResponseEntity<>(responseStructure, HttpStatus.OK);
            }
            throw new ResourceNotFoundException("NO PRODUCT FOUND");
      }

      @PutMapping
      protected ResponseEntity<ResponseStructure<Product>> updateProduct(@RequestBody Product product) {
            Product response = productService.updateProduct(product);
            ResponseStructure<Product> responseStructure = new ResponseStructure<>();

            responseStructure.setMessage("PRODUCT WITH ID: " + product.getProductId() + " UPDATED SUCCESSFULLY");
            responseStructure.setHttpStatusCode(HttpStatus.OK.value());
            responseStructure.setData(response);

            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
      }

      @DeleteMapping(path = "/{productId}")
      protected ResponseEntity<String> deleteProductById(@PathVariable String productId) {
            Product product = productService.getProductById(productId);
            if (product != null) {
                  boolean res = productService.deleteProductById(productId);
                  if (res)
                        return new ResponseEntity<>("PRODUCT WITH ID '" + productId + "'  AND NAME '" + product.getProductName() + "' DELETED SUCCESSFULLY!", HttpStatus.OK);
            }
            throw new ResourceNotFoundException("NO PRODUCT FOUND WITH  ID : " + product.getProductId());
      }


}
