package com.example.rekrutacjepstrg1.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Transit {
	@JsonProperty("source_address")
	private final String sourceAddress;
	@JsonProperty("destination_address")
	private final String destinationAddress;
	@JsonProperty("price")
	private final double price;
	@JsonProperty("date")
	private final String date;

	public Transit() {
		this.sourceAddress = null;
		this.destinationAddress = null;
		this.price = -1;
		this.date = null;
	}
}
