package com.foodhub.backend.testdata;

import com.foodhub.backend.entity.User;

import java.util.Optional;

/**
 * Test data factory for User-related test objects. Provides factory methods to create
 * consistent test data across all user tests.
 */
public final class UserTestDataFactory {

	private UserTestDataFactory() {
		throw new UnsupportedOperationException("Utility class");
	}

	// ===== Entity Objects =====

	/**
	 * Creates a User entity with default test data.
	 */
	public static User createUser() {
		User user = new User();
		user.setId(getDefaultUserId());
		user.setName("John Doe");
		user.setPhone("1234567890");
		user.setEmail("john.doe@example.com");
		user.setAddress("123 Main St");
		return user;
	}

	/**
	 * Creates a User entity with custom parameters.
	 */
	public static User createUser(String name, String phone, String email, String address) {
		User user = new User();
		user.setName(name);
		user.setPhone(phone);
		user.setEmail(email);
		user.setAddress(address);
		return user;
	}

	/**
	 * Creates a User entity with custom ID.
	 */
	public static User createUser(Long id) {
		User user = createUser();
		user.setId(id);
		return user;
	}

	/**
	 * Creates an Optional containing a User with default test data.
	 */
	public static Optional<User> createOptionalUser() {
		return Optional.of(createUser());
	}

	/**
	 * Creates an empty Optional for User.
	 */
	public static Optional<User> createEmptyOptionalUser() {
		return Optional.empty();
	}

	// ===== Constants =====

	/**
	 * Default test user ID.
	 */
	public static Long getDefaultUserId() {
		return 1L;
	}

	/**
	 * Default test user name.
	 */
	public static String getDefaultUserName() {
		return "John Doe";
	}

	/**
	 * Default test phone number.
	 */
	public static String getDefaultPhone() {
		return "1234567890";
	}

	/**
	 * Default test email.
	 */
	public static String getDefaultEmail() {
		return "john.doe@example.com";
	}

	/**
	 * Default test address.
	 */
	public static String getDefaultAddress() {
		return "123 Main St";
	}

	// ===== Message Constants (Inner Types Must Be Last) =====

	/**
	 * Message constants for test assertions. Note: Inner types must be placed at the end
	 * to comply with InnerTypeLast checkstyle rule.
	 */
	public static final class Messages {

		public static final String USER_CREATED_SUCCESSFULLY = "User created successfully";

		public static final String USER_FETCHED_SUCCESSFULLY = "User fetched successfully";

		/**
		 * Private constructor to prevent instantiation.
		 */
		private Messages() {
			// Messages class - prevent instantiation
		}

	}

}
