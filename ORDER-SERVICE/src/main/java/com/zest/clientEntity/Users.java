package com.zest.clientEntity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {

      private String userId;
      private String username;
      private String password;
      @Enumerated(EnumType.STRING)
      private Role role;
}
