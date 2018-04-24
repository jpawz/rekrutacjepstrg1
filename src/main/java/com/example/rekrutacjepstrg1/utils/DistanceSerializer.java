package com.example.rekrutacjepstrg1.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Serializes long value to String representation of distance - adds "km" suffix to number
 * e.g. 15 -> "15km"
 */
public class DistanceSerializer extends StdSerializer<Long> {

	private static final long serialVersionUID = 1L;

	public DistanceSerializer() {
		super(Long.class);
	}

	@Override
	public void serialize(Long value, JsonGenerator gen, SerializerProvider provider)
			throws IOException {
		gen.writeString(value + "km");
	}

}
