package com.foodhub.backend.services;

import com.foodhub.backend.dto.OrderStatusUpdate;
import com.foodhub.backend.entity.Order;
import com.foodhub.backend.entity.OrderStatus;
import com.foodhub.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class OrderStatusScheduler {

	private final OrderRepository orderRepo;

	private final SimpMessagingTemplate messagingTemplate;

	@Scheduled(fixedRate = 30000)
	public void updateOrderStatuses() {

		final List<Order> orders = orderRepo.findByStatusNot(OrderStatus.DELIVERED);

		for (final Order order : orders) {

			final OrderStatus oldStatus = order.getStatus();

			switch (oldStatus) {
				case ORDER_RECEIVED -> order.setStatus(OrderStatus.PREPARING);
				case PREPARING -> order.setStatus(OrderStatus.OUT_FOR_DELIVERY);
				case OUT_FOR_DELIVERY -> order.setStatus(OrderStatus.DELIVERED);
				default -> {
					continue;
				}
			}

			orderRepo.save(order);
			messagingTemplate.convertAndSend("/topic/order-status",
					new OrderStatusUpdate(order.getId(), order.getStatus()));
		}
	}

}
