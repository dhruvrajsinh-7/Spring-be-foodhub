package com.foodhub.backend.repository;

import com.foodhub.backend.entity.Order;
import com.foodhub.backend.entity.OrderStatus;
import com.foodhub.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUser(User user);

	List<Order> findByStatusNot(OrderStatus status);

}
