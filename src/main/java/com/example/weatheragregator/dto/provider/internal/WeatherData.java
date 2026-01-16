package com.example.weatheragregator.dto.provider.internal;

public record WeatherData(
		String city,
		double temperature,
		double feelsLike,
		int humidity,
		double windSpeed) {
}
