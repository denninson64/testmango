package com.mango.customer.service;


import com.mango.customer.exception.UserNotFoundException;
import com.mango.customer.model.User;
import com.mango.customer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	public static final String USER_NOT_FOUND_ERROR_MSG = "User not found with id ";
	@Autowired
	private UserRepository userRepository;

	public User getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UserNotFoundException(USER_NOT_FOUND_ERROR_MSG + id);
		}
	}

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public User updateUser(Long id, User userDetails) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ERROR_MSG + id));

		user.setName(userDetails.getName());
		user.setLastName(userDetails.getLastName());
		user.setEmail(userDetails.getEmail());
		user.setAddress(userDetails.getAddress());
		user.setCity(userDetails.getCity());
		return userRepository.save(user);
	}

	public void deleteUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			userRepository.deleteById(id);
		} else {
			throw new UserNotFoundException(USER_NOT_FOUND_ERROR_MSG + id);
		}
	}
}
