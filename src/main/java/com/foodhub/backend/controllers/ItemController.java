package com.foodhub.backend.controllers;

import com.foodhub.backend.entity.Item;
import com.foodhub.backend.response.APINormalResponse;
import com.foodhub.backend.response.APIResponder;
import com.foodhub.backend.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@CrossOrigin
public class ItemController {

	private final ItemService itemService;

	private final APIResponder apiResponder;

	@GetMapping
	public ResponseEntity<APINormalResponse<List<Item>>> getMenu() {
		List<Item> items = itemService.getActiveItems();
		return apiResponder.respond(items);
	}

}
