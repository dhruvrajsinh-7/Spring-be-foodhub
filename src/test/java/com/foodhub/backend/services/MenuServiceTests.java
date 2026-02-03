package com.foodhub.backend.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.foodhub.backend.dto.MenuItemResponseDTO;
import com.foodhub.backend.entity.MenuItem;
import com.foodhub.backend.repository.MenuItemRepository;
import com.foodhub.backend.testdata.MenuTestDataFactory;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuServiceTests {

	@InjectMocks
	private MenuService menuService;

	@Mock
	private MenuItemRepository menuItemRepository;

	@BeforeEach
	void setUp() {
		// Common setup if needed
	}

	@Test
	@DisplayName("Get all menu items should return list of menu item DTOs")
	void testGetAllMenuItemsReturnsMenuItemDTOList() {
		// Given
		List<MenuItem> menuItems = MenuTestDataFactory.createMenuItemList();
		List<MenuItemResponseDTO> expectedDTOs = MenuTestDataFactory.createMenuItemResponseList();

		given(this.menuItemRepository.findAll()).willReturn(menuItems);

		// When
		List<MenuItemResponseDTO> result = this.menuService.getAllMenuItems();

		// Then
		assertThat(result).isNotNull();
		assertThat(result).hasSize(2);
		assertThat(result.get(0).getId()).isEqualTo(MenuTestDataFactory.getDefaultMenuItemId());
		assertThat(result.get(0).getName()).isEqualTo(MenuTestDataFactory.getDefaultMenuItemName());
		then(this.menuItemRepository).should().findAll();
	}

	@Test
	@DisplayName("Get all menu items should return empty list when no items")
	void testGetAllMenuItemsNoItemsReturnsEmptyList() {
		// Given
		List<MenuItem> emptyList = MenuTestDataFactory.createEmptyMenuItemList();

		given(this.menuItemRepository.findAll()).willReturn(emptyList);

		// When
		List<MenuItemResponseDTO> result = this.menuService.getAllMenuItems();

		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEmpty();
		then(this.menuItemRepository).should().findAll();
	}

	@Test
	@DisplayName("Get all menu items should map entity to DTO correctly")
	void testGetAllMenuItemsMapsEntityToDTOCorrectly() {
		// Given
		MenuItem menuItem = MenuTestDataFactory.createMenuItem();
		List<MenuItem> menuItems = List.of(menuItem);

		given(this.menuItemRepository.findAll()).willReturn(menuItems);

		// When
		List<MenuItemResponseDTO> result = this.menuService.getAllMenuItems();

		// Then
		assertThat(result).isNotNull();
		assertThat(result).hasSize(1);
		MenuItemResponseDTO dto = result.get(0);
		assertThat(dto.getId()).isEqualTo(menuItem.getId());
		assertThat(dto.getName()).isEqualTo(menuItem.getName());
		assertThat(dto.getDescription()).isEqualTo(menuItem.getDescription());
		assertThat(dto.getPrice()).isEqualTo(menuItem.getPrice());
		assertThat(dto.getImageUrl()).isEqualTo(menuItem.getImageUrl());
		then(this.menuItemRepository).should().findAll();
	}

}
