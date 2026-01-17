package com.example.weatheragregator.provider;

import com.example.weatheragregator.dto.provider.internal.WeatherData;

public interface WeatherProvider {
	public WeatherData getWeather(String city);
}
