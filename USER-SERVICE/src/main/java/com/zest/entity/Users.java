package com.zest.entity;

import com.zest.entityClients.Orders;
import com.zest.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
