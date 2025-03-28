package com.zest.request;

import com.zest.clientEntity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

      private String userId;
      private Date Orderdate;

      private List<Product> products;
}
