package com.foodhub.backend.testdata;

import com.foodhub.backend.entity.Item;

import java.util.Arrays;
import java.util.List;

/**
 * Test data factory for Item-related test objects. Provides factory methods to create
 * consistent test data across all item tests.
 */
public final class ItemTestDataFactory {

	private ItemTestDataFactory() {
		throw new UnsupportedOperationException("Utility class");
	}

	// ===== Entity Objects =====

	/**
	 * Creates an Item entity with default test data.
	 */
	public static Item createItem() {
		Item item = new Item();
		item.setId(getDefaultItemId());
		item.setName("Pizza");
		item.setDescription("Delicious pizza");
		item.setPrice(12.99);
		item.setImageUrl("pizza.jpg");
		item.setActive(true);
		return item;
	}

	/**
	 * Creates an Item entity with custom ID.
	 */
	public static Item createItem(Long id) {
		Item item = createItem();
		item.setId(id);
		return item;
	}

	/**
	 * Creates an inactive Item entity.
	 */
	public static Item createInactiveItem() {
		Item item = createItem();
		item.setActive(false);
		return item;
	}

	/**
	 * Creates a list of active Item entities with default test data.
	 */
	public static List<Item> createActiveItemList() {
		Item item1 = createItem();
		Item item2 = createItem(2L);
		item2.setName("Burger");
		item2.setPrice(8.99);
		return Arrays.asList(item1, item2);
	}

	/**
	 * Creates an empty list of Item entities.
	 */
	public static List<Item> createEmptyItemList() {
		return Arrays.asList();
	}

	// ===== Constants =====

	/**
	 * Default test item ID.
	 */
	public static Long getDefaultItemId() {
		return 1L;
	}

	/**
	 * Default test item name.
	 */
	public static String getDefaultItemName() {
		return "Pizza";
	}

	/**
	 * Default test item price.
	 */
	public static Double getDefaultItemPrice() {
		return 12.99;
	}

	// ===== Message Constants (Inner Types Must Be Last) =====

	/**
	 * Message constants for test assertions. Note: Inner types must be placed at the end
	 * to comply with InnerTypeLast checkstyle rule.
	 */
	public static final class Messages {

		public static final String ITEM_NOT_FOUND = "Item not found";

		public static final String ITEMS_FETCHED_SUCCESSFULLY = "Items fetched successfully";

		/**
		 * Private constructor to prevent instantiation.
		 */
		private Messages() {
			// Messages class - prevent instantiation
		}

	}

}
