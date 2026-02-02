package com.foodhub.backend.services;

import com.foodhub.backend.dto.OrderRequestItem;
import com.foodhub.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.foodhub.backend.entity.Order;
import com.foodhub.backend.entity.OrderItem;
import com.foodhub.backend.entity.Item;
import com.foodhub.backend.entity.User;
import com.foodhub.backend.entity.OrderStatus;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepo;

	private final ItemService itemService;

	private final UserService userService;

	public Order placeOrder(String name, String phone, String email, String address,
			List<OrderRequestItem> itemsRequest) {

		final User user = userService.getOrCreateUser(name, phone, email, address);

		final Order order = new Order();
		order.setUser(user);
		order.setStatus(OrderStatus.ORDER_RECEIVED);

		final List<OrderItem> orderItems = new ArrayList<>();
		double totalAmount = 0;

		for (final OrderRequestItem req : itemsRequest) {
			final Item item = itemService.getItemById(req.getItemId());

			final OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setItem(item);
			orderItem.setQuantity(req.getQuantity());
			orderItem.setPriceAtOrderTime(item.getPrice());

			totalAmount += item.getPrice() * req.getQuantity();
			orderItems.add(orderItem);
		}

		order.setOrderItems(orderItems);
		order.setTotalAmount(totalAmount);

		return orderRepo.save(order);
	}

	public Order getOrderById(final Long id) {
		return orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
	}

	public Order updateStatus(final Long orderId, final OrderStatus status) {
		final Order order = getOrderById(orderId);
		order.setStatus(status);
		return orderRepo.save(order);
	}

}
