package com.zest.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ApiResponse {

      private String message;
      private boolean success;
      private HttpStatus status;
}