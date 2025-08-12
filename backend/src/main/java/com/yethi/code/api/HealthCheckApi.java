package com.yethi.code.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckApi {

	@GetMapping("/health-check")
	public ResponseEntity<String> getHealth() {
		return ResponseEntity.ok("Api working 8080");

	}

	

}
