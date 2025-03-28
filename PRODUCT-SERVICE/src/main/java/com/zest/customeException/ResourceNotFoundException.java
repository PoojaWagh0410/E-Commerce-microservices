package com.zest.customeException;

public class ResourceNotFoundException  extends RuntimeException{

      public ResourceNotFoundException(String message){
            super(message);
      }
}
