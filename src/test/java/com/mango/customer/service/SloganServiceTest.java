package com.mango.customer.service;

import com.mango.customer.exception.SloganLimitExceededException;
import com.mango.customer.exception.UserNotFoundException;
import com.mango.customer.model.Slogan;
import com.mango.customer.model.User;
import com.mango.customer.repository.SloganRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SloganServiceTest {

	private static final Long USER_ID = 1L;
	private static final User USER_DEMO = new User(USER_ID, "John", "Doe", "123 Arago", "Barcelona", "john.doe@mango.com");
	@Mock
	private SloganRepository sloganRepositoryMock;
	@Mock
	private UserService userService;
	@InjectMocks
	private SloganService sloganService;

	@Test
	public void testCreateSlogan() throws Exception {
		Long userId = 1L;
		when(userService.getUserById(userId)).thenReturn(USER_DEMO);


		List<Slogan> slogans = Arrays.asList(
			new Slogan(1L, "Slogan 1"),
			new Slogan(2L, "Slogan 2")
		);

		Slogan slogan = new Slogan(null, "Test slogan");
		Slogan newSlogan = new Slogan(userId, "Test slogan", USER_DEMO);

		when(sloganRepositoryMock.findByUserId(userId)).thenReturn(slogans);
		when(sloganRepositoryMock.save(slogan)).thenReturn(slogan);

		Slogan createdSlogan = sloganService.createSlogan(userId, slogan);

		assertThat(createdSlogan.getUserId(), is(equalTo(userId)));
		assertThat(createdSlogan.getContent(), is(equalTo(slogan.getContent())));
	}


	@Test
	public void testCreateSloganMoreThanthree() throws Exception {
		Long userId = 1L;
		when(userService.getUserById(userId)).thenReturn(USER_DEMO);

		List<Slogan> slogans = Arrays.asList(
			new Slogan(1L, "Slogan 1"),
			new Slogan(2L, "Slogan 2"),
			new Slogan(3L, "Slogan 3")
		);

		Slogan slogan = new Slogan(null, "Test slogan");

		when(sloganRepositoryMock.findByUserId(userId)).thenReturn(slogans);

		assertThrows(SloganLimitExceededException.class,
			() -> sloganService.createSlogan(userId, slogan)
		);
	}
}
