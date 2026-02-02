package com.foodhub.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestItem {

	@NotNull(message = "Item ID required")
	private Long itemId;

	@Min(value = 1, message = "Quantity must be at least 1")
	private Integer quantity;

}
