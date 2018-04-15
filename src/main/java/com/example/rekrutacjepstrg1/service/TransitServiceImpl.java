package com.example.rekrutacjepstrg1.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.rekrutacjepstrg1.domain.Transit;

@Service
public class TransitServiceImpl implements TransitService {

	@Override
	public Transit createTransit(String sourceAddress, String destinationAddress,
			double price, LocalDate date) {
		return null;
	}

}
