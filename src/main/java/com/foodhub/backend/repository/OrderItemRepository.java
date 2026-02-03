package com.foodhub.backend.repository;

import com.foodhub.backend.entity.OrderItem;
import com.foodhub.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	List<OrderItem> findByOrder(Order order);

}
