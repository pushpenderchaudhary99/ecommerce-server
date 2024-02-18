package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.model.Order;


public interface OrderRepository extends JpaRepository<Order, Long>{
	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.orderStatus IN ('PLACED', 'CONFIRMED', 'SHIPPED', 'DELIVERED') ORDER BY o.orderDate DESC")
	public List<Order> getUserOrders(@Param("userId") Long userId);
	@Query("SELECT COUNT(o) FROM Order o WHERE o.orderStatus = 'DELIVERED'")
    public Long countDeliveredOrders();
	@Query("SELECT SUM(o.totalDiscountedPrice) FROM Order o WHERE o.orderStatus = 'DELIVERED'")
    public Long getRevanue();
	@Query("SELECT o FROM Order o WHERE o.orderStatus = 'DELIVERED'")
	public List<Order> getDeliveredOrders();
	 @Query("SELECT o FROM Order o WHERE o.orderStatus = 'DELIVERED' AND YEAR(o.orderDate) = :year")
	    List<Order> findDeliveredOrdersByYear(@Param("year") int year);
	 


}
