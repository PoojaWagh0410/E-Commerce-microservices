package com.zest.client;

import com.zest.response.OrderedProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

      @GetMapping(path = "/product/{productId}")
      OrderedProductResponse getProductById(@PathVariable String productId);
}
