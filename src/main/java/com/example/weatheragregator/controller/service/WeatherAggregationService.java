package com.example.weatheragregator.controller.service;

import com.example.weatheragregator.controller.service.provider.WeatherProvider;
import com.example.weatheragregator.dto.provider.internal.WeatherData;
import com.example.weatheragregator.dto.provider.internal.WeatherResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherAggregationService {
	private final List<WeatherProvider> providers;

	public WeatherAggregationService(List<WeatherProvider> providers) {
		this.providers = providers;
	}

	public WeatherResponse getWeather(String city) {
		List<WeatherData> allData = providers.stream()
				.map(provider -> provider.getWeather(city))
				.collect(Collectors.toList());

		double avgTemp = allData.stream()
				.mapToDouble(WeatherData::temperature)
				.average()
				.orElse(0.0);

		double avgFeelsLike = allData.stream()
				.mapToDouble(WeatherData::feelsLike)
				.average()
				.orElse(0.0);

		double avgHumidity = allData.stream()
				.mapToDouble(WeatherData::humidity)
				.average()
				.orElse(0.0);

		double avgWindSpeed = allData.stream()
				.mapToDouble(WeatherData::windSpeed)
				.average()
				.orElse(0.0);

		return new WeatherResponse(new WeatherData(
				city,
				avgTemp,
				avgFeelsLike,
				(int) Math.round(avgHumidity),  // Округляем до целого
				avgWindSpeed
		));
	}
}
