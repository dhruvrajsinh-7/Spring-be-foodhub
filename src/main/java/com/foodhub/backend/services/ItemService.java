package com.foodhub.backend.services;

import com.foodhub.backend.entity.Item;
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

}
