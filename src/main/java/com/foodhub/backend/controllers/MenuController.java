package com.foodhub.backend.controllers;

import com.foodhub.backend.dto.MenuItemResponseDTO;
import com.foodhub.backend.response.APINormalResponse;
import com.foodhub.backend.response.APIResponder;
import com.foodhub.backend.services.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
@CrossOrigin
public class MenuController {

	private final MenuService menuService;

	private final APIResponder apiResponder;

	@GetMapping
	public ResponseEntity<APINormalResponse<List<MenuItemResponseDTO>>> getMenu() {
		List<MenuItemResponseDTO> menuItems = menuService.getAllMenuItems();
		return apiResponder.respond(menuItems);
	}

}
