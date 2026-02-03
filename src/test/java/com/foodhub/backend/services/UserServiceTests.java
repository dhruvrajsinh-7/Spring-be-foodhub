package com.foodhub.backend.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;

import com.foodhub.backend.entity.User;
import com.foodhub.backend.repository.UserRepository;
import com.foodhub.backend.testdata.UserTestDataFactory;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		// Common setup if needed
	}

	@Test
	@DisplayName("Get or create user should return existing user when found")
	void testGetOrCreateUserExistingUserReturnsUser() {
		// Given
		String name = UserTestDataFactory.getDefaultUserName();
		String phone = UserTestDataFactory.getDefaultPhone();
		String email = UserTestDataFactory.getDefaultEmail();
		String address = UserTestDataFactory.getDefaultAddress();
		User existingUser = UserTestDataFactory.createUser();

		given(this.userRepository.findByPhone(phone)).willReturn(Optional.of(existingUser));

		// When
		User result = this.userService.getOrCreateUser(name, phone, email, address);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(UserTestDataFactory.getDefaultUserId());
		assertThat(result.getPhone()).isEqualTo(phone);
		then(this.userRepository).should().findByPhone(phone);
		then(this.userRepository).should(never()).save(any(User.class));
	}

	@Test
	@DisplayName("Get or create user should create new user when not found")
	void testGetOrCreateUserUserNotFoundCreatesNewUser() {
		// Given
		String name = UserTestDataFactory.getDefaultUserName();
		String phone = UserTestDataFactory.getDefaultPhone();
		String email = UserTestDataFactory.getDefaultEmail();
		String address = UserTestDataFactory.getDefaultAddress();
		User newUser = UserTestDataFactory.createUser();

		given(this.userRepository.findByPhone(phone)).willReturn(Optional.empty());
		given(this.userRepository.save(any(User.class))).willReturn(newUser);

		// When
		User result = this.userService.getOrCreateUser(name, phone, email, address);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo(name);
		assertThat(result.getPhone()).isEqualTo(phone);
		assertThat(result.getEmail()).isEqualTo(email);
		assertThat(result.getAddress()).isEqualTo(address);
		then(this.userRepository).should().findByPhone(phone);
		then(this.userRepository).should().save(any(User.class));
	}

	@Test
	@DisplayName("Get or create user should set correct user details when creating")
	void testGetOrCreateUserSetsCorrectUserDetails() {
		// Given
		String name = "Jane Smith";
		String phone = "9876543210";
		String email = "jane.smith@example.com";
		String address = "456 Oak Ave";
		User newUser = UserTestDataFactory.createUser(name, phone, email, address);
		newUser.setId(UserTestDataFactory.getDefaultUserId());

		given(this.userRepository.findByPhone(phone)).willReturn(Optional.empty());
		given(this.userRepository.save(any(User.class))).willAnswer(invocation -> {
			User user = invocation.getArgument(0);
			user.setId(UserTestDataFactory.getDefaultUserId());
			return user;
		});

		// When
		User result = this.userService.getOrCreateUser(name, phone, email, address);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo(name);
		assertThat(result.getPhone()).isEqualTo(phone);
		assertThat(result.getEmail()).isEqualTo(email);
		assertThat(result.getAddress()).isEqualTo(address);
		then(this.userRepository).should().findByPhone(phone);
		then(this.userRepository).should().save(any(User.class));
	}

	@Test
	@DisplayName("Get or create user should return existing user with different details")
	void testGetOrCreateUserExistingUserIgnoresNewDetails() {
		// Given
		String name = "Different Name";
		String phone = UserTestDataFactory.getDefaultPhone();
		String email = "different@example.com";
		String address = "Different Address";
		User existingUser = UserTestDataFactory.createUser();

		given(this.userRepository.findByPhone(phone)).willReturn(Optional.of(existingUser));

		// When
		User result = this.userService.getOrCreateUser(name, phone, email, address);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getPhone()).isEqualTo(phone);
		assertThat(result.getName()).isEqualTo(existingUser.getName());
		assertThat(result.getEmail()).isEqualTo(existingUser.getEmail());
		assertThat(result.getAddress()).isEqualTo(existingUser.getAddress());
		then(this.userRepository).should().findByPhone(phone);
		then(this.userRepository).should(never()).save(any(User.class));
	}

}
