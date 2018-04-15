package com.example.rekrutacjepstrg1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rekrutacjepstrg1.domain.Transit;

@RestController
@RequestMapping("transits")
public class TransitController {

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Transit> addTransit(@RequestBody Transit transit) {
		return new ResponseEntity<Transit>(transit, HttpStatus.CREATED);
	}
}
