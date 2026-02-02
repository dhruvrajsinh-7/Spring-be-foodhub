package com.foodhub.backend.controllers;

import com.foodhub.backend.dto.CreateOrderRequest;
import com.foodhub.backend.entity.Order;
import com.foodhub.backend.entity.OrderStatus;
import com.foodhub.backend.response.APINormalResponse;
import com.foodhub.backend.response.APIResponder;
import com.foodhub.backend.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {

	private final OrderService orderService;

	private final APIResponder apiResponder;

	// 🛒 Place Order
	@PostMapping
	public ResponseEntity<APINormalResponse<Order>> placeOrder(@Valid @RequestBody CreateOrderRequest request) {
		Order order = orderService.placeOrder(request.getName(), request.getPhone(), request.getEmail(),
				request.getAddress(), request.getItems());
		return apiResponder.respond(order, "Order placed successfully");
	}

	// 📦 Get Order
	@GetMapping("/{id}")
	public ResponseEntity<APINormalResponse<Order>> getOrder(@PathVariable final Long id) {
		Order order = orderService.getOrderById(id);
		return apiResponder.respond(order);
	}

	// 🔄 Update Status
	@PutMapping("/{id}/status")
	public ResponseEntity<APINormalResponse<Order>> updateStatus(@PathVariable final Long id,
			@RequestParam final OrderStatus status) {
		Order order = orderService.updateStatus(id, status);
		return apiResponder.respond(order, "Order status updated successfully");
	}

}
