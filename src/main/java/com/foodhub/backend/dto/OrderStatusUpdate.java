package com.foodhub.backend.dto;

import com.foodhub.backend.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderStatusUpdate {

	private Long orderId;

	private OrderStatus status;

}
