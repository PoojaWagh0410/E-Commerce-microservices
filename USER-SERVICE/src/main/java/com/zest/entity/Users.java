package com.zest.entity;

import com.zest.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Users {

      @Id
      private String userId;
      private String username;
      private String password;
      @Enumerated(EnumType.STRING)
      private Role role;

//      @Transient
//      private List<Orders> orders;

}
