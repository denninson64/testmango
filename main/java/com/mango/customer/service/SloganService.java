package com.mango.customer.service;

import com.mango.customer.exception.SloganLimitExceededException;
import com.mango.customer.exception.SloganNotFoundException;
import com.mango.customer.model.Slogan;
import com.mango.customer.model.User;
import com.mango.customer.repository.SloganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SloganService {
	public static final String SLOGAN_NOT_FOUND_ERROR_MSG = "Slogan not found with id ";

	@Autowired
	private SloganRepository sloganRepository;

	@Autowired
	private UserService userService;


	public Slogan createSlogan(Long userId, Slogan slogan) throws SloganLimitExceededException {
		User user = userService.getUserById(userId);
		List<Slogan> userSlogans = sloganRepository.findByUserId(userId);
		if (userSlogans.size() >= 3) {
			throw new SloganLimitExceededException();
		}
		slogan.setUser(user);
		slogan.setUserId(userId);
		return sloganRepository.save(slogan);
	}

	public Slogan getSloganById(Long id) {
		Optional<Slogan> slogan = sloganRepository.findById(id);
		if (slogan.isPresent()) {
			return slogan.get();
		} else {
			throw new SloganNotFoundException(SLOGAN_NOT_FOUND_ERROR_MSG + id);
		}
	}

	public List<Slogan> findByUserId(Long userId) {
		return sloganRepository.findByUserId(userId);
	}


	public Slogan updateSlogan(Long id, Slogan sloganDetails) {
		Slogan slogan = sloganRepository.findById(id)
			.orElseThrow(() -> new SloganNotFoundException(SLOGAN_NOT_FOUND_ERROR_MSG + id));

		slogan.setContent(sloganDetails.getContent());

		return sloganRepository.save(slogan);
	}

	public void deleteSlogan(Long id) {
		Optional<Slogan> slogan = sloganRepository.findById(id);
		if (slogan.isPresent()) {
			sloganRepository.deleteById(id);
		} else {
			throw new SloganNotFoundException(SLOGAN_NOT_FOUND_ERROR_MSG + id);
		}
	}
}
