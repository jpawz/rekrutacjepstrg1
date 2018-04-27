package com.example.rekrutacjepstrg1.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.rekrutacjepstrg1.domain.Transit;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(TransitController.class)
public class TransitControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private JacksonTester<Transit> json;

	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void addTransit() throws Exception {
		// given
		Transit transit = new Transit("ul. Zakręt 8, Poznań", "Złota 44, Warszawa", 450,
				LocalDate.of(2018, 3, 15));

		// when
		JsonContent<Transit> content = json.write(transit);
		MockHttpServletResponse response = mockMvc.perform(post("/transits")
				.contentType(MediaType.APPLICATION_JSON).content(content.getJson()))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).isEqualTo(content.getJson());
		checkJson(content, "@.source_address", "ul. Zakręt 8, Poznań");
		checkJson(content, "@.destination_address", "Złota 44, Warszawa");
		checkJson(content, "@.date", "2018-03-15");
		assertThat(content).hasJsonPathNumberValue("@.price");
		assertThat(content).extractingJsonPathNumberValue("@.price")
				.isEqualTo(new Double(450));
	}

	private void checkJson(JsonContent<Transit> content, String path, String value)
			throws Exception {
		assertThat(content).hasJsonPathStringValue(path);
		assertThat(content).extractingJsonPathStringValue(path).isEqualTo(value);
	}
}
