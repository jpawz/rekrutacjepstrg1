package com.example.rekrutacjepstrg1.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.rekrutacjepstrg1.domain.Transit;
import com.example.rekrutacjepstrg1.service.TransitService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(TransitController.class)
public class TransitControllerTest {

	@MockBean
	private TransitService transitService;

	@Autowired
	private MockMvc mockMvc;

	private JacksonTester<Transit> json;

	private Transit transit;

	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		transit = new Transit("ul. Zakręt 8, Poznań", "Złota 44, Warszawa", 450,
				LocalDate.of(2018, 3, 15));
	}

	@Test
	public void addTransit() throws Exception {
		// given
		given(transitService.createTransit("ul. Zakręt 8, Poznań", "Złota 44, Warszawa",
				450, LocalDate.of(2018, 3, 15))).willReturn(transit);

		// when
		MockHttpServletResponse response = mockMvc
				.perform(post("/transits").contentType(MediaType.APPLICATION_JSON)
						.content(json.write(transit).getJson()))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString())
				.isEqualTo(json.write(transit).getJson());
		checkJson("@.source_address", "ul. Zakręt 8, Poznań");
		checkJson("@.destination_address", "Złota 44, Warszawa");
		checkJson("@.date", "2018-03-15");
		assertThat(json.write(transit)).hasJsonPathNumberValue("@.price");
		assertThat(json.write(transit)).extractingJsonPathNumberValue("@.price")
				.isEqualTo(new Double(450));
	}

	private void checkJson(String path, String value) throws Exception {
		assertThat(json.write(transit)).hasJsonPathStringValue(path);
		assertThat(json.write(transit)).extractingJsonPathStringValue(path)
				.isEqualTo(value);
	}
}
