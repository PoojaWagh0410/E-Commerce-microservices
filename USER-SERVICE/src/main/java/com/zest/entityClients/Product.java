package com.zest.entityClients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

      private String productId;
      private String productName;
      private double price;
      private int stockQuantity;
      private String category;

}


