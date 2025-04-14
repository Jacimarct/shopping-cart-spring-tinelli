package com.ecom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.model.Order;
import com.ecom.model.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {
	
	List<ProductOrder> findByUserId(Integer userId);

	Optional<ProductOrder> findByOrderId(String orderId);
	
	List<Order> findByPrintedFalse();
	
}
