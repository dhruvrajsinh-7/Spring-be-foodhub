package com.foodhub.backend.services;

import com.foodhub.backend.entity.User;
import com.foodhub.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepo;

	public User getOrCreateUser(String name, String phone, String email, String address) {
		final User user = userRepo.findByPhone(phone).orElseGet(() -> {
			final User newUser = new User();
			newUser.setName(name);
			newUser.setPhone(phone);
			newUser.setEmail(email);
			newUser.setAddress(address);
			return userRepo.save(newUser);
		});
		return user;
	}

}
