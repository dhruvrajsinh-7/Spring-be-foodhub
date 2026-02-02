package com.foodhub.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Phone is required")
	private String phone;

	@Email(message = "Invalid email format")
	private String email; // Optional field

	@NotBlank(message = "Address is required")
	private String address;

	@NotEmpty(message = "Order must contain items")
	private List<OrderRequestItem> items;

}
