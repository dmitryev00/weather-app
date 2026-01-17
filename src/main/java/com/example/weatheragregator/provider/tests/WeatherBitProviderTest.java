package com.example.weatheragregator.provider.tests;

import com.example.weatheragregator.dto.provider.external.WeatherBitResponse;
import com.example.weatheragregator.dto.provider.internal.WeatherData;
import com.example.weatheragregator.provider.WeatherBitProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherBitProviderTest {

	@Mock
	private RestTemplate mockRestTemplate;

	private WeatherBitProvider provider;

	@BeforeEach
	void setUp() {
		provider = new WeatherBitProvider(mockRestTemplate, "api-key");
	}

	@Test
	void getWeather_ValidCity_ReturnCorrectData() {
		WeatherBitResponse mockResponse = mockResponse();

		when(mockRestTemplate.getForObject(
				anyString(),
				eq(WeatherBitResponse.class)
		)).thenReturn(mockResponse);

		WeatherData result = provider.getWeather("Moscow");

		assertEquals("Moscow", result.city());
		assertEquals(22.5, result.temperature());
		assertEquals(24.0, result.feelsLike());
		assertEquals(65, result.humidity());
		assertEquals(5.2, result.windSpeed());

		verify(mockRestTemplate, times(1))
				.getForObject(anyString(), eq(WeatherBitResponse.class));
	}

	@Test
	void getWeather_NullResponse_ThrowsException() {
		when(mockRestTemplate.getForObject(anyString(), any()))
				.thenReturn(null);

		RuntimeException exception = assertThrows(
				RuntimeException.class,
				() -> provider.getWeather("BadCity")
		);

		assertTrue(exception.getMessage().contains("Нет данных от WeatherBit"));
	}

	@Test
	void getWeather_EmptyDataList_ThrowsException() {
		WeatherBitResponse emptyResponse = new WeatherBitResponse(
				List.of(),
				0
		);

		when(mockRestTemplate.getForObject(anyString(), any()))
				.thenReturn(emptyResponse);

		RuntimeException exception = assertThrows(
				RuntimeException.class,
				() -> provider.getWeather("EmptyCity")
		);

		assertTrue(exception.getMessage().contains("Нет данных от WeatherBit"));
	}

	private WeatherBitResponse mockResponse() {
		return new WeatherBitResponse(
				List.of(new WeatherBitResponse.Data(
						65,
						5.2,
						22.5,
						24.0,
						"Moscow"
				)),
				1
		);
	}
}