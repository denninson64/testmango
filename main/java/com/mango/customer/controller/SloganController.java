package com.mango.customer.controller;

import com.mango.customer.exception.SloganLimitExceededException;
import com.mango.customer.exception.SloganNotFoundException;
import com.mango.customer.exception.UserNotFoundException;
import com.mango.customer.model.Slogan;
import com.mango.customer.service.SloganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/slogans")
public class SloganController {

	@Autowired
	private SloganService sloganService;

	@PostMapping("")
	public ResponseEntity<?> createSlogan(@PathVariable("userId") Long userId, @RequestBody Slogan slogan) {
		try {
			Slogan createdSlogan = sloganService.createSlogan(userId, slogan);
			return new ResponseEntity<>(createdSlogan, HttpStatus.CREATED);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (SloganLimitExceededException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can't create more than 3 slogans.");
		}
	}

	@GetMapping("")
	public ResponseEntity<?> getSlogansByUserId(@PathVariable("userId") Long userId) {
		try {
			List<Slogan> slogans = sloganService.findByUserId(userId);
			return ResponseEntity.ok(slogans);
		} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{sloganId}")
	public ResponseEntity<?> getSlogan(@PathVariable("sloganId") Long sloganId) {
		try {
			Slogan slogan = sloganService.getSloganById(sloganId);
			return ResponseEntity.ok(slogan);
		} catch (SloganNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{sloganId}")
	public ResponseEntity<?> updateSlogan(@PathVariable("sloganId") Long sloganId,
										  @RequestBody Slogan updatedSlogan) {
		try {
			Slogan slogan = sloganService.updateSlogan(sloganId, updatedSlogan);
			return ResponseEntity.ok(slogan);
		} catch (SloganNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{sloganId}")
	public ResponseEntity<?> deleteSlogan(@PathVariable("userId") Long userId,
										  @PathVariable("sloganId") Long sloganId) {
		try {
			sloganService.deleteSlogan(sloganId);
			return ResponseEntity.noContent().build();
		} catch (SloganNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
