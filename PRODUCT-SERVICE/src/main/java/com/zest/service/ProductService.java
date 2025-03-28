package com.zest.service;

import com.zest.customeException.EntityCreationException;
import com.zest.customeException.ResourceNotFoundException;
import com.zest.entity.Product;
import com.zest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

      @Autowired
      private ProductRepository productRepository;

      public Product addProduct(Product product) {
            product.setProductId(UUID.randomUUID().toString().substring(0, 8));
            Product savedProduct = productRepository.save(product);
            if (savedProduct != null)
                  return savedProduct;
            throw new EntityCreationException("PRODUCT NOT ADDED");
      }

      public Product getProductById(String productId) {
            Optional<Product> byId = productRepository.findById(productId);
            if (byId.isPresent()) {
                  Product product = byId.get();
                  System.out.println(product);
                  return product;
            }
            throw new ResourceNotFoundException("PRODUCT NOT FOUND WITH ID  : " + productId);
      }

      public List<Product> getProductsByName(String productName) {
            List<Product> products = productRepository.findByProductName(productName);
            if (!products.isEmpty()) {
                  return products;
            }
            throw new ResourceNotFoundException("PRODUCT NOT FOUND WITH NAME: " + productName);
      }

      public Product updateProduct(Product product) {
            Product updated = productRepository.save(product);
            if (updated != null)
                  return updated;
            throw new ResourceNotFoundException("NO PRODUCT FOUND WITH ID : " + product.getProductId());
      }

      public List<Product> getAllProducts() {
            List<Product> products = productRepository.findAll();
            if (products != null)
                  return products;
            throw new ResourceNotFoundException("NO PRODUCT(S) FOUND");
      }

      public boolean deleteProductById(String productId) {
            try {
                  productRepository.deleteById(productId);
                  return true;
            } catch (Exception e) {
                  e.printStackTrace();
            }
            return false;
      }


}

