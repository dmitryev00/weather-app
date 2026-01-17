package com.example.weatheragregator.provider;

import com.example.weatheragregator.dto.provider.external.WeatherBitResponse;
import com.example.weatheragregator.dto.provider.internal.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class WeatherBitProvider implements WeatherProvider{

	private final RestTemplate restTemplate;
	private final String apiKey;

	public WeatherBitProvider(@Value("${weatherbit.api.key}") String apiKey) {
		this.restTemplate = new RestTemplate();
		this.apiKey = apiKey;
	}

	public WeatherBitProvider(RestTemplate restTemplate, String apiKey) {
		this.restTemplate = restTemplate;
		this.apiKey = apiKey;
	}


	@Override
	public WeatherData getWeather(String city) {
		var url = String.format(
				"https://api.weatherbit.io/v2.0/current?city=%s&key=%s",
				city,
				apiKey
		);
		WeatherBitResponse response = restTemplate.getForObject(
				url,
				WeatherBitResponse.class
				);
		if (response == null || response.data().isEmpty()) {
			throw new RuntimeException("Нет данных от WeatherBit");
		}

		var data = response.data().get(0);
		return new WeatherData(
				data.city(),
				data.temperature(),
				data.feelsLike(),
				data.humidity(),
				data.windSpeed()
		);
	}
}
