package com.zest.entity;

import jakarta.persistence.Entity;
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
public class Product {

      @Id
      private String productId;
      private String productName;
      private String description;
      private double price;
      private int stockQuantity;
      private String category;

}
