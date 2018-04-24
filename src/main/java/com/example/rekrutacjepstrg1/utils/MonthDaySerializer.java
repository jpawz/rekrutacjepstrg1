package com.example.rekrutacjepstrg1.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Serializes LocalDate to format: "Month, ordinal number", e.g. "January, 22nd". Uses
 * English locale for the names of months.
 */
public class MonthDaySerializer extends StdSerializer<LocalDate> {

	private static final long serialVersionUID = 1L;

	public MonthDaySerializer() {
		super(LocalDate.class);
	}

	@Override
	public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider provider)
			throws IOException {
		gen.writeString(value
				.format(DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.ENGLISH))
				+ ", " + value.getDayOfMonth()
				+ DayNumberSuffix.getDayNumberSuffix(value.getDayOfMonth()));
	}

}
