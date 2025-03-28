package com.zest.repository;

import com.zest.entity.Orders;
import com.zest.response.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, String> {

      @Query("SELECT o FROM Orders o WHERE o.productId = :productId")
      List<Orders> findByProductId(@Param("productId") String productId);

      @Modifying
      @Query("DELETE FROM Orders o WHERE o.userId = :userId")
      void deleteByUserId(@Param("userId") String userId);

      List<Orders> findOrderByUserId(String userId);

      Optional<Orders> findByOrderId(@Param("orderId") String orderId);

}
