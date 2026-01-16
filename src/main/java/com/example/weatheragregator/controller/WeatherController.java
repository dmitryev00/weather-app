package com.example.weatheragregator.controller;

import com.example.weatheragregator.controller.service.WeatherAggregationService;
import com.example.weatheragregator.dto.provider.internal.WeatherResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("weather")
public class WeatherController {

	private final WeatherAggregationService weatherAggregationService;


	public WeatherController(WeatherAggregationService weatherAggregationService) {
		this.weatherAggregationService = weatherAggregationService;
	}


	@GetMapping("/{city}")
	public WeatherResponse getWeather(
			@PathVariable("city") String city
			)
	{
		return weatherAggregationService.getWeather(city);
	}


}
