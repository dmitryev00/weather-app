package com.example.weatheragregator.service;

import com.example.weatheragregator.provider.WeatherProvider;
import com.example.weatheragregator.dto.provider.internal.WeatherData;
import com.example.weatheragregator.dto.provider.internal.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WeatherAggregationService {
	private final List<WeatherProvider> providers;
	private static final Logger log = LoggerFactory.getLogger(WeatherAggregationService.class);

	public WeatherAggregationService(List<WeatherProvider> providers) {
		this.providers = providers;
		log.info("Инициализирован WeatherAggregationService с {} провайдерами", providers.size());

	}


	@Cacheable(value = "weatherCache", key = "#city")
	public WeatherResponse getWeather(String city) {

		log.debug("Начало агрегации для города: {}", city);

		List<WeatherData> allData = providers.stream()
				.map(provider -> {try {
					WeatherData data = provider.getWeather(city);
					log.debug("Данные от {}: {}",
							provider.getClass().getSimpleName(), data);
					return data;
				} catch (Exception e) {
					log.error("Ошибка в провайдере {}: {}",
							provider.getClass().getSimpleName(), e.getMessage());
					return null;
				}
				})
				.filter(Objects::nonNull)
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
				(int) Math.round(avgHumidity),
				avgWindSpeed
		));
	}


	@Scheduled(fixedRate = 10 * 60 * 1000)
	@CacheEvict(value = "weatherCache", allEntries = true)
	public void clearCache() {
		log.debug("Кэш очищен:{}",new Date());
	}
}
