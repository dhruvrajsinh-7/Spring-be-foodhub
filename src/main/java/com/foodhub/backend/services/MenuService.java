package com.foodhub.backend.services;

import com.foodhub.backend.dto.MenuItemResponseDTO;
import com.foodhub.backend.entity.MenuItem;
import com.foodhub.backend.mapper.MenuItemMapper;
import com.foodhub.backend.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

	private final MenuItemRepository menuRepo;

	public List<MenuItemResponseDTO> getAllMenuItems() {
		final List<MenuItem> items = menuRepo.findAll();

		return items.stream().map(MenuItemMapper::toDTO).collect(Collectors.toList());
	}

	public MenuItem getMenuItemById(Long id) {
		return menuRepo.findById(id).orElseThrow(() -> new RuntimeException("MenuItem not found"));
	}

}
