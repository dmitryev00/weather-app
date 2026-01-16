package com.example.weatheragregator.dto.provider.external;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record WeatherBitResponse(
		List<Data> data,
		int count
) {
	public record Data(
			@JsonProperty("rh") int humidity,
			@JsonProperty("wind_spd") double windSpeed,
			@JsonProperty("temp") double temperature,
			@JsonProperty("app_temp") double feelsLike,
			@JsonProperty("city_name") String city
	) {}
}
