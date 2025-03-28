package com.zest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

      private String userId;
      private String username;
      private String role;
      private Date orderDate;
      private List<OrderedProductResponse> orderedProductResponseList;

}
