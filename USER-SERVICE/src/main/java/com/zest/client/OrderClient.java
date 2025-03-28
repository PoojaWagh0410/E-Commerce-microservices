package com.zest.client;

import com.zest.response.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderClient {

      @GetMapping(path = "/orders/user/{userId}")
     List<OrderResponse> getOrderByUserId(@PathVariable("userId") String userId);

}
