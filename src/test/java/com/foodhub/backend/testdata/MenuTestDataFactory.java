package com.foodhub.backend.testdata;

import com.foodhub.backend.dto.MenuItemResponseDTO;
import com.foodhub.backend.entity.MenuItem;

import java.util.Arrays;
import java.util.List;

/**
 * Test data factory for Menu-related test objects. Provides factory methods to create
 * consistent test data across all menu tests.
 */
public final class MenuTestDataFactory {

	private MenuTestDataFactory() {
		throw new UnsupportedOperationException("Utility class");
	}

	// ===== Response DTOs =====

	/**
	 * Creates a MenuItemResponseDTO with default test data.
	 */
	public static MenuItemResponseDTO createMenuItemResponse() {
		return MenuItemResponseDTO.builder()
			.id(getDefaultMenuItemId())
			.name("Margherita Pizza")
			.description("Classic cheese pizza")
			.price(8.99)
			.imageUrl("pizza.jpg")
			.build();
	}

	/**
	 * Creates a MenuItemResponseDTO with custom parameters.
	 */
	public static MenuItemResponseDTO createMenuItemResponse(Long id, String name, String description, Double price,
			String imageUrl) {
		return MenuItemResponseDTO.builder()
			.id(id)
			.name(name)
			.description(description)
			.price(price)
			.imageUrl(imageUrl)
			.build();
	}

	/**
	 * Creates a list of MenuItemResponseDTO with default test data.
	 */
	public static List<MenuItemResponseDTO> createMenuItemResponseList() {
		MenuItemResponseDTO item1 = createMenuItemResponse();
		MenuItemResponseDTO item2 = createMenuItemResponse(2L, "Veg Burger", "Grilled veg patty", 5.49, "burger.jpg");
		return Arrays.asList(item1, item2);
	}

	/**
	 * Creates an empty list of MenuItemResponseDTO.
	 */
	public static List<MenuItemResponseDTO> createEmptyMenuItemResponseList() {
		return Arrays.asList();
	}

	// ===== Entity Objects =====

	/**
	 * Creates a MenuItem entity with default test data.
	 */
	public static MenuItem createMenuItem() {
		return MenuItem.builder()
			.id(getDefaultMenuItemId())
			.name("Margherita Pizza")
			.description("Classic cheese pizza")
			.price(8.99)
			.imageUrl("pizza.jpg")
			.build();
	}

	/**
	 * Creates a MenuItem entity with custom parameters.
	 */
	public static MenuItem createMenuItem(Long id, String name, String description, Double price, String imageUrl) {
		return MenuItem.builder().id(id).name(name).description(description).price(price).imageUrl(imageUrl).build();
	}

	/**
	 * Creates a list of MenuItem entities with default test data.
	 */
	public static List<MenuItem> createMenuItemList() {
		MenuItem item1 = createMenuItem();
		MenuItem item2 = createMenuItem(2L, "Veg Burger", "Grilled veg patty", 5.49, "burger.jpg");
		return Arrays.asList(item1, item2);
	}

	/**
	 * Creates an empty list of MenuItem entities.
	 */
	public static List<MenuItem> createEmptyMenuItemList() {
		return Arrays.asList();
	}

	// ===== Constants =====

	/**
	 * Default test menu item ID.
	 */
	public static Long getDefaultMenuItemId() {
		return 1L;
	}

	/**
	 * Default test menu item name.
	 */
	public static String getDefaultMenuItemName() {
		return "Margherita Pizza";
	}

	/**
	 * Default test menu item price.
	 */
	public static Double getDefaultMenuItemPrice() {
		return 8.99;
	}

	// ===== Message Constants (Inner Types Must Be Last) =====

	/**
	 * Message constants for test assertions. Note: Inner types must be placed at the end
	 * to comply with InnerTypeLast checkstyle rule.
	 */
	public static final class Messages {

		public static final String MENU_ITEMS_FETCHED_SUCCESSFULLY = "Menu items fetched successfully";

		/**
		 * Private constructor to prevent instantiation.
		 */
		private Messages() {
			// Messages class - prevent instantiation
		}

	}

}
