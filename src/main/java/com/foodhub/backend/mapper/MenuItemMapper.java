package com.foodhub.backend.mapper;

import com.foodhub.backend.dto.MenuItemResponseDTO;
import com.foodhub.backend.entity.MenuItem;

public class MenuItemMapper {

	public static MenuItemResponseDTO toDTO(MenuItem item) {
		return MenuItemResponseDTO.builder()
			.id(item.getId())
			.name(item.getName())
			.description(item.getDescription())
			.price(item.getPrice())
			.imageUrl(item.getImageUrl())
			.build();
	}

}
