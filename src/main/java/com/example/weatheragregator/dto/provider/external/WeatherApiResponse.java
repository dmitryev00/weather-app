package com.example.weatheragregator.dto.provider.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherApiResponse(
		Location location,
		Current current
) {
	public record Location(
			String name
	) {}

	public record Current(
			@JsonProperty("temp_c") double temperature,
			@JsonProperty("feelslike_c") double feelsLike,
			int humidity,
			@JsonProperty("wind_kph") double windSpeed
	) {}
}
