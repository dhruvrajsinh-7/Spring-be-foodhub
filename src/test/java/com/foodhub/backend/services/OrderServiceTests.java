package com.foodhub.backend.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import com.foodhub.backend.entity.MenuItem;
import com.foodhub.backend.entity.Item;
import com.foodhub.backend.entity.Order;
import com.foodhub.backend.entity.OrderStatus;
import com.foodhub.backend.entity.User;
import com.foodhub.backend.dto.OrderRequestItem;
import com.foodhub.backend.repository.OrderRepository;
import com.foodhub.backend.testdata.ItemTestDataFactory;
import com.foodhub.backend.testdata.OrderTestDataFactory;
import com.foodhub.backend.testdata.UserTestDataFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
class OrderServiceTests {

	@InjectMocks
	private OrderService orderService;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private ItemService itemService;

	@Mock
	private UserService userService;

	@Mock
	private MenuService menuService;

	@Mock
	private EntityManager entityManager;

	@BeforeEach
	void setUp() {
	}

	@Test
	@DisplayName("Place order should return order with correct total amount")
	void testPlaceOrderValidRequestReturnsOrderWithCorrectTotal() {
		// Given
		String name = OrderTestDataFactory.getDefaultName();
		String phone = OrderTestDataFactory.getDefaultPhone();
		String email = OrderTestDataFactory.getDefaultEmail();
		String address = OrderTestDataFactory.getDefaultAddress();
		List<OrderRequestItem> itemsRequest = OrderTestDataFactory.createOrderRequestItemList();

		User user = UserTestDataFactory.createUser();
		Item item1 = ItemTestDataFactory.createItem();
		Item item2 = ItemTestDataFactory.createItem(2L);
		item2.setPrice(8.99);
		Order savedOrder = OrderTestDataFactory.createOrder();
		MenuItem menuItem1 = new MenuItem();
		MenuItem menuItem2 = new MenuItem();

		given(this.menuService.getMenuItemById(1L)).willReturn(menuItem1);
		given(this.menuService.getMenuItemById(2L)).willReturn(menuItem2);

		given(this.itemService.getOrCreateItemFromMenuItem(menuItem1)).willReturn(item1);
		given(this.itemService.getOrCreateItemFromMenuItem(menuItem2)).willReturn(item2);

		given(this.userService.getOrCreateUser(name, phone, email, address)).willReturn(user);
		given(this.orderRepository.save(any(Order.class))).willReturn(savedOrder);

		// When
		Order result = this.orderService.placeOrder(name, phone, email, address, itemsRequest);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getStatus()).isEqualTo(OrderStatus.ORDER_RECEIVED);
		assertThat(result.getUser()).isNotNull();
		assertThat(result.getUser().getPhone()).isEqualTo(user.getPhone());
		assertThat(result.getUser().getName()).isEqualTo(user.getName());
		then(this.userService).should().getOrCreateUser(name, phone, email, address);
		then(this.menuService).should().getMenuItemById(1L);
		then(this.itemService).should().getOrCreateItemFromMenuItem(menuItem1);
		then(this.orderRepository).should().save(any(Order.class));
	}

	@Test
	@DisplayName("Place order should calculate total amount correctly")
	void testPlaceOrderCalculatesTotalAmountCorrectly() {
		// Given
		String name = OrderTestDataFactory.getDefaultName();
		String phone = OrderTestDataFactory.getDefaultPhone();
		String email = OrderTestDataFactory.getDefaultEmail();
		String address = OrderTestDataFactory.getDefaultAddress();
		OrderRequestItem item1 = OrderTestDataFactory.createOrderRequestItem(1L, 2);
		OrderRequestItem item2 = OrderTestDataFactory.createOrderRequestItem(2L, 3);
		List<OrderRequestItem> itemsRequest = Arrays.asList(item1, item2);

		User user = UserTestDataFactory.createUser();
		Item itemEntity1 = ItemTestDataFactory.createItem();
		itemEntity1.setPrice(10.0);
		Item itemEntity2 = ItemTestDataFactory.createItem(2L);
		itemEntity2.setPrice(5.0);
		Order savedOrder = OrderTestDataFactory.createOrder();
		savedOrder.setTotalAmount(35.0); // (10 * 2) + (5 * 3) = 35
		MenuItem menuItem1 = new MenuItem();
		MenuItem menuItem2 = new MenuItem();

		given(this.menuService.getMenuItemById(1L)).willReturn(menuItem1);
		given(this.menuService.getMenuItemById(2L)).willReturn(menuItem2);

		given(this.itemService.getOrCreateItemFromMenuItem(menuItem1)).willReturn(itemEntity1);
		given(this.itemService.getOrCreateItemFromMenuItem(menuItem2)).willReturn(itemEntity2);

		given(this.userService.getOrCreateUser(name, phone, email, address)).willReturn(user);
		given(this.itemService.getItemById(2L)).willReturn(itemEntity2);
		given(this.orderRepository.save(any(Order.class))).willAnswer(invocation -> {
			Order order = invocation.getArgument(0);
			order.setId(OrderTestDataFactory.getDefaultOrderId());
			return order;
		});

		// When
		Order result = this.orderService.placeOrder(name, phone, email, address, itemsRequest);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getTotalAmount()).isEqualTo(35.0);
		then(this.userService).should().getOrCreateUser(name, phone, email, address);
		then(this.menuService).should().getMenuItemById(1L);
		then(this.itemService).should().getOrCreateItemFromMenuItem(menuItem1);
		then(this.orderRepository).should().save(any(Order.class));
	}

	@Test
	@DisplayName("Place order should throw RuntimeException when item not found")
	void testPlaceOrderItemNotFoundThrowsRuntimeException() {
		// Given
		String name = OrderTestDataFactory.getDefaultName();
		String phone = OrderTestDataFactory.getDefaultPhone();
		String email = OrderTestDataFactory.getDefaultEmail();
		String address = OrderTestDataFactory.getDefaultAddress();
		List<OrderRequestItem> itemsRequest = OrderTestDataFactory.createOrderRequestItemList();

		User user = UserTestDataFactory.createUser();

		given(this.userService.getOrCreateUser(name, phone, email, address)).willReturn(user);
		given(this.menuService.getMenuItemById(anyLong())).willThrow(new RuntimeException("Item not found"));

		// When & Then
		assertThatThrownBy(() -> this.orderService.placeOrder(name, phone, email, address, itemsRequest))
			.isInstanceOf(RuntimeException.class)
			.hasMessageContaining("Item not found");

		then(this.userService).should().getOrCreateUser(name, phone, email, address);
		then(this.menuService).should().getMenuItemById(1L);
		then(this.orderRepository).should(never()).save(any(Order.class));
	}

	@Test
	@DisplayName("Get order by id should return order when found")
	void testGetOrderByIdOrderFoundReturnsOrder() {
		// Given
		Long orderId = OrderTestDataFactory.getDefaultOrderId();
		Order order = OrderTestDataFactory.createOrder();

		given(this.orderRepository.findById(orderId)).willReturn(Optional.of(order));

		// When
		Order result = this.orderService.getOrderById(orderId);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(orderId);
		assertThat(result.getStatus()).isEqualTo(OrderStatus.ORDER_RECEIVED);
		then(this.orderRepository).should().findById(orderId);
	}

	@Test
	@DisplayName("Get order by id should throw RuntimeException when order not found")
	void testGetOrderByIdOrderNotFoundThrowsRuntimeException() {
		// Given
		Long orderId = OrderTestDataFactory.getDefaultOrderId();

		given(this.orderRepository.findById(orderId)).willReturn(Optional.empty());

		// When & Then
		assertThatThrownBy(() -> this.orderService.getOrderById(orderId)).isInstanceOf(RuntimeException.class)
			.hasMessageContaining("Order not found");

		then(this.orderRepository).should().findById(orderId);
	}

	@Test
	@DisplayName("Update status should update order status successfully")
	void testUpdateStatusValidRequestUpdatesStatusSuccessfully() {
		// Given
		Long orderId = OrderTestDataFactory.getDefaultOrderId();
		OrderStatus newStatus = OrderStatus.PREPARING;
		Order existingOrder = OrderTestDataFactory.createOrder();
		Order updatedOrder = OrderTestDataFactory.createOrder();
		updatedOrder.setStatus(newStatus);

		given(this.orderRepository.findById(orderId)).willReturn(Optional.of(existingOrder));
		given(this.orderRepository.save(any(Order.class))).willReturn(updatedOrder);

		// When
		Order result = this.orderService.updateStatus(orderId, newStatus);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getStatus()).isEqualTo(newStatus);
		then(this.orderRepository).should().findById(orderId);
		then(this.orderRepository).should().save(any(Order.class));
	}

	@Test
	@DisplayName("Update status should throw RuntimeException when order not found")
	void testUpdateStatusOrderNotFoundThrowsRuntimeException() {
		// Given
		Long orderId = OrderTestDataFactory.getDefaultOrderId();
		OrderStatus newStatus = OrderStatus.PREPARING;

		given(this.orderRepository.findById(orderId)).willReturn(Optional.empty());

		// When & Then
		assertThatThrownBy(() -> this.orderService.updateStatus(orderId, newStatus))
			.isInstanceOf(RuntimeException.class)
			.hasMessageContaining("Order not found");

		then(this.orderRepository).should().findById(orderId);
		then(this.orderRepository).should(never()).save(any(Order.class));
	}

	@Test
	@DisplayName("Update status should update to DELIVERED status successfully")
	void testUpdateStatusToDeliveredUpdatesSuccessfully() {
		// Given
		Long orderId = OrderTestDataFactory.getDefaultOrderId();
		OrderStatus newStatus = OrderStatus.DELIVERED;
		Order existingOrder = OrderTestDataFactory.createOrder();
		Order updatedOrder = OrderTestDataFactory.createOrder();
		updatedOrder.setStatus(newStatus);

		given(this.orderRepository.findById(orderId)).willReturn(Optional.of(existingOrder));
		given(this.orderRepository.save(any(Order.class))).willReturn(updatedOrder);

		// When
		Order result = this.orderService.updateStatus(orderId, newStatus);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getStatus()).isEqualTo(OrderStatus.DELIVERED);
		then(this.orderRepository).should().findById(orderId);
		then(this.orderRepository).should().save(any(Order.class));
	}

	@Test
	@DisplayName("Update status should update to CANCELLED status successfully")
	void testUpdateStatusToCancelledUpdatesSuccessfully() {
		// Given
		Long orderId = OrderTestDataFactory.getDefaultOrderId();
		OrderStatus newStatus = OrderStatus.CANCELLED;
		Order existingOrder = OrderTestDataFactory.createOrder();
		Order updatedOrder = OrderTestDataFactory.createOrder();
		updatedOrder.setStatus(newStatus);

		given(this.orderRepository.findById(orderId)).willReturn(Optional.of(existingOrder));
		given(this.orderRepository.save(any(Order.class))).willReturn(updatedOrder);

		// When
		Order result = this.orderService.updateStatus(orderId, newStatus);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getStatus()).isEqualTo(OrderStatus.CANCELLED);
		then(this.orderRepository).should().findById(orderId);
		then(this.orderRepository).should().save(any(Order.class));
	}

}
