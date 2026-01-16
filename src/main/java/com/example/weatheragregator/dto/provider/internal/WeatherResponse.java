package com.example.weatheragregator.dto.provider.internal;


import com.example.weatheragregator.controller.service.WeatherAggregationService;

public record WeatherResponse(
		WeatherData data
) {
}
