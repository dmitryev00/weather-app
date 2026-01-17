package com.example.weatheragregator.provider.tests;


import com.example.weatheragregator.dto.provider.external.WeatherApiResponse;
import com.example.weatheragregator.dto.provider.internal.WeatherData;
import com.example.weatheragregator.provider.WeatherApiProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherApiProviderTest {

	@Mock
	RestTemplate mockRestTemplate;

	WeatherApiProvider provider;

	@BeforeEach
	void setUp()
	{
		provider = new WeatherApiProvider(mockRestTemplate, "api-key");
	}


	@Test
	void getWeather_ValidCity_ReturnCorrectData()
	{
		WeatherApiResponse mockResponse = mockResponse();

		when(mockRestTemplate.getForObject(
				anyString(),
				eq(WeatherApiResponse.class)
		)).thenReturn(mockResponse);

		WeatherData result = provider.getWeather("Moscow");
		assertEquals("Moscow", result.city());
		assertEquals(24.0, result.temperature());
		assertEquals(22.4, result.feelsLike());
		assertEquals(21, result.humidity());
		assertEquals(13.0, result.windSpeed());

		verify(mockRestTemplate, times(1))
				.getForObject(anyString(), eq(WeatherApiResponse.class));
	}


	@Test
	void getWeather_NullResponse_ThrowsException() {
		when(mockRestTemplate.getForObject(anyString(), any()))
				.thenReturn(null);

		RuntimeException exception = assertThrows(
				RuntimeException.class,
				() -> provider.getWeather("BadCity")
		);

		assertTrue(exception.getMessage().contains("Нет данных от WeatherApi"));
	}

	@Test
	void getWeather_EmptyDataList_ThrowsException() {
		WeatherApiResponse emptyResponse = new WeatherApiResponse(
				null,
				null
		);

		when(mockRestTemplate.getForObject(anyString(), any()))
				.thenReturn(emptyResponse);

		RuntimeException exception = assertThrows(
				RuntimeException.class,
				() -> provider.getWeather("EmptyCity")
		);

		assertTrue(exception.getMessage().contains("Нет данных от WeatherApi"));
	}


	private WeatherApiResponse mockResponse() {
		return new WeatherApiResponse(
				new WeatherApiResponse.Location("Moscow"),
				new WeatherApiResponse.Current(
						24.0,
						22.4,
						21,
						13.0
				)
		);
	}

}
