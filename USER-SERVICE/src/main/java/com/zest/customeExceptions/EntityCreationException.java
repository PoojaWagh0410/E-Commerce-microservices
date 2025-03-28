package com.zest.customeExceptions;

public class EntityCreationException extends RuntimeException{

      public EntityCreationException(String message){
            super(message);
      }
}
