package com.example.rekrutacjepstrg1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.rekrutacjepstrg1.domain.Transit;
import com.example.rekrutacjepstrg1.repository.TransitRepository;

public class ReportsServiceImplTest {

	private ReportsServiceImpl reportsServiceImpl;

	@Mock
	private TransitRepository transitRepository;

	private LocalDate now = LocalDate.now();
	private LocalDate startDate = LocalDate.of(now.getYear(), now.getMonth(), 10);
	private LocalDate endDate = LocalDate.of(now.getYear(), now.getMonth(), 25);
	private Transit transit1, transit2;
	private long distance1 = 789, distance2 = 654;
	private String totalDistance = (distance1 + distance2) + "km";
	private double price1 = 123, price2 = 321;
	private String totalPrice = (price1 + price2) + "PLN";

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		reportsServiceImpl = new ReportsServiceImpl(transitRepository);
		transit1 = new Transit("any", "any", price1,
				LocalDate.of(now.getYear(), now.getMonth(), 11));
		transit1.setDistance(distance1);
		transit2 = new Transit("any", "any", price2,
				LocalDate.of(now.getYear(), now.getMonth(), 19));
		transit2.setDistance(distance2);
	}

	@Test
	public void checkDailyReport() {
		given(transitRepository.findByDateBetween(startDate, endDate))
				.willReturn(Arrays.asList(transit1, transit2));

		Map<String, String> dailyReport = reportsServiceImpl.getDailyReport(startDate,
				endDate);

		assertThat(dailyReport).containsOnlyKeys("total_distance", "total_price");
		assertThat(dailyReport.get("total_distance")).isEqualTo(totalDistance);
		assertThat(dailyReport.get("total_price")).isEqualTo(totalPrice);
	}

}
