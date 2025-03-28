package com.zest.entityClients;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
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
public class Orders {

      @Id
      private String orderId;
      private String userId;
      private String productId;
      private int quantity;
      @Temporal(TemporalType.DATE)
      private Date orderDate;


      @Transient
      private List<Product> productResponses;
}

