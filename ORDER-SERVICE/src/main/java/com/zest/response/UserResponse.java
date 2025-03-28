package com.zest.response;

import com.zest.clientEntity.Role;
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
public class UserResponse {

      private String userId;
      private String username;
      private Role role;
      private Date orderDate;
      private List<ProductResponse> productResponseList;
}
