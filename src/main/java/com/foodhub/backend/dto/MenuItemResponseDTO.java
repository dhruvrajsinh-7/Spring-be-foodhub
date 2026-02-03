package com.foodhub.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemResponseDTO {

	private Long id;

	private String name;

	private String description;

	private Double price;

	private String imageUrl;

}
