package com.example.weatheragregator.provider;

import com.example.weatheragregator.dto.provider.external.WeatherApiResponse;
import com.example.weatheragregator.dto.provider.internal.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class WeatherApiProvider implements WeatherProvider{

	RestTemplate restTemplate = new RestTemplate();

	@Value("${weatherapi.api.key}")
	private String apiKey;

	@Override
	public WeatherData getWeather(String city) {
		var url = String.format(
				"http://api.weatherapi.com/v1/current.json?key=%s&q=%s&aqi=no",
				apiKey,
				city
		);
		WeatherApiResponse weatherApiResponse = restTemplate.getForObject(
				url,
				WeatherApiResponse.class
		);

		var data = weatherApiResponse.current();

		String cityName = weatherApiResponse.location().name();

		return new WeatherData(
				cityName,
				data.temperature(),
				data.feelsLike(),
				data.humidity(),
				data.windSpeed()
		);
	}
}
