package com.yethi.code.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yethi.code.domain.User;
import com.yethi.code.service.UserService;

@RestController
public class HealthCheckApi {

	@Autowired
	private UserService userService;

	@GetMapping("/health-check")
	public ResponseEntity<String> getHealth() {
		return ResponseEntity.ok("Api working 8080");

	}

	@PostMapping("/user")
	public ResponseEntity<User> addUser(@RequestBody User use) {
		return ResponseEntity.ok(userService.adduserUser(use));
	}

	@GetMapping("/user")
	public ResponseEntity<List<User>> getlist() {
		return ResponseEntity.ok(userService.getUser());
	}

}
