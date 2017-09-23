package com.nokia.charts.util;

public class MathUtil {

	public static Double formatDouble(Double value) {
		return Double.valueOf(String.format("%.3f", value));
	}

	public static Float formatFloat(float value) {
		return Float.valueOf(String.format("%.2f", value));
	}

}
