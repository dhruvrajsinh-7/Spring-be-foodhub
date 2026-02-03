package com.foodhub.backend.testdata;

import com.foodhub.backend.dto.CreateOrderRequest;
import com.foodhub.backend.dto.OrderRequestItem;
import com.foodhub.backend.dto.OrderStatusUpdate;
import com.foodhub.backend.entity.Item;
import com.foodhub.backend.entity.Order;
import com.foodhub.backend.entity.OrderItem;
import com.foodhub.backend.entity.OrderStatus;
import com.foodhub.backend.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test data factory for Order-related test objects. Provides factory methods to create
 * consistent test data across all order tests.
 */
public final class OrderTestDataFactory {

	private OrderTestDataFactory() {
		throw new UnsupportedOperationException("Utility class");
	}

	// ===== Request DTOs =====

	/**
	 * Creates a CreateOrderRequest with default test data.
	 */
	public static CreateOrderRequest createOrderRequest() {
		CreateOrderRequest request = new CreateOrderRequest();
		request.setName("John Doe");
		request.setPhone("1234567890");
		request.setEmail("john.doe@example.com");
		request.setAddress("123 Main St");
		request.setItems(Arrays.asList(createOrderRequestItem()));
		return request;
	}

	/**
	 * Creates a CreateOrderRequest with empty items list.
	 */
	public static CreateOrderRequest createOrderRequestWithEmptyItems() {
		CreateOrderRequest request = new CreateOrderRequest();
		request.setName("John Doe");
		request.setPhone("1234567890");
		request.setEmail("john.doe@example.com");
		request.setAddress("123 Main St");
		request.setItems(new ArrayList<>());
		return request;
	}

	/**
	 * Creates a CreateOrderRequest with custom parameters.
	 */
	public static CreateOrderRequest createOrderRequest(String name, String phone, String email, String address,
			List<OrderRequestItem> items) {
		CreateOrderRequest request = new CreateOrderRequest();
		request.setName(name);
		request.setPhone(phone);
		request.setEmail(email);
		request.setAddress(address);
		request.setItems(items);
		return request;
	}

	/**
	 * Creates an OrderRequestItem with default test data.
	 */
	public static OrderRequestItem createOrderRequestItem() {
		OrderRequestItem item = new OrderRequestItem();
		item.setItemId(getDefaultItemId());
		item.setQuantity(2);
		return item;
	}

	/**
	 * Creates an OrderRequestItem with custom parameters.
	 */
	public static OrderRequestItem createOrderRequestItem(Long itemId, Integer quantity) {
		OrderRequestItem item = new OrderRequestItem();
		item.setItemId(itemId);
		item.setQuantity(quantity);
		return item;
	}

	/**
	 * Creates a list of OrderRequestItem with default test data.
	 */
	public static List<OrderRequestItem> createOrderRequestItemList() {
		return Arrays.asList(createOrderRequestItem(), createOrderRequestItem(getDefaultItemId() + 1, 3));
	}

	/**
	 * Creates an OrderStatusUpdate with default test data.
	 */
	public static OrderStatusUpdate createOrderStatusUpdate() {
		return new OrderStatusUpdate(getDefaultOrderId(), OrderStatus.PREPARING);
	}

	/**
	 * Creates an OrderStatusUpdate with custom parameters.
	 */
	public static OrderStatusUpdate createOrderStatusUpdate(Long orderId, OrderStatus status) {
		return new OrderStatusUpdate(orderId, status);
	}

	// ===== Entity Objects =====

	/**
	 * Creates an Order entity with default test data.
	 */
	public static Order createOrder() {
		Order order = new Order();
		order.setId(getDefaultOrderId());
		order.setUser(createUser());
		order.setStatus(OrderStatus.ORDER_RECEIVED);
		order.setTotalAmount(25.98);
		OrderItem orderItem = createOrderItem();
		orderItem.setOrder(order);
		order.setOrderItems(Arrays.asList(orderItem));
		return order;
	}

	/**
	 * Creates an Order entity with custom status.
	 */
	public static Order createOrderWithStatus(OrderStatus status) {
		Order order = createOrder();
		order.setStatus(status);
		return order;
	}

	/**
	 * Creates an OrderItem entity with default test data.
	 */
	public static OrderItem createOrderItem() {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(1L);
		orderItem.setItem(ItemTestDataFactory.createItem());
		orderItem.setQuantity(2);
		orderItem.setPriceAtOrderTime(12.99);
		return orderItem;
	}

	/**
	 * Creates an OrderItem entity with custom parameters.
	 */
	public static OrderItem createOrderItem(Order order, Item item, Integer quantity, Double price) {
		OrderItem orderItem = new OrderItem();
		orderItem.setOrder(order);
		orderItem.setItem(item);
		orderItem.setQuantity(quantity);
		orderItem.setPriceAtOrderTime(price);
		return orderItem;
	}

	/**
	 * Creates a User entity with default test data.
	 */
	public static User createUser() {
		User user = new User();
		user.setId(getDefaultUserId());
		user.setName("John Doe");
		user.setPhone("1234567890");
		user.setEmail("john.doe@example.com");
		user.setAddress("123 Main St");
		return user;
	}

	/**
	 * Creates a User entity with custom parameters.
	 */
	public static User createUser(String name, String phone, String email, String address) {
		User user = new User();
		user.setName(name);
		user.setPhone(phone);
		user.setEmail(email);
		user.setAddress(address);
		return user;
	}

	// ===== Constants =====

	/**
	 * Default test order ID.
	 */
	public static Long getDefaultOrderId() {
		return 1L;
	}

	/**
	 * Default test user ID.
	 */
	public static Long getDefaultUserId() {
		return 1L;
	}

	/**
	 * Default test item ID.
	 */
	public static Long getDefaultItemId() {
		return 1L;
	}

	/**
	 * Default test phone number.
	 */
	public static String getDefaultPhone() {
		return "1234567890";
	}

	/**
	 * Default test name.
	 */
	public static String getDefaultName() {
		return "John Doe";
	}

	/**
	 * Default test email.
	 */
	public static String getDefaultEmail() {
		return "john.doe@example.com";
	}

	/**
	 * Default test address.
	 */
	public static String getDefaultAddress() {
		return "123 Main St";
	}

	// ===== Message Constants (Inner Types Must Be Last) =====

	/**
	 * Message constants for test assertions. Note: Inner types must be placed at the end
	 * to comply with InnerTypeLast checkstyle rule.
	 */
	public static final class Messages {

		public static final String ORDER_CREATED_SUCCESSFULLY = "Order created successfully";

		public static final String ORDER_UPDATED_SUCCESSFULLY = "Order updated successfully";

		public static final String ORDER_NOT_FOUND = "Order not found";

		public static final String ITEM_NOT_FOUND = "Item not found";

		/**
		 * Private constructor to prevent instantiation.
		 */
		private Messages() {
			// Messages class - prevent instantiation
		}

	}

}
