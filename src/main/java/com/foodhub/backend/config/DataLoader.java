package com.foodhub.backend.config;

import com.foodhub.backend.entity.MenuItem;
import com.foodhub.backend.repository.MenuItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader {

	private final MenuItemRepository menuRepo;

	@PostConstruct
	public void loadData() {
		if (menuRepo.count() == 0) {
			menuRepo.save(MenuItem.builder()
				.name("Margherita Pizza")
				.description("Classic cheese pizza")
				.price(8.99)
				.imageUrl("pizza.jpg")
				.build());

			menuRepo.save(MenuItem.builder()
				.name("Veg Burger")
				.description("Grilled veg patty with lettuce")
				.price(5.49)
				.imageUrl("burger.jpg")
				.build());

			menuRepo.save(MenuItem.builder()
				.name("Pasta Alfredo")
				.description("Creamy white sauce pasta")
				.price(7.25)
				.imageUrl("pasta.jpg")
				.build());
		}
	}

}
