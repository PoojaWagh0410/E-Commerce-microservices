package com.zest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProductResponse {

      private String productId;
      private String productName;
      private double price;
      private int quantity;
      private double totalPrice;

}
