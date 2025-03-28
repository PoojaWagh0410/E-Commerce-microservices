package com.zest.client;

import com.zest.clientEntity.Users;
import com.zest.response.ResponseStructure;
import jakarta.ws.rs.PathParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {

      @GetMapping(path = "/users/all")
      ResponseEntity<ResponseStructure<List<Users>>> getAllUser();

      @GetMapping(path = "/users/{userId}")
      ResponseEntity<ResponseStructure<Users>> getUserById(@PathVariable String userId);
}
