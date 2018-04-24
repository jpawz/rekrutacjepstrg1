package com.example.rekrutacjepstrg1.domain;

import java.time.LocalDate;

import com.example.rekrutacjepstrg1.utils.DistanceSerializer;
import com.example.rekrutacjepstrg1.utils.MonthDaySerializer;
import com.example.rekrutacjepstrg1.utils.PriceSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DailyTransit {

	@JsonSerialize(using = MonthDaySerializer.class)
	@JsonProperty("date")
	private final LocalDate date;

	@JsonSerialize(using = DistanceSerializer.class)
	@JsonProperty("total_distance")
	private final Long totalDistance;

	@JsonSerialize(using = DistanceSerializer.class)
	@JsonProperty("avg_distance")
	private final Long avgDistance;

	@JsonSerialize(using = PriceSerializer.class)
	@JsonProperty("avg_price")
	private final Double avgPrice;

}
