package com.example.rekrutacjepstrg1.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Transit {
	private final String sourceAddress;
	private final String destinationAddress;
	private final double price;
	private final String date;

	public Transit() {
		this.sourceAddress = null;
		this.destinationAddress = null;
		this.price = -1;
		this.date = null;
	}
}
