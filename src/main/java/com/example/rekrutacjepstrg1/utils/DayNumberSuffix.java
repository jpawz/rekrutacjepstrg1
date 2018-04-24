package com.example.rekrutacjepstrg1.utils;

public class DayNumberSuffix {
	
	/**
	 * Returns ordinal number suffix for given cardinal number.
	 * @param day - day of month
	 * @return ordinal number suffix (st, nd, rd, th).
	 */
	public static String getDayNumberSuffix(int day) {
		if (day >= 11 && day <= 13) {
			return "th";
		}
		switch (day % 10) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}
}
