package com.foodhub.backend.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;

import com.foodhub.backend.entity.Item;
import com.foodhub.backend.repository.ItemRepository;
import com.foodhub.backend.testdata.ItemTestDataFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemServiceTests {

	@InjectMocks
	private ItemService itemService;

	@Mock
	private ItemRepository itemRepository;

	@BeforeEach
	void setUp() {
		// Common setup if needed
	}

	@Test
	@DisplayName("Get active items should return list of active items")
	void testGetActiveItemsReturnsActiveItemsList() {
		// Given
		List<Item> activeItems = ItemTestDataFactory.createActiveItemList();

		given(this.itemRepository.findByActiveTrue()).willReturn(activeItems);

		// When
		List<Item> result = this.itemService.getActiveItems();

		// Then
		assertThat(result).isNotNull();
		assertThat(result).hasSize(2);
		assertThat(result).allMatch(Item::getActive);
		then(this.itemRepository).should().findByActiveTrue();
	}

	@Test
	@DisplayName("Get active items should return empty list when no active items")
	void testGetActiveItemsNoActiveItemsReturnsEmptyList() {
		// Given
		List<Item> emptyList = ItemTestDataFactory.createEmptyItemList();

		given(this.itemRepository.findByActiveTrue()).willReturn(emptyList);

		// When
		List<Item> result = this.itemService.getActiveItems();

		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEmpty();
		then(this.itemRepository).should().findByActiveTrue();
	}

	@Test
	@DisplayName("Get item by id should return item when found")
	void testGetItemByIdItemFoundReturnsItem() {
		// Given
		Long itemId = ItemTestDataFactory.getDefaultItemId();
		Item item = ItemTestDataFactory.createItem();

		given(this.itemRepository.findById(itemId)).willReturn(Optional.of(item));

		// When
		Item result = this.itemService.getItemById(itemId);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(itemId);
		assertThat(result.getName()).isEqualTo(ItemTestDataFactory.getDefaultItemName());
		then(this.itemRepository).should().findById(itemId);
	}

	@Test
	@DisplayName("Get item by id should throw RuntimeException when item not found")
	void testGetItemByIdItemNotFoundThrowsRuntimeException() {
		// Given
		Long itemId = ItemTestDataFactory.getDefaultItemId();

		given(this.itemRepository.findById(itemId)).willReturn(Optional.empty());

		// When & Then
		assertThatThrownBy(() -> this.itemService.getItemById(itemId)).isInstanceOf(RuntimeException.class)
			.hasMessageContaining("Item not found");

		then(this.itemRepository).should().findById(itemId);
	}

	@Test
	@DisplayName("Get item by id should return correct item details")
	void testGetItemByIdReturnsCorrectItemDetails() {
		// Given
		Long itemId = ItemTestDataFactory.getDefaultItemId();
		Item item = ItemTestDataFactory.createItem();
		item.setName("Special Pizza");
		item.setPrice(15.99);
		item.setDescription("Premium pizza");

		given(this.itemRepository.findById(itemId)).willReturn(Optional.of(item));

		// When
		Item result = this.itemService.getItemById(itemId);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(itemId);
		assertThat(result.getName()).isEqualTo("Special Pizza");
		assertThat(result.getPrice()).isEqualTo(15.99);
		assertThat(result.getDescription()).isEqualTo("Premium pizza");
		then(this.itemRepository).should().findById(itemId);
	}

}
