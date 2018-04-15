package com.example.rekrutacjepstrg1.service;

import java.time.LocalDate;

import com.example.rekrutacjepstrg1.domain.Transit;

public interface TransitService {
	Transit createTransit(String sourceAddress, String destinationAddress, double price,
			LocalDate date);
}
