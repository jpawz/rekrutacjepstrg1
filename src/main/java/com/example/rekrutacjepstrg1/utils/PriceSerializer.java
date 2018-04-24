package com.example.rekrutacjepstrg1.utils;

import java.io.IOException;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Serializes long value to String representation of price, e.g. 12 -> "12PLN".
 */
public class PriceSerializer extends StdSerializer<Double> {

	private static final long serialVersionUID = 1L;

	public PriceSerializer() {
		super(Double.class);
	}

	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider provider)
			throws IOException {
		gen.writeString(new DecimalFormat("#.#").format(value) + "PLN");
	}

}
