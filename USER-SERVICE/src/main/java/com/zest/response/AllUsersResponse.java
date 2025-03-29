package com.zest.response;

import com.zest.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AllUsersResponse {

      private String userId;
      private String username;
      private String password;
      @Enumerated(EnumType.STRING)
      private Role role;
      private List<UsersOrderResponse> usersOrdersResponse;

}
