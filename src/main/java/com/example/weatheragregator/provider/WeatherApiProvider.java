package com.example.weatheragregator.provider;

import com.example.weatheragregator.dto.provider.external.WeatherApiResponse;
import com.example.weatheragregator.dto.provider.internal.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class WeatherApiProvider implements WeatherProvider{

	private final RestTemplate restTemplate;
	private final String apiKey;

	public WeatherApiProvider(@Value("${weatherapi.api.key}") String apiKey) {
		this.restTemplate = new RestTemplate();
		this.apiKey = apiKey;
	}

	public WeatherApiProvider(RestTemplate restTemplate, String apiKey) {
		this.restTemplate = restTemplate;
		this.apiKey = apiKey;
	}

	@Override
	public WeatherData getWeather(String city) {
		var url = String.format(
				"http://api.weatherapi.com/v1/current.json?key=%s&q=%s&aqi=no",
				apiKey,
				city
		);
		WeatherApiResponse response = restTemplate.getForObject(
				url,
				WeatherApiResponse.class
		);

		if (response == null || response.location() == null || response.current() == null) {
			throw new RuntimeException("Нет данных от WeatherApi");
		}

		var data = response.current();

		String cityName = response.location().name();

		return new WeatherData(
				cityName,
				data.temperature(),
				data.feelsLike(),
				data.humidity(),
				data.windSpeed()
		);
	}
}
