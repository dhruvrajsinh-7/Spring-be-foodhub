package com.foodhub.backend.services;

import com.foodhub.backend.entity.Item;
import com.foodhub.backend.entity.MenuItem;
import com.foodhub.backend.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepo;

	public List<Item> getActiveItems() {
		return itemRepo.findByActiveTrue();
	}

	public Item getItemById(Long id) {
		return itemRepo.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
	}

	/**
	 * Gets or creates an Item from a MenuItem. First tries to find Item by MenuItem ID,
	 * then by name if not found. Creates a new Item from MenuItem data if no matching
	 * Item exists.
	 */
	public Item getOrCreateItemFromMenuItem(MenuItem menuItem) {
		// First try to find by ID (in case MenuItem and Item share IDs)
		return itemRepo.findById(menuItem.getId()).orElseGet(() -> {
			// If not found by ID, try to find by name
			return itemRepo.findByName(menuItem.getName()).orElseGet(() -> {
				// If not found by name either, create new Item from MenuItem
				Item item = new Item();
				item.setName(menuItem.getName());
				item.setDescription(menuItem.getDescription());
				item.setPrice(menuItem.getPrice());
				item.setImageUrl(menuItem.getImageUrl());
				item.setActive(true);
				return itemRepo.save(item);
			});
		});
	}

}
