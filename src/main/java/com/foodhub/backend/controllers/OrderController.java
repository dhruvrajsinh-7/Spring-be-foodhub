package com.foodhub.backend.controllers;

import com.foodhub.backend.dto.CreateOrderRequest;
import com.foodhub.backend.entity.Order;
import com.foodhub.backend.entity.OrderStatus;
import com.foodhub.backend.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

	// 🛒 Place Order
	@PostMapping
	public Order placeOrder(@Valid @RequestBody CreateOrderRequest request) {
		return orderService.placeOrder(request.getName(), request.getPhone(), request.getEmail(), request.getAddress(),
				request.getItems());
	}

	// 📦 Get Order
	@GetMapping("/{id}")
	public Order getOrder(@PathVariable final Long id) {
		return orderService.getOrderById(id);
	}

	// 🔄 Update Status
	@PutMapping("/{id}/status")
	public Order updateStatus(@PathVariable final Long id, @RequestParam final OrderStatus status) {
		return orderService.updateStatus(id, status);
	}

}
