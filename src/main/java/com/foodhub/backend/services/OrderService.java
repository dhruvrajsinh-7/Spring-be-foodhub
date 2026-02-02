package com.foodhub.backend.services;

import com.foodhub.backend.dto.OrderRequestItem;
import com.foodhub.backend.entity.Item;
import com.foodhub.backend.entity.MenuItem;
import com.foodhub.backend.entity.Order;
import com.foodhub.backend.entity.OrderItem;
import com.foodhub.backend.entity.OrderStatus;
import com.foodhub.backend.entity.User;
import com.foodhub.backend.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepo;

	private final ItemService itemService;

	private final MenuService menuService;

	private final UserService userService;

	private final EntityManager entityManager;

	@Transactional
	public Order placeOrder(String name, String phone, String email, String address,
			List<OrderRequestItem> itemsRequest) {

		User user = userService.getOrCreateUser(name, phone, email != null && !email.isEmpty() ? email : null, address);

		Order order = new Order();
		order.setUser(user);
		order.setStatus(OrderStatus.ORDER_RECEIVED);

		List<OrderItem> orderItems = new ArrayList<>();
		double totalAmount = 0;

		for (OrderRequestItem req : itemsRequest) {
			// Frontend sends menuItemId, get MenuItem first, then get or create
			// corresponding Item
			MenuItem menuItem = menuService.getMenuItemById(req.getItemId());
			Item item = itemService.getOrCreateItemFromMenuItem(menuItem);

			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setItem(item);
			orderItem.setQuantity(req.getQuantity());
			orderItem.setPriceAtOrderTime(item.getPrice());

			totalAmount += item.getPrice() * req.getQuantity();
			orderItems.add(orderItem);
		}

		order.setOrderItems(orderItems);
		order.setTotalAmount(totalAmount);

		Order savedOrder = orderRepo.save(order);
		entityManager.flush();
		entityManager.refresh(savedOrder);
		return savedOrder;
	}

	public Order getOrderById(Long id) {
		return orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
	}

	@Transactional
	public Order updateStatus(Long orderId, OrderStatus status) {
		Order order = getOrderById(orderId);
		order.setStatus(status);
		Order savedOrder = orderRepo.save(order);
		entityManager.flush();
		entityManager.refresh(savedOrder);
		return savedOrder;
	}

}
