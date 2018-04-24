package com.example.rekrutacjepstrg1.controller;

import static org.hamcrest.Matchers.hasKey;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.rekrutacjepstrg1.domain.DailyTransit;
import com.example.rekrutacjepstrg1.domain.Transit;
import com.example.rekrutacjepstrg1.service.ReportsService;
import com.example.rekrutacjepstrg1.utils.DayNumberSuffix;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ReportsController.class)
public class ReportsControllerTest {

	@MockBean
	private ReportsService reportsService;

	@Autowired
	private MockMvc mockMvc;

	private Transit transit1, transit2;
	private long distance1 = 320, distance2 = 230;
	private double price1 = 450, price2 = 335;
	private String totalDistance, totalPrice;
	private Map<String, String> dailyReport;

	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		MockitoAnnotations.initMocks(this);
		transit1 = new Transit("ul. Zakręt 8, Poznań", "Złota 44, Warszawa", price1,
				LocalDate.of(2018, 3, 15));
		transit1.setDistance(distance1);
		transit2 = new Transit("Akademicka 5, Warszawa",
				"Mikołaja Kopernika 15, Tarnobrzeg", price2, LocalDate.of(2018, 3, 21));
		transit2.setDistance(distance2);
		totalDistance = distance1 + distance2 + "km";
		totalPrice = price1 + price2 + "PLN";
		dailyReport = new HashMap<>();
		dailyReport.put("total_distance", totalDistance);
		dailyReport.put("total_price", totalPrice);
	}

	@Test
	public void checkDailyReport() throws Exception {
		given(reportsService.getDailyReport(LocalDate.of(2018, 3, 10),
				LocalDate.of(2018, 3, 25))).willReturn(dailyReport);

		mockMvc.perform(get("/reports/daily?start_date=2018-03-10&end_date=2018-03-25")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$").value(hasKey("total_distance")))
				.andExpect(jsonPath("$.total_distance").value(totalDistance))
				.andExpect(jsonPath("$").value(hasKey("total_price")))
				.andExpect(jsonPath("$.total_price").value(totalPrice));
	}

	@Test
	public void checkMonthlyReport() throws Exception {
		String date = LocalDate.of(2018, 3, 9)
				.format(DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.ENGLISH))
				+ ", 9" + DayNumberSuffix.getDayNumberSuffix(9);
		given(reportsService.getMonthlyReport(LocalDate.now().withDayOfMonth(1),
				LocalDate.now())).willReturn(Arrays.asList(
						new DailyTransit(LocalDate.of(2018, 3, 9), 123L, 98L, 120d),
						new DailyTransit(LocalDate.of(2018, 3, 15), 111L, 55L, 77d)));

		mockMvc.perform(get("/reports/monthly").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0]").value(hasKey("date")))
				.andExpect(jsonPath("$[0].date").value(date))
				.andExpect(jsonPath("$[0]").value(hasKey("total_distance")))
				.andExpect(jsonPath("$[0].total_distance").value("123km"))
				.andExpect(jsonPath("$[0]").value(hasKey("avg_distance")))
				.andExpect(jsonPath("$[0].avg_distance").value("98km"))
				.andExpect(jsonPath("$[0]").value(hasKey("avg_price")))
				.andExpect(jsonPath("$[0].avg_price").value("120PLN"));
	}

}
