package com.zest.exceptionHandler;

import com.zest.customeExceptions.EntityCreationException;
import com.zest.customeExceptions.ResourceNotFoundException;
import com.zest.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

      @ExceptionHandler(ResourceNotFoundException.class)
      public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
            String message = ex.getMessage();
            ApiResponse response = ApiResponse.builder().message(message).success(false).status(HttpStatus.NOT_FOUND).build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
      }

      @ExceptionHandler(EntityCreationException.class)
      public ResponseEntity<ApiResponse> handleEntityCreationException(EntityCreationException ex){
            String message = ex.getMessage();
            ApiResponse response = ApiResponse.builder().message(message).success(false).status(HttpStatus.BAD_REQUEST).build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
      }
}
