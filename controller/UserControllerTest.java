package com.mango.customer.controller;

import com.mango.customer.exception.UserNotFoundException;
import com.mango.customer.model.User;
import com.mango.customer.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
	private static final Long USER_ID = 1L;
	private static final User USER_DEMO = new User(USER_ID, "John", "Doe", "123 Arago", "Barcelona", "john.doe@mango.com");
	@Autowired
	private UserController userController;
	@MockBean
	private UserService userService;

	@Test
	public void testGetUserById() {
		when(userService.getUserById(USER_ID)).thenReturn(USER_DEMO);

		ResponseEntity<User> response = userController.getUserById(USER_ID);
		User returnedUser = response.getBody();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(USER_DEMO, returnedUser);
	}

	@Test
	public void testGetUserByIdNotFound() {
		when(userService.getUserById(USER_ID)).thenThrow(new UserNotFoundException());

		ResponseEntity<User> response = userController.getUserById(USER_ID);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testCreateUser() {
		when(userService.createUser(USER_DEMO)).thenReturn(USER_DEMO);

		ResponseEntity<User> response = userController.createUser(USER_DEMO);
		User createdUser = response.getBody();

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(USER_DEMO, createdUser);
	}

	@Test
	public void testUpdateUser() {
		when(userService.updateUser(USER_ID, USER_DEMO)).thenReturn(USER_DEMO);

		ResponseEntity<User> response = userController.updateUser(USER_DEMO.getId(), USER_DEMO);
		User updatedUser = response.getBody();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(USER_DEMO, updatedUser);
	}

	@Test
	public void testUpdateUserNotFound() {
		when(userService.updateUser(USER_ID, USER_DEMO)).thenThrow(new UserNotFoundException());

		ResponseEntity<User> response = userController.updateUser(USER_ID, USER_DEMO);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testDeleteUser() {
		ResponseEntity<Void> response = userController.deleteUser(USER_DEMO.getId());

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(userService, times(1)).deleteUser(USER_DEMO.getId());
	}

	@Test
	public void testDeleteUserNotFound() {
		doThrow(new UserNotFoundException("User not found with id " + USER_DEMO.getId())).when(userService).deleteUser(USER_DEMO.getId());

		ResponseEntity<Void> response = userController.deleteUser(USER_DEMO.getId());

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		verify(userService, times(1)).deleteUser(USER_DEMO.getId());
	}

}
