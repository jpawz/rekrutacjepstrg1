package com.example.rekrutacjepstrg1.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class Transit {
	@Id
	@GeneratedValue
	private long id;

	@JsonProperty("source_address")
	private final String sourceAddress;

	@JsonProperty("destination_address")
	private final String destinationAddress;

	@JsonProperty("price")
	private final double price;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty("date")
	private final LocalDate date;

	@JsonProperty("distance")
	private long distance;

	protected Transit() {
		this.sourceAddress = null;
		this.destinationAddress = null;
		this.price = -1;
		this.date = null;
	}
}
