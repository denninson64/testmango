package com.mango.customer.service;

import com.mango.customer.exception.UserNotFoundException;
import com.mango.customer.model.User;
import com.mango.customer.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	private static final Long USER_ID = 1L;
	private static final User USER_DEMO = new User(USER_ID, "John", "Doe", "123 Arago", "Barcelona", "john.doe@mango.com");
	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private UserService userService;

	@Test
	public void testGetUserById() {

		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER_DEMO));

		User result = userService.getUserById(USER_ID);

		assertNotNull(result);
		assertEquals(USER_ID, result.getId());
		assertEquals(USER_DEMO, result);
	}

	@Test
	public void testGetUserByIdNotFound() {
		when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class,
			() -> userService.getUserById(USER_ID)
		);
	}

	@Test
	public void testCreateUser() {

		when(userRepository.save(USER_DEMO)).thenReturn(USER_DEMO);

		User result = userService.createUser(USER_DEMO);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals(USER_DEMO, result);
	}

	@Test
	public void testUpdateUser() {

		User updateUser = new User();
		updateUser.setId(USER_ID);
		updateUser.setName("Jose");
		updateUser.setLastName("Diaz");
		updateUser.setAddress("456 Meridiana");
		updateUser.setCity("Girona");
		updateUser.setEmail("josedias@mango.com");

		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER_DEMO));
		when(userRepository.save(updateUser)).thenReturn(updateUser);

		User result = userService.updateUser(USER_ID, updateUser);

		assertNotNull(result);
		assertEquals(USER_ID, result.getId());
		assertEquals(updateUser, result);
	}

	@Test
	public void testUpdateUserNotFound() {
		when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class,
			() -> userService.updateUser(USER_ID, USER_DEMO)
		);
	}

	@Test
	public void testDeleteUser() throws UserNotFoundException {
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER_DEMO));

		userService.deleteUser(USER_ID);

		verify(userRepository).deleteById(USER_ID);
	}

	@Test
	public void testDeleteUserNotFound() throws UserNotFoundException {
		when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class,
			() -> userService.deleteUser(USER_ID)
		);
	}

}
