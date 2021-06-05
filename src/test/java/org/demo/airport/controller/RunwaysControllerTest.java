package org.demo.airport.controller;

import org.demo.airport.impl.CountryAirportRunwayDetailsServiceImpl;
import org.demo.airport.model.Runways;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)

public class RunwaysControllerTest {

    @Mock
    CountryAirportRunwayDetailsServiceImpl countryAirportRunwayDetailsServiceImplMock;

    @InjectMocks
    RunwaysController underTest;


    @Before
    public void setup() {

    }

    @Test
    public void getRunwaysForCountryAirport() {
        Map<String, List<Runways>> mapforRunways = new HashMap<>();
        mapforRunways.put("NL", Collections.singletonList(Runways.builder().id("11").build()));
        Mockito.when(countryAirportRunwayDetailsServiceImplMock.getRunwaysOfAirportBasedOnCountry("NL")).thenReturn(mapforRunways);
        ResponseEntity<Map<String, List<Runways>>> responseEntity = underTest.getRunwaysForCountryAirport("NL");
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getRunwaysForCountryAirportWithN() {
        Map<String, List<Runways>> mapforRunways = new HashMap<>();
        mapforRunways.put("NL", Collections.singletonList(Runways.builder().id("11").build()));
        Mockito.when(countryAirportRunwayDetailsServiceImplMock.getRunwaysOfAirportBasedOnCountry("NL")).thenReturn(mapforRunways);
        ResponseEntity<Map<String, List<Runways>>> responseEntity = underTest.getRunwaysForCountryAirport("N");
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}