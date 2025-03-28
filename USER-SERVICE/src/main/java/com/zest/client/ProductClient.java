package com.zest.client;

import com.zest.entityClients.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

      @GetMapping(path = "/product/{productId}")
      Product getProductById(@PathVariable("productId") String productId);

}


