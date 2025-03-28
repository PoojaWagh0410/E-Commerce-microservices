package com.zest.ExceptionHandler;

import com.zest.customeException.EntityCreationException;
import com.zest.customeException.ResourceNotFoundException;
import com.zest.response.ApiResponse;
import com.zest.response.ResponseStructure;
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

      @ExceptionHandler(Exception.class)
      public ResponseEntity<ResponseStructure<String>> handleException(Exception ex) {
            ResponseStructure<String> response = new ResponseStructure<>();
            response.setMessage("An error occurred: " + ex.getMessage());
            response.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }

}
